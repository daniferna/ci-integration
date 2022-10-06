package com.dfernandezaller.shared_expenses.utils

import com.dfernandezaller.shared_expenses.model.AggregatedSpentByPerson
import com.dfernandezaller.shared_expenses.model.Payment
import com.dfernandezaller.shared_expenses.model.Person
import java.time.Instant
import java.util.*


fun getTestPayment(): Payment {
    return getTestPayment("Test name", (Math.random() * 10F).toFloat())
}

fun getTestPayment(name: String, amount: Float): Payment {
    return Payment(
        null,
        Person(null, name, "Test last name"),
        amount,
        "Test description",
        Date(Instant.now().toEpochMilli())
    )
}

fun getTestPerson(name: String): Person {
    return Person(null, name, "Test last name")
}

fun getAggregatedSpentByPerson(name: String, amount: Float): AggregatedSpentByPerson {
    return AggregatedSpentByPerson(null, name, "Test lastName", amount)
}