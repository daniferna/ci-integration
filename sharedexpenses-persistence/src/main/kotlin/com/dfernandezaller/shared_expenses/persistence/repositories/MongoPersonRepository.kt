package com.dfernandezaller.shared_expenses.persistence.repositories

import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.PersonRepository
import com.dfernandezaller.shared_expenses.persistence.MongoPersonDAO
import com.dfernandezaller.shared_expenses.persistence.data.MongoPerson
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Component
class MongoPersonRepository(private val mongoPersonDAO: MongoPersonDAO) : PersonRepository {

    override fun save(person: Person): Mono<Person> {
        return mongoPersonDAO
            .save(MongoPerson(null, person.name, person.lastName))
            .map { Person(it.name, it.lastName) }
    }

    override fun getAll(): Flux<Person> {
        return mongoPersonDAO
            .findAll(Sort.unsorted())
            .map { Person(it.name, it.lastName) }
    }

}