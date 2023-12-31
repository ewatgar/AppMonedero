package com.example.appmonedero.data.model

data class Account(
    var countryCode: CountryCode,
    var entidad: Int,
    var oficina: Int,
    var dc: Int,
    var accountNum: Long,
    var balance: Int
) :Comparable<Account> {
    override fun compareTo(other: Account): Int {
        return when{
            this.entidad != other.entidad -> this.entidad.compareTo(other.entidad)
            this.oficina != other.oficina -> this.oficina.compareTo(other.oficina)
            this.dc != other.dc -> this.dc.compareTo(other.dc)
            else -> this.accountNum.compareTo(other.accountNum)
        }
    }
}
