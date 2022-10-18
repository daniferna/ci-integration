package com.dfernandezaller.shared_expenses.domain.utils

import com.dfernandezaller.shared_expenses.domain.model.dto.PersonDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux

class PersonMother {
    companion object {
        fun getListOfNormalPerson(amount: Int = 3): MutableList<Person> {
            val people = ArrayList<Person>(amount)
            for (i in 1..amount) {
                people.add(
                    Person.Builder()
                        .withName(("Test name #${i}"))
                        .build()
                )
            }
            return people
        }

        fun getFluxOfNormalPersonDTO(amount: Int = 3): Flux<PersonDTO> {
            return getListOfNormalPerson(amount).map(Person::toDTO).toFlux()
        }
    }
}