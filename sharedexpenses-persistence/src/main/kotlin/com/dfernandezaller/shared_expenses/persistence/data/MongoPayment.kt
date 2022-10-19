package com.dfernandezaller.shared_expenses.persistence.data

import org.springframework.data.mongodb.core.mapping.Document
import java.util.*
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document(collection = "payment")
data class MongoPayment(
    @Id @GeneratedValue val id: String?,
    val owner: MongoPerson,
    val amount: Float,
    val description: String,
    val date: Date
)