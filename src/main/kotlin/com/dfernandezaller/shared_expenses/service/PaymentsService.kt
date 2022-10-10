package com.dfernandezaller.shared_expenses.service

import com.dfernandezaller.shared_expenses.model.dto.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.model.entities.Payment
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentsService {

    fun getPayments(): Flux<Payment>

    fun addPayment(payment: Payment): Mono<Payment>

    fun getBalance(): Flux<PersonalBalanceDTO>

}