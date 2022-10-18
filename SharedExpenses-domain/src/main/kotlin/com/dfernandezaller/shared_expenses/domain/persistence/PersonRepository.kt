package com.dfernandezaller.shared_expenses.domain.persistence

import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import org.springframework.data.domain.Sort
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface PersonRepository {

    fun save(person: Person): Mono<Person>
    fun getAll(sort: Sort = Sort.unsorted()): Flux<Person>

}