package com.oniksen.project_for_students.model.dataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("saved_posts")
data class PostsPlaceholderItem(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "body") val body: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "userId") val userId: Int
)