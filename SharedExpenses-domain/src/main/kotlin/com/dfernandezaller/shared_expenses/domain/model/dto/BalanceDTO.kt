package com.dfernandezaller.shared_expenses.domain.model.dto

import kotlin.random.Random

data class BalanceDTO(
    val name: String,
    val lastName: String,
    val amount: Float
) {
    internal class Builder {
        private var name: String = "Test name"
        private var lastName: String = "Test last name"
        private var amount: Float = Random.nextFloat() * 10

        fun withName(name: String): Builder {
            this.name = name
            return this
        }

        fun withLastName(lastName: String): Builder {
            this.lastName = lastName
            return this
        }

        fun withAmount(amount: Float): Builder {
            this.amount = amount
            return this
        }

        fun build(): BalanceDTO {
            return BalanceDTO(name, lastName, amount)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BalanceDTO

        if (name != other.name) return false
        if (lastName != other.lastName) return false
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lastName.hashCode()
        result = 31 * result + amount.hashCode()
        return result
    }
}