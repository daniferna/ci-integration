package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.PeopleRepository
import com.dfernandezaller.shared_expenses.utils.getTestPerson
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
internal class PeopleServiceImplTest {

    @InjectMocks
    lateinit var peopleServiceImpl: PeopleServiceImpl

    @Mock
    lateinit var peopleRepository: PeopleRepository

    @Test
    fun shouldCreatePerson() {
        //GIVEN
        val person = getTestPerson("Test name")
        Mockito.`when`(peopleRepository.save(person)).thenReturn(Mono.just(person))

        //WHEN
        val result = peopleServiceImpl.savePerson(person)

        //THEN
        Mockito.verify(peopleRepository, Mockito.times(1)).save(person)
        StepVerifier.create(result)
            .expectNext(person)
            .verifyComplete()
    }
}