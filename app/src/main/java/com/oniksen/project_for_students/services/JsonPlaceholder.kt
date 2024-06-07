package com.oniksen.project_for_students.services

import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholder
import retrofit2.Response
import retrofit2.http.GET

interface JsonPlaceholder {

    @GET("posts")
    suspend fun fetchPosts(): Response<PostsPlaceholder>
}