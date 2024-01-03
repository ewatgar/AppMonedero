package com.example.appmonedero.data.model

import android.content.Context
import com.example.appmonedero.R

enum class CountryCode(val stringResource:Int, val stringDefault: String) {
    ES(R.string.enum_countrycode_es,"España"),
    AU(R.string.enum_countrycode_au,"Australia"),
    UA(R.string.enum_countrycode_ua,"Ucrania"),
    US(R.string.enum_countrycode_us,"Estados Unidos"),
    FR(R.string.enum_countrycode_fr,"Francia");


    companion object{
        var context: Context? = null
    }
    override fun toString(): String {
        return context?.getString(stringResource) ?: stringDefault
    }
}