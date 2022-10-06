package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.PeopleRepository
import com.dfernandezaller.shared_expenses.model.Person
import com.dfernandezaller.shared_expenses.service.PeopleService
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class PeopleServiceImpl(private val peopleRepository: PeopleRepository) : PeopleService {

    override fun savePerson(person: Person): Mono<Person> {
        return peopleRepository.save(person)
    }

}