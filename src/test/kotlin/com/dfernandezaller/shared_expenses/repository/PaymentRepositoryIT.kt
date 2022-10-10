package com.dfernandezaller.shared_expenses.repository

import com.dfernandezaller.shared_expenses.BaseMongoITTest
import com.dfernandezaller.shared_expenses.repositories.PaymentRepository
import com.dfernandezaller.shared_expenses.repositories.PeopleRepository
import com.dfernandezaller.shared_expenses.utils.getTestPayment
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PaymentRepositoryIT : BaseMongoITTest() {

    @Autowired
    lateinit var paymentRepository: PaymentRepository

    @Autowired
    lateinit var peopleRepository: PeopleRepository

    @BeforeEach
    fun cleanDb() {
        paymentRepository.deleteAll().block()
        peopleRepository.deleteAll().block()
    }

    @Test
    fun shouldSavePayment() {
        // GIVEN
        val testPayment = getTestPayment();

        //WHEN
        val result = paymentRepository.save(testPayment).block()!!

        //THEN
        assertNotNull(result.id)
        assertEquals(testPayment.description, result.description)
        assertEquals(testPayment.owner, result.owner)
        assertEquals(testPayment.amount, result.amount)
    }

    @Test
    fun shouldGetPayment() {
        // GIVEN
        val testPayment = getTestPayment()

        //WHEN
        val saveAndFindResult = paymentRepository.save(testPayment)
            .then(paymentRepository.findAll().next())
            .block()!!

        //THEN
        assertNotNull(saveAndFindResult.id)
        assertEquals(testPayment.description, saveAndFindResult.description)
        assertEquals(testPayment.owner, saveAndFindResult.owner)
        assertEquals(testPayment.amount, saveAndFindResult.amount)
    }

}
