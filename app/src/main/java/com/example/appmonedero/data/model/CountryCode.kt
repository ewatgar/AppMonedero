package com.example.appmonedero.data.model

enum class CountryCode(val value:String) {
    ES("España"),
    AU("Australia"),
    UA("Ucrania"),
    US("Estados Unidos"),
    FR("Francia");

    companion object {
        fun getTypeAccountsList(): ArrayList<CountryCode> {
            return arrayListOf(ES, AU, UA, US, FR)
        }
    }

    override fun toString(): String {
        return value
    }
}