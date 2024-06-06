package com.oniksen.project_for_students.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholderItem

@Dao
interface RoomPostsDao {

    @Query("SELECT * FROM saved_posts")
    suspend fun fetchAll(): List<PostsPlaceholderItem>

    @Insert
    suspend fun insertAll(posts: List<PostsPlaceholderItem>)
}