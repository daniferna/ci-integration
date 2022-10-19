package com.dfernandezaller.shared_expenses.domain.primary.ports

import com.dfernandezaller.shared_expenses.domain.logic.CalculateBalanceService
import com.dfernandezaller.shared_expenses.domain.model.dto.BalanceDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.PaymentRepository
import com.dfernandezaller.shared_expenses.domain.persistence.PersonRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BalanceService(
    private val paymentRepository: PaymentRepository,
    private val personRepository: PersonRepository,
    private val calculateBalanceService: CalculateBalanceService
) {
    fun getBalance(): Flux<BalanceDTO> {
        val payments = paymentRepository
            .getAll()
            .map(Payment::toDTO)
        val people = personRepository
            .getAll()
            .map(Person::toDTO)

        return calculateBalanceService.calculateBalance(payments, people)
    }
}