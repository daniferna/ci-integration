package com.dfernandezaller.shared_expenses.domain.service

import com.dfernandezaller.shared_expenses.domain.model.dto.PersonDTO
import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.RepositoryFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PersonService(private val repositoryFactory: RepositoryFactory) {
    fun savePerson(person: PersonDTO): Mono<PersonDTO> {
        return repositoryFactory.createPersonRepository()
            .save(person.toEntity())
            .map(Person::toDTO)
    }

}