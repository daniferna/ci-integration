package com.dfernandezaller.shared_expenses.domain.logic

import com.dfernandezaller.shared_expenses.domain.primary.ports.BalanceService
import com.dfernandezaller.shared_expenses.domain.utils.BalanceMother
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import reactor.core.publisher.Flux

class BalanceServiceTest {

    private val balanceServiceMock: BalanceService = Mockito.mock(BalanceService::class.java)

    @Test
    fun getBalance() {
        //GIVEN
        val balances = BalanceMother.getListOfBalanceDTOs()
        Mockito.`when`(balanceServiceMock.getBalance()).thenReturn(Flux.fromIterable(balances))

        //WHEN
        val result = balanceServiceMock.getBalance().collectList().block()!!

        //THEN
        Assertions.assertThat(result)
            .containsAll(balances)
    }
}