package com.dfernandezaller.shared_expenses.domain.primary.ports

import com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import com.dfernandezaller.shared_expenses.domain.persistence.PaymentRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PaymentService(private val paymentRepository: PaymentRepository) {
    fun getPayments(): Flux<PaymentDTO> {
        return paymentRepository
            .getAll()
            .map(Payment::toDTO)
    }

    fun addPayment(payment: PaymentDTO): Mono<PaymentDTO> {
        return paymentRepository
            .save(payment.toEntity())
            .map(Payment::toDTO)
    }

}