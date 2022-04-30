package com.example.composedemo2.data.retrofit

import com.example.composedemo2.data.Session
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    @GET("Sessions.json")
    suspend fun getSessionList(): List<Session>

    companion object {
        var api: ApiInterface? = null
        fun getInstance(): ApiInterface {
            if(api == null) {
                api = Retrofit.Builder()
                    .baseUrl("https://gist.githubusercontent.com/AJIEKCX/901e7ae9593e4afd136abe10ca7d510f/raw/61e7c1f037345370cf28b5ae6fdaffdd9e7e18d5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiInterface::class.java)
            }
            return api!!
        }
    }
}




