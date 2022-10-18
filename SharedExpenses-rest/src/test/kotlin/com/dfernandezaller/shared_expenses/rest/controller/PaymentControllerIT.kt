package com.dfernandezaller.shared_expenses.rest.controller

import com.dfernandezaller.shared_expenses.domain.service.PaymentService
import com.dfernandezaller.shared_expenses.domain.utils.*
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
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

const val BASE_PAYMENTS_PATH: String = "/payment"

@WebMvcTest(PaymentController::class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PaymentControllerIT {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var paymentServiceMock: PaymentService

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
        val paymentsFlux = Flux.fromIterable(PaymentMother.getListOfSmallPaymentsDTOs())
        val paymentsJson = objectMapper.writeValueAsString(paymentsFlux.collectList().block())
        Mockito.`when`(paymentServiceMock.getPayments()).thenReturn(paymentsFlux)

        //WHEN
        val mvcResult: MvcResult = mockMvc.get(BASE_PAYMENTS_PATH)
            .andExpect { MockMvcResultMatchers.request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(paymentsJson))
    }

    @Test
    fun shouldSavePayment() {
        //GIVEN
        val payment = PaymentMother.getSmallAmountPaymentDTO()
        val paymentJson = objectMapper.writeValueAsString(payment)
        Mockito.`when`(paymentServiceMock.addPayment(payment)).thenReturn(Mono.just(payment))

        //WHEN
        val mvcResult: MvcResult = mockMvc
            .perform(
                MockMvcRequestBuilders.post(BASE_PAYMENTS_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(paymentJson)
            )
            .andExpect { MockMvcResultMatchers.request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(MockMvcRequestBuilders.asyncDispatch(mvcResult))
            .andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.content().json(paymentJson))
    }

}