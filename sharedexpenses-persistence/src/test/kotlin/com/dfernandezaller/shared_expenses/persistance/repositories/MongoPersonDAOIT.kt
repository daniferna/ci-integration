package com.dfernandezaller.shared_expenses.persistance.repositories

import com.dfernandezaller.shared_expenses.persistance.BaseMongoDeployer
import com.dfernandezaller.shared_expenses.persistence.MongoPersonDAO
import com.dfernandezaller.shared_expenses.persistence.data.MongoPerson
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired

class MongoPersonDAOIT : BaseMongoDeployer() {

    @Autowired
    lateinit var personDAO: MongoPersonDAO

    @BeforeEach
    fun cleanDb() {
        personDAO.deleteAll().block()
    }

    @Test
    fun shouldSavePerson() {
        // GIVEN
        val testPerson = MongoPerson(null, "Test name", "Test last name")

        //WHEN
        val result = personDAO.save(testPerson).block()!!

        //THEN
        Assertions.assertThat(result)
            .hasNoNullFieldsOrProperties()
            .usingRecursiveComparison()
            .ignoringFields("id")
            .isEqualTo(testPerson)
    }

}
