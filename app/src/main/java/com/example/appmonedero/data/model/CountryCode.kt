package com.example.appmonedero.data.model

enum class CountryCode(val value:String) {
    ES("España"),
    AU("Australia"),
    UA("Ucrania"),
    US("Estados unidos"),
    FR("Francia");

    override fun toString(): String {
        return value
    }
}