package com.dfernandezaller.shared_expenses

import com.dfernandezaller.shared_expenses.model.AggregatedSpentByPerson
import com.dfernandezaller.shared_expenses.model.Payment
import com.dfernandezaller.shared_expenses.model.Person
import org.springframework.data.mongodb.repository.Aggregation
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import reactor.core.publisher.Flux


interface PaymentRepository : ReactiveMongoRepository<Payment, Long>

interface PeopleRepository : ReactiveMongoRepository<Person, Long> {

    @Aggregation(
        pipeline = [
            "{ \$set: {  temp: {   name: '\$name',   lastName: '\$lastName'  } }}",
            "{ \$lookup: {  from: 'payment',  localField: 'temp',  foreignField: 'owner',  as: 'payments' }}",
            "{ \$project: {  name: '\$name',  lastName: '\$lastName',  totalSpent: {   \$sum: '\$payments.amount'  } }}"
        ]
    )
    fun getPeopleGroupedByAmountSpent(): Flux<AggregatedSpentByPerson>
}