package com.dfernandezaller.shared_expenses.service

import com.dfernandezaller.shared_expenses.model.Person
import reactor.core.publisher.Mono

interface PeopleService {

    fun savePerson(person: Person): Mono<Person>

}