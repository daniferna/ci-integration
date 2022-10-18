package com.dfernandezaller.shared_expenses.domain.service

import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Mono
import kotlin.test.assertEquals

internal class PersonServiceTest {

    private val personServiceMock: PersonService = mock(PersonService::class.java)

    @Test
    fun createPerson() {
        //GIVEN
        val person = Person.Builder().buildAsDto()
        `when`(personServiceMock.savePerson(person)).thenReturn(Mono.just(person))

        //WHEN
        val result = personServiceMock.savePerson(person).block()!!

        //THEN
        assertEquals(person, result)
    }
}