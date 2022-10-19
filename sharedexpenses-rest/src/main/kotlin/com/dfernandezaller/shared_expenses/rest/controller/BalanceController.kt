package com.dfernandezaller.shared_expenses.rest.controller

import com.dfernandezaller.shared_expenses.domain.model.dto.BalanceDTO
import com.dfernandezaller.shared_expenses.domain.primary.ports.BalanceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/balance")
class BalanceController(private val balanceService: BalanceService) {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    fun getBalance(): Flux<BalanceDTO> {
        return balanceService.getBalance()
    }

}