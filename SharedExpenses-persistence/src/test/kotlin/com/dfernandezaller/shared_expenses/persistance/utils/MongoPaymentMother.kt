package com.dfernandezaller.shared_expenses.com.dfernandezaller.shared_expenses.persistance.utils

import com.dfernandezaller.shared_expenses.persistence.data.MongoPayment
import com.dfernandezaller.shared_expenses.persistence.data.MongoPerson
import java.util.*
import kotlin.random.Random

class MongoPaymentMother {
    companion object {
        fun getSmallAmountMongoPayment(ownerName: String = "Test name"): MongoPayment {
            return MongoPayment(
                null, MongoPerson(null, ownerName, "Test last name"),
                10.2F, "Test description", Date()
            )
        }

        fun getListOfSmallMongoPayments(amount: Int = 3): MutableList<MongoPayment> {
            val payments = ArrayList<MongoPayment>(amount)
            for (i in 1..amount) {
                payments.add(
                    MongoPayment(
                        null, MongoPerson(null, "Test name #$i", "Test last name #$i"),
                        Random.nextFloat() * 10, "Test description #$i", Date()
                    )
                )
            }
            return payments
        }
    }
}