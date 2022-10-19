package com.dfernandezaller.shared_expenses

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SharedExpensesApp {}

fun main(args: Array<String>) {
    runApplication<SharedExpensesApp>(*args)
}