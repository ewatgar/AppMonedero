package com.example.appmonedero.data.model

enum class CountryCode(val value:String) {
    ES("España"),
    AU("Australia"),
    UA("Ucrania"),
    US("Estados unidos"),
    FR("Francia");
    //TODO enum countrycode traducir

    override fun toString(): String {
        return value
    }
}