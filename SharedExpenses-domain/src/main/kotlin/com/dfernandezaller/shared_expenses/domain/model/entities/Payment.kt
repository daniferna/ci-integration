package com.dfernandezaller.shared_expenses.domain.model.entities

import com.dfernandezaller.shared_expenses.domain.model.dto.PaymentDTO
import java.util.*
import kotlin.random.Random

data class Payment(
    val owner: Person,
    val amount: Float,
    val description: String,
    val date: Date
) {

    fun toDTO(): PaymentDTO {
        return PaymentDTO(owner.toDTO(), amount, description, date)
    }

    class PaymentBuilder {
        private var owner: Person = Person("Test name", "Test lastName")
        private var date: Date = Date()
        private var description: String = "This payment is part of a test"
        private var amount: Float = Random.nextFloat() * 10

        fun withOwner(owner: Person): PaymentBuilder {
            this.owner = owner
            return this
        }

        fun withDate(date: Date): PaymentBuilder {
            this.date = date
            return this
        }

        fun withDescription(description: String): PaymentBuilder {
            this.description = description
            return this
        }

        fun withAmount(amount: Float): PaymentBuilder {
            this.amount = amount
            return this
        }

        fun build(): Payment {
            return Payment(owner, amount, description, date)
        }
    }
}