package com.dfernandezaller.shared_expenses.persistance.repositories

import com.dfernandezaller.shared_expenses.com.dfernandezaller.shared_expenses.persistance.utils.MongoPaymentMother.Companion.getSmallAmountMongoPayment
import com.dfernandezaller.shared_expenses.persistance.BaseMongoDeployer
import com.dfernandezaller.shared_expenses.persistence.MongoPaymentDAO
import com.dfernandezaller.shared_expenses.persistence.MongoPersonDAO
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MongoPaymentDAOIT : BaseMongoDeployer() {

    @Autowired
    lateinit var paymentDAO: MongoPaymentDAO

    @Autowired
    lateinit var personDAO: MongoPersonDAO

    @BeforeEach
    fun cleanDb() {
        paymentDAO.deleteAll().block()
        personDAO.deleteAll().block()
    }

    @Test
    fun shouldSavePayment() {
        // GIVEN
        val testPayment = getSmallAmountMongoPayment()

        //WHEN
        val result = paymentDAO.save(testPayment).block()!!

        //THEN
        assertThat(result)
            .hasNoNullFieldsOrProperties()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(testPayment)
    }

    @Test
    fun shouldGetPayment() {
        // GIVEN
        val testPayment = getSmallAmountMongoPayment()

        //WHEN
        val saveAndFindResult = paymentDAO.save(testPayment)
            .then(paymentDAO.findAll().next())
            .block()!!

        //THEN
        assertThat(saveAndFindResult)
            .hasNoNullFieldsOrProperties()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(testPayment)
    }

}