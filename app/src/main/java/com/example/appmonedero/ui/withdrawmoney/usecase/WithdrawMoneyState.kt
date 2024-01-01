package com.example.appmonedero.ui.withdrawmoney.usecase

sealed class WithdrawMoneyState {
    data object MoneyEmptyError : WithdrawMoneyState()
    data object MoneyFormatError : WithdrawMoneyState()
    data object NotEnoughBalanceError : WithdrawMoneyState()
    data object MoneyOverDoubleLimit : WithdrawMoneyState()
    data object MoneyOverLimit : WithdrawMoneyState()
    data object Success: WithdrawMoneyState()
}