package com.dfernandezaller.shared_expenses.domain.service

import com.dfernandezaller.shared_expenses.domain.model.dto.BalanceDTO
import com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO
import com.dfernandezaller.shared_expenses.domain.model.dto.PersonDTO
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

//TODO: Tests de esta clase
@Service
class CalculateBalanceService {
    fun calculateBalance(paymentsFlux: Flux<PaymentDTO>, peopleFlux: Flux<PersonDTO>): Flux<BalanceDTO> {
        val cachedPeopleFlux = peopleFlux.cache()
        val cachedPaymentsFlux = paymentsFlux.cache()

        return cachedPaymentsFlux.map { it.amount }.reduce(Float::plus)
            .flatMap { totalSpent -> cachedPeopleFlux.count().map { peopleCount -> totalSpent / peopleCount } }
            .flatMapMany { totalBetweenEachOne ->
                cachedPeopleFlux.flatMap { person ->
                    cachedPaymentsFlux.filter { it.owner == person }.map { it.amount }.reduce(0F, Float::plus)
                        .map { spentByThisPerson ->
                            BalanceDTO(
                                person.name,
                                person.lastName,
                                (spentByThisPerson * -1 + totalBetweenEachOne)
                            )
                        }
                }
            }
    }
}