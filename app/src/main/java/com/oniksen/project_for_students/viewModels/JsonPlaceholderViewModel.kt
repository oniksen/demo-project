package com.oniksen.project_for_students.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oniksen.project_for_students.model.RetrofitClient
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholder
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class JsonPlaceholderViewModel: ViewModel() {
    private val service = RetrofitClient.jsonPlaceholderService

    private val _postsList: MutableStateFlow<PostsPlaceholder?> by lazy { MutableStateFlow(null) }

    val postsList = _postsList.asStateFlow()

    fun getPosts() {
        viewModelScope.launch { asyncFetchPosts() }
    }

    private suspend fun asyncFetchPosts() = coroutineScope {
        val response = service.fetchPosts()
        if (response.isSuccessful) {
            _postsList.emit(response.body())
        }
    }
}