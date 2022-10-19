package com.dfernandezaller.shared_expenses.domain.primary.ports

import com.dfernandezaller.shared_expenses.domain.model.dto.PersonDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.PersonRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PersonService(private val personRepository: PersonRepository) {
    fun savePerson(person: PersonDTO): Mono<PersonDTO> {
        return personRepository
            .save(person.toEntity())
            .map(Person::toDTO)
    }

}