package com.dfernandezaller.shared_expenses.model.dto

data class AggregatedSpentByPersonDTO(
    val _id: String? = null,
    val name: String,
    val lastName: String,
    val totalSpent: Float
)