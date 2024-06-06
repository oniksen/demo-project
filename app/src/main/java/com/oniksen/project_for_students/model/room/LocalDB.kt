package com.oniksen.project_for_students.model.room

import android.content.Context
import androidx.room.Room
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholderItem

class LocalDB(context: Context) {

    private val instance = Room.databaseBuilder(
        context = context.applicationContext,
        klass = PostsDataBase::class.java,
        name = "saved_posts_database"
    ).build()

    private val dataDao = instance.roomPostsDao()

    suspend fun fetchAllPosts(): List<PostsPlaceholderItem> {
        return dataDao.fetchAll()
    }
    suspend fun saveList(posts: List<PostsPlaceholderItem>) {
        dataDao.insertAll(posts)
    }
}