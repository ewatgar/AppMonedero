package com.example.appmonedero.data.model

import java.io.Serializable

data class Customer(val username: String, val pin:Int, val accountList:ArrayList<Account>):Serializable
