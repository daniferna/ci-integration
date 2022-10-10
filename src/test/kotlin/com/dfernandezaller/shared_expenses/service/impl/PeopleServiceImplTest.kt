package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.repositories.PeopleRepository
import com.dfernandezaller.shared_expenses.service.PeopleService
import com.dfernandezaller.shared_expenses.utils.getTestPerson
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

internal class PeopleServiceImplTest {

    private val peopleRepositoryMock: PeopleRepository = mock(PeopleRepository::class.java)

    private val peopleServiceImpl: PeopleService = PeopleServiceImpl(peopleRepositoryMock)

    @Test
    fun createPerson() {
        //GIVEN
        val person = getTestPerson("Test name")
        `when`(peopleRepositoryMock.save(person)).thenReturn(Mono.just(person))

        //WHEN
        val result = peopleServiceImpl.savePerson(person)

        //THEN
        StepVerifier.create(result)
            .expectNext(person)
            .verifyComplete()
    }
}