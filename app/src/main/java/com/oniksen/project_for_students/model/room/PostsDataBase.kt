package com.oniksen.project_for_students.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholderItem

/**
 * Абстрактный класс, предоставляющий сущности БД
 * */
@Database(entities = [PostsPlaceholderItem::class], version = 1, exportSchema = false)
abstract class PostsDataBase: RoomDatabase() {
    abstract fun roomPostsDao(): RoomPostsDao
}