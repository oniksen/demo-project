package com.oniksen.project_for_students.model.room

import android.content.Context
import androidx.room.Room
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholderItem

/* Класс для работы с локальной БД через библиотеку Room */
class LocalDB(context: Context) {

    /*
    * Создание БД. Помечаем модификатором private чтобы напрямую нельзя было
    * взаимодействовать из других классов, а только по средстам предоставленных
    * методов.
    * */
    private val instance = Room.databaseBuilder(
        context = context.applicationContext,
        klass = PostsDataBase::class.java,
        name = "saved_posts_database"
    ).build()

    // Получаем абстрактный интерфейс для работы с базой данных
    private val dataDao = instance.roomPostsDao()

    /** Получить сохранённые посты из БД. */
    suspend fun fetchAllPosts(): List<PostsPlaceholderItem> {
        return dataDao.fetchAll()
    }
    /** Сохранить посты в БД. */
    suspend fun saveList(posts: List<PostsPlaceholderItem>) {
        dataDao.insertAll(posts)
    }
}