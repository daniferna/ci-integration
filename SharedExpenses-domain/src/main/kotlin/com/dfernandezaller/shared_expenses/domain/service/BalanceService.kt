package com.dfernandezaller.shared_expenses.domain.service

import com.dfernandezaller.shared_expenses.domain.model.dto.BalanceDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.RepositoryFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux

@Service
class BalanceService(
    private val repositoryFactory: RepositoryFactory,
    private val calculateBalanceService: CalculateBalanceService
) {
    fun getBalance(): Flux<BalanceDTO> {
        val payments = repositoryFactory.createPaymentRepository()
            .getAll()
            .map(Payment::toDTO)
        val people = repositoryFactory.createPersonRepository()
            .getAll()
            .map(Person::toDTO)

        return calculateBalanceService.calculateBalance(payments, people)
    }
}