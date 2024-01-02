package com.example.appmonedero.data.model

data class Transaction(var customer: Customer, var account: Account, var deposit: Boolean, var money: Int, var cancelled: Boolean)
