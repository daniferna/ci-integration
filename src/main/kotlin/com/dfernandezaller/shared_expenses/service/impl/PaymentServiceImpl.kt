package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.PaymentRepository
import com.dfernandezaller.shared_expenses.PeopleRepository
import com.dfernandezaller.shared_expenses.model.AggregatedSpentByPerson
import com.dfernandezaller.shared_expenses.model.Payment
import com.dfernandezaller.shared_expenses.model.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.service.PaymentsService
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PaymentServiceImpl(
    private val paymentRepository: PaymentRepository,
    private val peopleRepository: PeopleRepository
) : PaymentsService {

    override fun getPayments(): Flux<Payment> {
        return paymentRepository.findAll(Sort.by(Sort.Order.desc("date")))
    }

    override fun addPayment(payment: Payment): Mono<Payment> {
        return paymentRepository.save(payment)
    }

    override fun getBalance(): Mono<List<PersonalBalanceDTO>> {
        return peopleRepository.getPeopleGroupedByAmountSpent()
            .collectList()
            .map(this::getFinalResult)
    }

    private fun getFinalResult(peopleAndEachTotalSpent: List<AggregatedSpentByPerson>): List<PersonalBalanceDTO> {
        val totalAmountSpent = peopleAndEachTotalSpent
            .fold(0F) { acc, aggregatedSpentByPerson -> acc.plus(aggregatedSpentByPerson.totalSpent) }
        val eachOneExpense = totalAmountSpent / peopleAndEachTotalSpent.size
        return peopleAndEachTotalSpent.map { person ->
            PersonalBalanceDTO(person.name, person.lastName, (person.totalSpent * -1) + eachOneExpense)
        }
    }

}