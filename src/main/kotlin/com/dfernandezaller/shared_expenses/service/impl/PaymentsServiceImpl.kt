package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.model.dto.AggregatedSpentByPersonDTO
import com.dfernandezaller.shared_expenses.model.dto.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.model.entities.Payment
import com.dfernandezaller.shared_expenses.repositories.PaymentRepository
import com.dfernandezaller.shared_expenses.repositories.PeopleRepository
import com.dfernandezaller.shared_expenses.service.PaymentsService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PaymentsServiceImpl(
    private val paymentRepository: PaymentRepository,
    private val peopleRepository: PeopleRepository
) : PaymentsService {

    override fun getPayments(): Flux<Payment> {
        return paymentRepository.findAll(Sort.by(Sort.Order.desc("date")))
    }

    override fun addPayment(payment: Payment): Mono<Payment> {
        return paymentRepository.save(payment)
    }

    override fun getBalance(): Flux<PersonalBalanceDTO> {
        return getFinalResult(peopleRepository.getPeopleGroupedByAmountSpent())
    }

    private fun getFinalResult(peopleAndEachTotalSpent: Flux<AggregatedSpentByPersonDTO>): Flux<PersonalBalanceDTO> {
        val eachOneExpenseMono = peopleAndEachTotalSpent
            .reduce(0F) { acc, aggregatedSpentByPerson -> acc.plus(aggregatedSpentByPerson.totalSpent) }
            .flatMap { totalAmountSpent -> peopleAndEachTotalSpent.count().map { count -> totalAmountSpent / count } }

        return eachOneExpenseMono.flatMapMany { eachOneExpense ->
            peopleAndEachTotalSpent.map { person ->
                PersonalBalanceDTO(
                    person.name,
                    person.lastName,
                    (person.totalSpent * -1) + eachOneExpense
                )
            }
        }
    }

}