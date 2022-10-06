package com.dfernandezaller.shared_expenses.service.impl

import com.dfernandezaller.shared_expenses.PaymentRepository
import com.dfernandezaller.shared_expenses.PeopleRepository
import com.dfernandezaller.shared_expenses.utils.getAggregatedSpentByPerson
import com.dfernandezaller.shared_expenses.utils.getTestPayment
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
internal class PaymentServiceImplTest {

    @InjectMocks
    lateinit var paymentsService: PaymentServiceImpl

    @Mock
    lateinit var paymentRepository: PaymentRepository

    @Mock
    lateinit var peopleRepository: PeopleRepository

    @Test
    fun shouldGetPayments() {
        //GIVEN
        Mockito.`when`(paymentRepository.findAll(any())).thenReturn(Flux.just(getTestPayment(), getTestPayment()))

        //WHEN
        val result = paymentsService.getPayments()

        //THEN
        Mockito.verify(paymentRepository, times(1)).findAll(any())
        StepVerifier.create(result)
            .expectNextCount(2L)
            .verifyComplete()
    }

    @Test
    fun addPayment() {
        //GIVEN
        val payment = getTestPayment()
        Mockito.`when`(paymentRepository.save(any())).thenReturn(Mono.just(payment))

        //WHEN
        val result = paymentsService.addPayment(payment)

        //THEN
        Mockito.verify(paymentRepository, times(1)).save(payment)
        StepVerifier.create(result)
            .expectNext(payment)
            .verifyComplete()
    }

    @Test
    fun getBalance() {
        //GIVEN
        val person1 = getAggregatedSpentByPerson("Name 1", 50F)
        val person2 = getAggregatedSpentByPerson("Name 2", 10F)
        val person3 = getAggregatedSpentByPerson("Name 3", 0F)

        Mockito.`when`(peopleRepository.getPeopleGroupedByAmountSpent())
            .thenReturn(Flux.just(person1, person2, person3))

        //WHEN
        val result = paymentsService.getBalance()

        //THEN
        Mockito.verify(peopleRepository, times(1)).getPeopleGroupedByAmountSpent()
        StepVerifier.create(result)
            .expectNextMatches { list ->
                Assertions.assertEquals(list[0].name, person1.name)
                Assertions.assertEquals(list[0].lastName, person1.lastName)
                Assertions.assertEquals(list[0].amount, -30F)
                Assertions.assertEquals(list[1].name, person2.name)
                Assertions.assertEquals(list[1].lastName, person2.lastName)
                Assertions.assertEquals(list[1].amount, 10F)
                Assertions.assertEquals(list[2].name, person3.name)
                Assertions.assertEquals(list[2].lastName, person3.lastName)
                Assertions.assertEquals(list[2].amount, 20F)
                return@expectNextMatches true
            }
            .verifyComplete()
    }
}