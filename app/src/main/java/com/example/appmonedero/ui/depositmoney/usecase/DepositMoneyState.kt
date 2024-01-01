package com.example.appmonedero.ui.depositmoney.usecase

sealed class DepositMoneyState {
    data object MoneyEmptyError : DepositMoneyState()
    data object MoneyFormatError : DepositMoneyState()
    data object MoneyDoubleOverLimit : DepositMoneyState()
    data object MoneyOverLimit : DepositMoneyState()
    data object Success: DepositMoneyState()
}