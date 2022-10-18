package com.dfernandezaller.shared_expenses.rest.controller

import com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO
import com.dfernandezaller.shared_expenses.domain.service.PaymentService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/payment")
class PaymentController(private val paymentService: PaymentService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getExpenses(): Flux<PaymentDTO> {
        return paymentService.getPayments()
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun savePayment(@RequestBody payment: PaymentDTO): Mono<PaymentDTO> {
        return paymentService.addPayment(payment)
    }

}