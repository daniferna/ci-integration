package com.dfernandezaller.shared_expenses.utils

import com.dfernandezaller.shared_expenses.model.dto.AggregatedSpentByPersonDTO
import com.dfernandezaller.shared_expenses.model.dto.PersonalBalanceDTO
import com.dfernandezaller.shared_expenses.model.entities.Payment
import com.dfernandezaller.shared_expenses.model.entities.Person
import reactor.core.publisher.Flux
import java.time.Instant
import java.util.*
import kotlin.random.Random

fun getTestPaymentFlux(amount: Int = 3): Flux<Payment> {
    val payments = ArrayList<Payment>()
    for (i in 1..amount) {
        payments.add(getTestPayment())
    }
    return Flux.fromIterable(payments)
}

fun getTestBalanceFlux(amount: Int = 3): Flux<PersonalBalanceDTO> {
    val balances = ArrayList<PersonalBalanceDTO>()
    for (i in 1..amount) {
        balances.add(getTestBalanceDto())
    }
    return Flux.fromIterable(balances)
}

fun getTestBalanceDto(): PersonalBalanceDTO {
    return PersonalBalanceDTO(
        "Test name ${Random.nextBits(2)}",
        "Test lastName ${Random.nextBits(2)}",
        Random.nextFloat() * 10)
}

fun getTestPayment(): Payment {
    return getTestPayment("Test name", Random.nextFloat() * 10)
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

fun getTestPayments(amount: Int = 3): MutableList<Payment> {
    val payments = ArrayList<Payment>()
    for (i in 1..amount) {
        payments.add(getTestPayment("Test name $i", i.toFloat()))
    }
    return payments
}

fun getTestPerson(name: String): Person {
    return Person(null, name, "Test last name")
}

fun getTestPeople(amount: Int = 3): List<Person> {
    val people = ArrayList<Person>()
    for (i in 1..amount) {
        people.add(getTestPerson("Test name $i"))
    }
    return people
}

fun getAggregatedSpentByPerson(name: String, amount: Float): AggregatedSpentByPersonDTO {
    return AggregatedSpentByPersonDTO(null, name, "Test lastName", amount)
}