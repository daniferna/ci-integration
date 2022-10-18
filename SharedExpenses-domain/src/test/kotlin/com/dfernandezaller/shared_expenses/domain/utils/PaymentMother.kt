package com.dfernandezaller.shared_expenses.domain.utils

import com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import kotlin.random.Random

class PaymentMother {
    companion object {
        fun getListOfSmallPayments(amount: Int = 3): MutableList<Payment> {
            val payments = ArrayList<Payment>(amount)
            for (i in 1..amount) {
                payments.add(
                    Payment.PaymentBuilder()
                        .withOwner(
                            Person.Builder()
                                .withName("Test name #${i}")
                                .build()
                        )
                        .build()
                )
            }
            return payments
        }

        fun getFluxOfSixSmallPaymentsDTOsWithFourPeople(): Flux<PaymentDTO> {
            val payments = getListOfSmallPayments(4)
            for (i in 1..2) {
                payments.add(
                    Payment.PaymentBuilder()
                        .withOwner(
                            Person.Builder()
                                .withName("Test name #${i}")
                                .build()
                        )
                        .build()
                )
            }
            return payments.map(Payment::toDTO).toFlux()
        }

        fun getSmallAmountPaymentDTO(): com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO {
            return Payment.PaymentBuilder()
                .withAmount(Random.nextFloat() * 10)
                .build().toDTO()
        }

        fun getSmallAmountPayment(ownerName: String): Payment {
            return Payment.PaymentBuilder()
                .withAmount(Random.nextFloat() * 10)
                .withOwner(Person.Builder().withName(ownerName).build())
                .build()
        }

        fun getListOfSmallPaymentsDTOs(amount: Int = 3): MutableList<com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO> {
            return getListOfSmallPayments(amount)
                .map(Payment::toDTO)
                .toCollection(ArrayList())
        }
    }
}