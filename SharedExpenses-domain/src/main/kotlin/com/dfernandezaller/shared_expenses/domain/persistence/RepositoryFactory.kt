package com.dfernandezaller.shared_expenses.domain.persistence

import org.springframework.stereotype.Component

@Component
interface RepositoryFactory {

    fun createPaymentRepository(): PaymentRepository
    fun createPersonRepository(): PersonRepository

}