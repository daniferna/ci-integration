package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.model.dto.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.repositories.PaymentRepository
import com.dfernandezaller.shared_expenses.repositories.PeopleRepository
import com.dfernandezaller.shared_expenses.service.PaymentsService
import com.dfernandezaller.shared_expenses.utils.getAggregatedSpentByPerson
import com.dfernandezaller.shared_expenses.utils.getTestPayment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

internal class PaymentsServiceImplTest {

    private val paymentRepositoryMock: PaymentRepository = mock(PaymentRepository::class.java)
    private val peopleRepositoryMock: PeopleRepository = mock(PeopleRepository::class.java)

    private val paymentsService: PaymentsService = PaymentsServiceImpl(paymentRepositoryMock, peopleRepositoryMock)

    @Test
    fun getPayments() {
        //GIVEN
        `when`(paymentRepositoryMock.findAll(any())).thenReturn(Flux.just(getTestPayment(), getTestPayment()))

        //WHEN
        val result = paymentsService.getPayments()

        //THEN
        assertEquals(2, result.count().block())
    }

    @Test
    fun addPayment() {
        //GIVEN
        val payment = getTestPayment()
        `when`(paymentRepositoryMock.save(any())).thenReturn(Mono.just(payment))

        //WHEN
        val result = paymentsService.addPayment(payment)

        //THEN
        assertEquals(payment, result.block())
    }

    @Test
    fun getBalance() {
        //GIVEN
        val p1 = getAggregatedSpentByPerson("Name 1", 50F)
        val p2 = getAggregatedSpentByPerson("Name 2", 10F)
        val p3 = getAggregatedSpentByPerson("Name 3", 0F)

        `when`(peopleRepositoryMock.getPeopleGroupedByAmountSpent())
            .thenReturn(Flux.just(p1, p2, p3))

        //WHEN
        val result = paymentsService.getBalance().collectList().block()!!

        //THEN
        val personalBalanceP1 = PersonalBalanceDTO(p1.name, p1.lastName, -30F)
        val personalBalanceP2 = PersonalBalanceDTO(p2.name, p2.lastName, 10F)
        val personalBalanceP3 = PersonalBalanceDTO(p3.name, p3.lastName, 20F)

        assertTrue(result.contains(personalBalanceP1))
        assertTrue(result.contains(personalBalanceP2))
        assertTrue(result.contains(personalBalanceP3))
    }
}