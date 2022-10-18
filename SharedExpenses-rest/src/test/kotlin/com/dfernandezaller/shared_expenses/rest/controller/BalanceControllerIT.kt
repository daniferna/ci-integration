package com.dfernandezaller.shared_expenses.com.dfernandezaller.shared_expenses.rest.controller

import com.dfernandezaller.shared_expenses.domain.service.BalanceService
import com.dfernandezaller.shared_expenses.domain.utils.BalanceMother
import com.dfernandezaller.shared_expenses.rest.controller.BalanceController
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
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import reactor.core.publisher.Flux
import javax.annotation.PostConstruct

const val BASE_BALANCE_PATH: String = "/balance"

@WebMvcTest(BalanceController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BalanceControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var balanceServiceMock: BalanceService

    @Autowired
    lateinit var springMvcJacksonConverter: MappingJackson2HttpMessageConverter

    lateinit var objectMapper: ObjectMapper

    @PostConstruct
    @BeforeAll
    fun setUpObjectMapper() {
        objectMapper = springMvcJacksonConverter.objectMapper
    }

    @Test
    fun shouldReturnBalance() {
        //GIVEN
        val balanceFlux = Flux.fromIterable(BalanceMother.getListOfBalanceDTOs())
        val balanceJson = objectMapper.writeValueAsString(balanceFlux.collectList().block())
        Mockito.`when`(balanceServiceMock.getBalance()).thenReturn(balanceFlux)

        //WHEN
        val mvcResult: MvcResult = mockMvc.get(BASE_BALANCE_PATH)
            .andExpect { MockMvcResultMatchers.request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(balanceJson))
    }

}