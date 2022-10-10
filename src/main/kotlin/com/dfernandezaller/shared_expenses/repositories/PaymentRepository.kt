package com.dfernandezaller.shared_expenses.repositories

import com.dfernandezaller.shared_expenses.model.entities.Payment
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface PaymentRepository : ReactiveMongoRepository<Payment, Long>