package com.dfernandezaller.shared_expenses.rest.controller

import com.dfernandezaller.shared_expenses.domain.model.entities.Person
import com.dfernandezaller.shared_expenses.domain.service.PersonService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

const val BASE_PEOPLE_PATH: String = "/person"

@WebMvcTest(PersonController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class PersonControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var personServiceMock: PersonService

    @Autowired
    lateinit var springMvcJacksonConverter: MappingJackson2HttpMessageConverter

    lateinit var objectMapper: ObjectMapper

    @PostConstruct
    @BeforeAll
    fun setUpObjectMapper() {
        objectMapper = springMvcJacksonConverter.objectMapper
    }

    @Test
    fun shouldReturnPayments() {
        //GIVEN
        val person = Person.Builder().buildAsDto()
        val personJson = objectMapper.writeValueAsString(person)
        Mockito.`when`(personServiceMock.savePerson(person)).thenReturn(Mono.just(person))

        //WHEN
        val mvcResult: MvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(BASE_PEOPLE_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(personJson)
            )
            .andExpect { MockMvcResultMatchers.request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(personJson))
    }

}