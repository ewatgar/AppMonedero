package com.example.appmonedero.data.model

import java.io.Serializable

data class Account(
    var countryCode: CountryCode,
    var entidad: Int,
    var oficina: Int,
    var dc: Int,
    var accountNum: Long,
    var balance: Int
) :Comparable<Account>, Serializable{
    override fun compareTo(other: Account): Int {
        return when{
            this.entidad != other.entidad -> this.entidad.compareTo(other.entidad)
            this.oficina != other.oficina -> this.oficina.compareTo(other.oficina)
            this.dc != other.dc -> this.dc.compareTo(other.dc)
            else -> this.accountNum.compareTo(other.accountNum)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Account

        if (countryCode != other.countryCode) return false
        if (entidad != other.entidad) return false
        if (oficina != other.oficina) return false
        if (dc != other.dc) return false
        if (accountNum != other.accountNum) return false

        return true
    }
}
