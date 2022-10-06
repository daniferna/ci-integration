package com.dfernandezaller.shared_expenses.model

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document
data class Payment(
    @Id @GeneratedValue val id: String? = null,
    val owner: Person,
    val amount: Float,
    val description: String,
    val date: Date
)

@Document(collection = "people")
data class Person(
    @Id @GeneratedValue val id: String? = null,
    val name: String,
    val lastName: String
)

data class AggregatedSpentByPerson(
    val _id: String? = null,
    val name: String,
    val lastName: String,
    val totalSpent: Float
)

