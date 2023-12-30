package com.example.appmonedero.data.network

import androidx.lifecycle.LiveData
import java.lang.Exception

sealed class Resource{
    data class Success<T>(var data: T?): Resource()
    data object Error:Resource()
}
