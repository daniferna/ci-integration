package com.dfernandezaller.shared_expenses.persistence.repositories.impl

import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.persistence.PersonRepository
import com.dfernandezaller.shared_expenses.persistence.MongoPersonDAO
import com.dfernandezaller.shared_expenses.persistence.data.MongoPerson
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class MongoPersonRepository(private val mongoPersonDAO: MongoPersonDAO) : PersonRepository {

    override fun save(person: Person): Mono<Person> {
        return mongoPersonDAO
            .save(MongoPerson(null, person.name, person.lastName))
            .map { Person(it.name, it.lastName) }
    }

    override fun getAll(sort: Sort): Flux<Person> {
        return mongoPersonDAO
            .findAll(sort)
            .map { Person(it.name, it.lastName) }
    }

}