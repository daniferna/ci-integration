package com.dfernandezaller.shared_expenses.domain.model.dto

import com.dfernandezaller.shared_expenses.domain.model.entities.Payment
import java.util.*

data class PaymentDTO(
    val owner: PersonDTO,
    val amount: Float,
    val description: String,
    val date: Date
) {
    fun toEntity(): Payment {
        return Payment(owner.toEntity(), amount, description, date)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PaymentDTO

        if (owner != other.owner) return false
        if (amount != other.amount) return false
        if (description != other.description) return false
        if (date != other.date) return false

        return true
    }

    override fun hashCode(): Int {
        var result = owner.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + date.hashCode()
        return result
    }


}