package com.dfernandezaller.shared_expenses.model.entities

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