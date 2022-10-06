package com.dfernandezaller.shared_expenses.repository

import com.dfernandezaller.shared_expenses.PaymentRepository
import com.dfernandezaller.shared_expenses.PeopleRepository
import com.dfernandezaller.shared_expenses.model.AggregatedSpentByPerson
import com.dfernandezaller.shared_expenses.utils.getTestPayment
import com.dfernandezaller.shared_expenses.utils.getTestPerson
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import reactor.test.StepVerifier

@DataMongoTest(excludeAutoConfiguration = [EmbeddedMongoAutoConfiguration::class])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class RepositoriesTestIT {

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
        val result = paymentRepository.save(testPayment)

        //THEN
        StepVerifier.create(result)
            .expectNextMatches { savedPayment ->
                assertNotNull(savedPayment.id)
                assertEquals(testPayment.description, savedPayment.description)
                assertEquals(testPayment.owner, savedPayment.owner)
                assertEquals(testPayment.amount, savedPayment.amount)
                return@expectNextMatches true
            }
            .verifyComplete()

        StepVerifier.create(paymentRepository.count())
            .expectNext(1L)
            .verifyComplete();
    }

    @Test
    fun shouldGetPayment() {
        // GIVEN
        val testPayment = getTestPayment()
        //WHEN
        val saveResult = paymentRepository.save(testPayment).then()
        val result = paymentRepository.findAll()

        //THEN
        StepVerifier.create(saveResult)
            .verifyComplete()
        StepVerifier.create(result)
            .expectNextMatches { savedPayment ->
                assertNotNull(savedPayment.id)
                assertEquals(testPayment.description, savedPayment.description)
                assertEquals(testPayment.owner, savedPayment.owner)
                assertEquals(testPayment.amount, savedPayment.amount)
                return@expectNextMatches true
            }
            .verifyComplete()
    }

    @Test
    fun shouldGetPeopleGroupedByAmountSpent() {
        // GIVEN
        val testPayment1 = getTestPayment("Test name 1", 10F)
        val testPayment2 = getTestPayment("Test name 2", 20F)
        val testPayment3 = getTestPayment("Test name 2", 15F)
        val testPayment4 = getTestPayment("Test name 3", 0F)

        val testPerson1 = getTestPerson("Test name 1")
        val testPerson2 = getTestPerson("Test name 2")
        val testPerson3 = getTestPerson("Test name 3")

        //WHEN
        val resultSavePayments = paymentRepository.save(testPayment1)
            .then(paymentRepository.save(testPayment2))
            .then(paymentRepository.save(testPayment3))
            .then(paymentRepository.save(testPayment4))
            .then()
        val resultSavePeople = peopleRepository.save(testPerson1)
            .then(peopleRepository.save(testPerson2))
            .then(peopleRepository.save(testPerson3))
            .then()

        val resultGetPeopleGroupedByAmountSpent = peopleRepository.getPeopleGroupedByAmountSpent()
            .sort(Comparator.comparing(AggregatedSpentByPerson::name, String.CASE_INSENSITIVE_ORDER))

        //THEN
        StepVerifier.create(resultSavePayments)
            .verifyComplete()
        StepVerifier.create(resultSavePeople)
            .verifyComplete()

        StepVerifier.create(resultGetPeopleGroupedByAmountSpent)
            .expectNextMatches { result ->
                assertEquals(result.name, testPerson1.name)
                assertEquals(result.name, testPayment1.owner.name)
                assertEquals(result.lastName, testPerson1.lastName)
                assertEquals(result.lastName, testPayment1.owner.lastName)
                assertEquals(result.totalSpent, 10F)
                return@expectNextMatches true
            }
            .expectNextMatches { result ->
                assertEquals(result.name, testPerson2.name)
                assertEquals(result.name, testPayment2.owner.name)
                assertEquals(result.name, testPayment3.owner.name)
                assertEquals(result.lastName, testPerson2.lastName)
                assertEquals(result.lastName, testPayment2.owner.lastName)
                assertEquals(result.lastName, testPayment3.owner.lastName)
                assertEquals(result.totalSpent, 35F)
                return@expectNextMatches true
            }
            .expectNextMatches { result ->
                assertEquals(result.name, testPerson3.name)
                assertEquals(result.name, testPayment4.owner.name)
                assertEquals(result.lastName, testPerson3.lastName)
                assertEquals(result.lastName, testPayment4.owner.lastName)
                assertEquals(result.totalSpent, 0F)
                return@expectNextMatches true
            }
            .verifyComplete()
    }
}
