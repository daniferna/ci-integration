package com.dfernandezaller.shared_expenses.persistence

import com.dfernandezaller.shared_expenses.persistence.data.MongoPayment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MongoPaymentDAO : ReactiveMongoRepository<MongoPayment, Long>