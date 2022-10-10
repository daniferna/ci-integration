package com.dfernandezaller.shared_expenses.controller

import com.dfernandezaller.shared_expenses.model.dto.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.model.entities.Payment
import com.dfernandezaller.shared_expenses.service.PaymentsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/payments")
class PaymentsController(private val paymentsService: PaymentsService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getExpenses(): Flux<Payment> {
        return paymentsService.getPayments()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun savePayment(@RequestBody payment: Payment): Mono<Payment> {
        return paymentsService.addPayment(payment)
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    fun getBalance(): Flux<PersonalBalanceDTO> {
        return paymentsService.getBalance()
    }

}