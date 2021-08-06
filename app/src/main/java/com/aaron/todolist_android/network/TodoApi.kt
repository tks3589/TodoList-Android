package com.aaron.todolist_android.network

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TodoApi {
    @FormUrlEncoded
    @POST("postTest")
    suspend fun login(@Field("id")id : String): Response<String>
}