package com.dfernandezaller.shared_expenses.controller

import com.dfernandezaller.shared_expenses.service.PeopleService
import com.dfernandezaller.shared_expenses.utils.getTestPerson
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

private const val BASE_PEOPLE_PATH = "/people"

@WebMvcTest(PeopleController::class)
@TestInstance(Lifecycle.PER_CLASS)
internal class PeopleControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var peopleServiceMock: PeopleService

    @Autowired
    lateinit var springMvcJacksonConverter: MappingJackson2HttpMessageConverter

    lateinit var objectMapper : ObjectMapper

    @PostConstruct
    @BeforeAll
    fun setUpObjectMapper() {
        objectMapper = springMvcJacksonConverter.objectMapper
    }

    @Test
    fun shouldReturnPayments() {
        //GIVEN
        val person = getTestPerson("Martin")
        val personJson = objectMapper.writeValueAsString(person)
        Mockito.`when`(peopleServiceMock.savePerson(person)).thenReturn(Mono.just(person))

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