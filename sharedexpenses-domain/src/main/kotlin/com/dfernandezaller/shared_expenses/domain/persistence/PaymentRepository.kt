package com.dfernandezaller.shared_expenses.domain.persistence

import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentRepository {

    fun save(payment: Payment): Mono<Payment>
    fun getAll(): Flux<Payment>

}