package com.dfernandezaller.shared_expenses.controller

import com.dfernandezaller.shared_expenses.model.entities.Person
import com.dfernandezaller.shared_expenses.service.PeopleService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/people")
class PeopleController(private val peopleService: PeopleService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postUser(@RequestBody person: Person): Mono<Person> {
        return peopleService.savePerson(person)
    }

}