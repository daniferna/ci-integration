package com.dfernandezaller.shared_expenses.rest.controller

import com.dfernandezaller.shared_expenses.domain.model.dto.PersonDTO
import com.dfernandezaller.shared_expenses.domain.service.PersonService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/person")
class PersonController(private val personService: PersonService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun postUser(@RequestBody person: PersonDTO): Mono<PersonDTO> {
        return personService.savePerson(person)
    }

}