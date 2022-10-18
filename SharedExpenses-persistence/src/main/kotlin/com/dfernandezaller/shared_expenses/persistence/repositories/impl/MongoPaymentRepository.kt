package com.dfernandezaller.shared_expenses.persistence.repositories.impl

import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.PaymentRepository
import com.dfernandezaller.shared_expenses.persistence.MongoPaymentDAO
import com.dfernandezaller.shared_expenses.persistence.data.MongoPayment
import com.dfernandezaller.shared_expenses.persistence.data.MongoPerson
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class MongoPaymentRepository(private val mongoPaymentDAO: MongoPaymentDAO) : PaymentRepository {

    override fun save(payment: Payment): Mono<Payment> {
        return mongoPaymentDAO
            .save(
                MongoPayment(
                    null,
                    MongoPerson(null, payment.owner.name, payment.owner.lastName),
                    payment.amount,
                    payment.description,
                    payment.date
                )
            )
            .map { Payment(Person(it.owner.name, it.owner.lastName), it.amount, it.description, it.date) }
    }

    override fun getAll(sort: Sort): Flux<Payment> {
        return mongoPaymentDAO
            .findAll(sort)
            .map { Payment(Person(it.owner.name, it.owner.lastName), it.amount, it.description, it.date) }
    }

}