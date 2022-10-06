package com.dfernandezaller.shared_expenses.controller

import com.dfernandezaller.shared_expenses.model.Payment
import com.dfernandezaller.shared_expenses.model.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.service.PaymentsService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController()
@RequestMapping("/payments")
@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
class PaymentController(private val paymentsService: PaymentsService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getExpenses(): Mono<List<Payment>> {
        return paymentsService.getPayments().collectList()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun savePayment(@RequestBody payment: Payment): Mono<Payment> {
        return paymentsService.addPayment(payment)
    }

    @GetMapping("/balance")
    @ResponseStatus(HttpStatus.OK)
    fun getBalance(): Mono<List<PersonalBalanceDTO>> {
        return paymentsService.getBalance()
    }

}