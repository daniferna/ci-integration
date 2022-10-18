package com.dfernandezaller.shared_expenses.persistence

import com.dfernandezaller.shared_expenses.persistence.data.MongoPerson
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.stereotype.Repository

@Repository
interface MongoPersonDAO : ReactiveMongoRepository<MongoPerson, Long>