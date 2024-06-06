package com.oniksen.project_for_students.model

import com.oniksen.project_for_students.services.JsonPlaceholder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val jsonPlaceholderService = retrofit.create(JsonPlaceholder::class.java)
}