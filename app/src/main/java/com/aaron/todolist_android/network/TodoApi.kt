package com.aaron.todolist_android.network

import com.aaron.todolist_android.database.TodoItem
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TodoApi {
    @FormUrlEncoded
    @POST("postTest")
    suspend fun login(@Field("id")id : String): Response<String>

    @FormUrlEncoded
    @POST("findAll")
    suspend fun findAll(@Field("id")id : String): Response<List<TodoItem>>

    @FormUrlEncoded
    @POST("findAllRecycled")
    suspend fun findAllRecycled(@Field("id")id : String): Response<List<TodoItem>>

    @FormUrlEncoded
    @POST("insert")
    suspend fun insert(@Field("id")id:String,@Field("data")data:String): Response<String>

    @FormUrlEncoded
    @POST("update")
    suspend fun update(@Field("id")id:String,@Field("data")data:String): Response<String>

    @FormUrlEncoded
    @POST("delete")
    suspend fun delete(@Field("id")id:String,@Field("data")data:String): Response<String>
}