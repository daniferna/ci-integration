package com.dfernandezaller.shared_expenses.domain.service

import com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import com.dfernandezaller.shared_expenses.domain.persistence.RepositoryFactory
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class PaymentService(private val repositoryFactory: RepositoryFactory) {
    fun getPayments(): Flux<PaymentDTO> {
        return repositoryFactory.createPaymentRepository()
            .getAll(Sort.by(Sort.Order.desc("date")))
            .map(Payment::toDTO)
    }

    fun addPayment(payment: PaymentDTO): Mono<PaymentDTO> {
        return repositoryFactory.createPaymentRepository()
            .save(payment.toEntity())
            .map(Payment::toDTO)
    }

}