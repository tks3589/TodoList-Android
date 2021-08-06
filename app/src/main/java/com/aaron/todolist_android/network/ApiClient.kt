package com.aaron.todolist_android.network

import kotlinx.coroutines.delay
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.lang.Exception
import java.util.concurrent.TimeUnit

class ApiClient {
    private val okHttp: Call.Factory
    private val retrofit: Retrofit
    private val todoApi: TodoApi

    constructor(baseUrl: String = "http://172.105.216.243:529/todolist/api/"){
        this.okHttp = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { setLevel(HttpLoggingInterceptor.Level.BODY); })
            .connectTimeout(10,TimeUnit.SECONDS)
            .build()
        this.retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(this.okHttp)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        this.todoApi = retrofit.create(TodoApi::class.java)
    }

    suspend fun login(id: String): String{
        return try {
            val response = todoApi.login(id)
            if(response.isSuccessful){
                response.body()!!
            }else{
                "error"
            }
        }catch (e: Exception){
            "error"
        }
    }
}