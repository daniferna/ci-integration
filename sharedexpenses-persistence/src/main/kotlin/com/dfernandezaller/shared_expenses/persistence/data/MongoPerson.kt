package com.dfernandezaller.shared_expenses.persistence.data

import org.springframework.data.mongodb.core.mapping.Document
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Document(collection = "person")
data class MongoPerson(
    @Id @GeneratedValue val id: String?,
    val name: String,
    val lastName: String
)
