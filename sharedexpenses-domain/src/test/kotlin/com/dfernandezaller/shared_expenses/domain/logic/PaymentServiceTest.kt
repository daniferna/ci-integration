package com.dfernandezaller.shared_expenses.domain.logic

import com.dfernandezaller.shared_expenses.domain.primary.ports.PaymentService
import com.dfernandezaller.shared_expenses.domain.utils.PaymentMother
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class PaymentServiceTest {

    private val paymentServiceMock: PaymentService = mock(PaymentService::class.java)

    @Test
    fun getPayments() {
        //GIVEN
        val payments = PaymentMother.getListOfSmallPaymentsDTOs(5)
        `when`(paymentServiceMock.getPayments()).thenReturn(Flux.fromIterable(payments))

        //WHEN
        val result = paymentServiceMock.getPayments().collectList().block()!!

        //THEN
        assertEquals(5, result.count())
        Assertions.assertThat(result)
            .containsAll(payments)
    }

    @Test
    fun addPayment() {
        //GIVEN
        val payment = PaymentMother.getSmallAmountPaymentDTO()
        `when`(paymentServiceMock.addPayment(payment)).thenReturn(Mono.just(payment))

        //WHEN
        val result = paymentServiceMock.addPayment(payment).block()!!

        //THEN
        assertEquals(payment, result)
    }
}