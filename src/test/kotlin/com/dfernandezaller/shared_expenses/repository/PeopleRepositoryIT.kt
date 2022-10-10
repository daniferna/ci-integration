package com.dfernandezaller.shared_expenses.repository

import com.dfernandezaller.shared_expenses.BaseMongoITTest
import com.dfernandezaller.shared_expenses.model.dto.AggregatedSpentByPersonDTO
import com.dfernandezaller.shared_expenses.repositories.PaymentRepository
import com.dfernandezaller.shared_expenses.repositories.PeopleRepository
import com.dfernandezaller.shared_expenses.utils.getTestPayment
import com.dfernandezaller.shared_expenses.utils.getTestPayments
import com.dfernandezaller.shared_expenses.utils.getTestPeople
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

internal class PeopleRepositoryIT : BaseMongoITTest() {

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
    fun shouldGetPeopleGroupedByAmountSpent() {
        // GIVEN
        val testPeople = getTestPeople(3)
        val testPayments = getTestPayments(3)
        testPayments.add(getTestPayment("Test name 3", 4F))
        val expectedTotalSpent = floatArrayOf(1F, 2F, 7F)

        //WHEN
        val result = paymentRepository
            .saveAll(testPayments)
            .thenMany(peopleRepository.saveAll(testPeople))
            .thenMany(
                peopleRepository
                    .getPeopleGroupedByAmountSpent()
                    .sort(Comparator.comparing(AggregatedSpentByPersonDTO::name, String.CASE_INSENSITIVE_ORDER))
            )
            .collectList().block()!!

        //THEN
        for (i in 0 until result.size) {
            assertEquals(result[i].name, testPeople[i].name)
            assertEquals(result[i].name, testPayments[i].owner.name)
            assertEquals(result[i].lastName, testPeople[i].lastName)
            assertEquals(result[i].lastName, testPayments[i].owner.lastName)
            assertEquals(result[i].totalSpent, expectedTotalSpent[i])
        }
    }

}
