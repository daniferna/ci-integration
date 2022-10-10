package com.dfernandezaller.shared_expenses.model.entities

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document(collection = "people")
data class Person(
    @Id @GeneratedValue val id: String? = null,
    val name: String,
    val lastName: String
)