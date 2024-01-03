package com.example.appmonedero

import android.app.Application
import com.example.appmonedero.data.model.CountryCode

class AppMonedero: Application() {
    override fun onCreate() {
        super.onCreate()
        //Se inicializa los elementos que necesitan el contexto
        CountryCode.context=applicationContext
    }
}