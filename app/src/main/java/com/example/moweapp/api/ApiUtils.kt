package com.example.moweapp.api

import com.example.moweapp.utils.Constants.BASE_URL

class ApiUtils {
    companion object{
        fun getApi(): MovieInterface {
            return RetrofitClient.getClient(BASE_URL).create(MovieInterface::class.java)
        }
    }
}