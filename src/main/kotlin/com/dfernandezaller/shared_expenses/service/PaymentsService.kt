package com.dfernandezaller.shared_expenses.service

import com.dfernandezaller.shared_expenses.model.Payment
import com.dfernandezaller.shared_expenses.model.PersonalBalanceDTO
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PaymentsService {

    fun getPayments(): Flux<Payment>

    fun addPayment(payment: Payment): Mono<Payment>

    fun getBalance(): Mono<List<PersonalBalanceDTO>>

}