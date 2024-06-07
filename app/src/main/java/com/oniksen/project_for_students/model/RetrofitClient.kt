package com.oniksen.project_for_students.model

import com.oniksen.project_for_students.services.JsonPlaceholder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Клиент для получения сервиса, работающего с API.
 */
object RetrofitClient {
    /*
    * Самые базовые настройки Retrofit.
    *
    * GsonConverterFactory класс используется для автоматической конвертации
    * полученного Json файла в подготовленный data class.
    * */
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val jsonPlaceholderService: JsonPlaceholder = retrofit.create(JsonPlaceholder::class.java)
}