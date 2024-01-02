package com.example.appmonedero.ui.addaccount.usecase

sealed class AddAccountState {
    data object EntidadEmptyError :AddAccountState()
    data object OficinaEmptyError :AddAccountState()
    data object DcEmptyError :AddAccountState()
    data object AccountNumEmptyError :AddAccountState()
    data object EntidadFormatError :AddAccountState()
    data object OficinaFormatError :AddAccountState()
    data object DcFormatError :AddAccountState()
    data object AccountNumFormatError :AddAccountState()
    data object AccountExistsError: AddAccountState()
    data object Success: AddAccountState()


}