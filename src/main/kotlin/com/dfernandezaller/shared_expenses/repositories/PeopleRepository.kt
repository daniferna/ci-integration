package com.dfernandezaller.shared_expenses.repositories

import com.dfernandezaller.shared_expenses.model.dto.AggregatedSpentByPersonDTO
import com.dfernandezaller.shared_expenses.model.entities.Person
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux

interface PeopleRepository : ReactiveMongoRepository<Person, Long> {

    @Aggregation(
        pipeline = [
            "{ \$set: {  temp: {   name: '\$name',   lastName: '\$lastName'  } }}",
            "{ \$lookup: {  from: 'payment',  localField: 'temp',  foreignField: 'owner',  as: 'payments' }}",
            "{ \$project: {  name: '\$name',  lastName: '\$lastName',  totalSpent: {   \$sum: '\$payments.amount'  } }}"
        ]
    )
    fun getPeopleGroupedByAmountSpent(): Flux<AggregatedSpentByPersonDTO>
}