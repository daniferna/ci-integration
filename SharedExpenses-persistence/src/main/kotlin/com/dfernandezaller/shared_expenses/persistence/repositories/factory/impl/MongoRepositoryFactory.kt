package com.dfernandezaller.shared_expenses.persistence.repositories.factory.impl

import com.dfernandezaller.shared_expenses.domain.persistence.PaymentRepository
import com.dfernandezaller.shared_expenses.domain.persistence.PersonRepository
import com.dfernandezaller.shared_expenses.domain.persistence.RepositoryFactory
import com.dfernandezaller.shared_expenses.persistence.MongoPaymentDAO
import com.dfernandezaller.shared_expenses.persistence.MongoPersonDAO
import com.dfernandezaller.shared_expenses.persistence.repositories.impl.MongoPaymentRepository
import com.dfernandezaller.shared_expenses.persistence.repositories.impl.MongoPersonRepository
import org.springframework.stereotype.Repository

@Repository
class MongoRepositoryFactory(
    private val mongoPaymentDAO: MongoPaymentDAO, private val mongoPersonDAO: MongoPersonDAO
) : RepositoryFactory {

    override fun createPaymentRepository(): PaymentRepository {
        return MongoPaymentRepository(mongoPaymentDAO)
    }

    override fun createPersonRepository(): PersonRepository {
        return MongoPersonRepository(mongoPersonDAO)
    }
}