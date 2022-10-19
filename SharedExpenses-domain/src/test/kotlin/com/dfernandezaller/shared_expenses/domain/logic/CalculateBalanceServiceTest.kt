package com.dfernandezaller.shared_expenses.domain.logic

import com.dfernandezaller.shared_expenses.domain.utils.BalanceMother
import com.dfernandezaller.shared_expenses.domain.utils.PaymentMother
import com.dfernandezaller.shared_expenses.domain.utils.PersonMother
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`

class CalculateBalanceServiceTest {

    private val calculateBalanceServiceMock: CalculateBalanceService = Mockito.mock(CalculateBalanceService::class.java)

    @Test
    fun calculateBalance() {
        //GIVEN
        val payments = PaymentMother.getFluxOfSixSmallPaymentsDTOsWithFourPeople()
        val people = PersonMother.getFluxOfNormalPersonDTO(5)
        val balances = BalanceMother.getFluxOfNormalBalanceDTOs(5)

        `when`(calculateBalanceServiceMock.calculateBalance(payments, people)).thenReturn(balances)

        //WHEN
        val result = calculateBalanceServiceMock.calculateBalance(payments, people).collectList().block()!!

        //THEN
        Assertions.assertThat(result)
            .containsAll(balances.toIterable())
    }
}