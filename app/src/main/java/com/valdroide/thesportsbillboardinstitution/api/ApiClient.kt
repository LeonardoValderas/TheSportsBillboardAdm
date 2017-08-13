package com.valdroide.thesportsbillboardinstitution.api

import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit


class ApiClient {
    //private final static String BASE_URL = "http://myd.esy.es/my_citys_shops_adm/";
    //Emulator
    //private final static String BASE_URL = "http://10.0.2.2:8080/the_sports_billboard_adm/";
    //Test
    //private final static String BASE_URL = "http://127.0.0.1:8080/the_sports_billboard_adm/";
    //Genymotion
    private val BASE_URL = "http://10.0.3.2:8080/the_sports_billboard_adm/"

    fun ApiClient(): Retrofit{
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(setTimeOut())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun setTimeOut(): OkHttpClient {
        return OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    fun getApiService(): ApiService = ApiClient().create(ApiService::class.java)
}