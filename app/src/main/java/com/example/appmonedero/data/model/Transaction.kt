package com.example.appmonedero.data.model

data class Transaction(var customer: Customer, var account: Account, var transaccionType: TransaccionType, var money: Int)
