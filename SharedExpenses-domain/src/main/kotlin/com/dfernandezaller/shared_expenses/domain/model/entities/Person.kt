package com.dfernandezaller.shared_expenses.domain.model.entities

import com.dfernandezaller.shared_expenses.domain.model.dto.PersonDTO

data class Person(
    val name: String,
    val lastName: String
) {

    fun toDTO(): PersonDTO {
        return PersonDTO(name, lastName)
    }

    class Builder {
        private var name: String = "Test name"
        private var lastName: String = "Test last name"

        fun withLastName(lastName: String): Builder {
            this.lastName = lastName
            return this
        }

        fun withName(name: String): Builder {
            this.name = name
            return this
        }

        fun build(): Person {
            return Person(name, lastName)
        }

        fun buildAsDto(): PersonDTO {
            return PersonDTO(name, lastName)
        }

    }

}