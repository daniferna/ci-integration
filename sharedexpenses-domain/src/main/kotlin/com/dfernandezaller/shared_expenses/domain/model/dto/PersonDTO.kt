package com.dfernandezaller.shared_expenses.domain.model.dto

import com.dfernandezaller.shared_expenses.domain.model.entities.Person

data class PersonDTO(val name: String, val lastName: String) {

    fun toEntity(): Person {
        return Person(name, lastName)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PersonDTO

        if (name != other.name) return false
        if (lastName != other.lastName) return false

        return true
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + lastName.hashCode()
        return result
    }


}