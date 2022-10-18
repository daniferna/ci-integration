package com.dfernandezaller.shared_expenses.domain.utils

import com.dfernandezaller.shared_expenses.domain.model.dto.BalanceDTO
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import kotlin.random.Random

class BalanceMother {
    companion object {
        fun getListOfBalanceDTOs(amount: Int = 3): List<BalanceDTO> {
            val balances = ArrayList<BalanceDTO>(amount)
            for (i in 1..amount) {
                balances.add(
                    BalanceDTO.Builder()
                        .withName("Test name #${i}")
                        .withAmount(Random.nextFloat() * 10)
                        .build()
                )
            }
            return balances
        }

        fun getFluxOfNormalBalanceDTOs(amount: Int = 3): Flux<BalanceDTO> {
            return getListOfBalanceDTOs(amount).toFlux()
        }
    }
}