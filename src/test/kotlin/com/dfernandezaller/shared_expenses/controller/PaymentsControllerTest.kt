package com.dfernandezaller.shared_expenses.controller

import com.dfernandezaller.shared_expenses.service.PaymentsService
import com.dfernandezaller.shared_expenses.utils.getTestBalanceFlux
import com.dfernandezaller.shared_expenses.utils.getTestPayment
import com.dfernandezaller.shared_expenses.utils.getTestPaymentFlux
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import reactor.core.publisher.Mono
import javax.annotation.PostConstruct

private const val BASE_PAYMENTS_PATH = "/payments"
private const val BALANCE_PATH = "/balance"

@WebMvcTest(PaymentsController::class)
@TestInstance(Lifecycle.PER_CLASS)
internal class PaymentsControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var paymentsServiceMock: PaymentsService

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
        val paymentsFlux = getTestPaymentFlux()
        val paymentsJson = objectMapper.writeValueAsString(paymentsFlux.collectList().block())
        `when`(paymentsServiceMock.getPayments()).thenReturn(paymentsFlux)

        //WHEN
        val mvcResult: MvcResult = mockMvc.get(BASE_PAYMENTS_PATH)
            .andExpect { request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(paymentsJson))
    }

    @Test
    fun shouldReturnBalance() {
        //GIVEN
        val balanceFlux = getTestBalanceFlux()
        val balanceJson = objectMapper.writeValueAsString(balanceFlux.collectList().block())
        `when`(paymentsServiceMock.getBalance()).thenReturn(balanceFlux)

        //WHEN
        val mvcResult: MvcResult = mockMvc.get(BASE_PAYMENTS_PATH + BALANCE_PATH)
            .andExpect { request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isOk)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(balanceJson))
    }

    @Test
    fun shouldSavePayment() {
        //GIVEN
        val payment = getTestPayment()
        val paymentJson = objectMapper.writeValueAsString(payment)
        `when`(paymentsServiceMock.addPayment(payment)).thenReturn(Mono.just(payment))

        //WHEN
        val mvcResult: MvcResult = mockMvc
            .perform(
                post(BASE_PAYMENTS_PATH)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(paymentJson)
            )
            .andExpect { request().asyncStarted() }
            .andReturn()

        // THEN
        mockMvc.perform(asyncDispatch(mvcResult))
            .andExpect(status().isCreated)
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(content().json(paymentJson))
    }

}