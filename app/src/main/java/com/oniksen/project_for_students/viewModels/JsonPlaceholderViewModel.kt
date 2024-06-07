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
    /** Retrofit сервис для получения json данных. */
    private val service = RetrofitClient.jsonPlaceholderService

    /**
     * Flow хранящий список постов, получаемых от сервиса. Имеет модицикатор private чтобы извне нельзя было
     * изменить значение списка.
     * */
    private val _postsList: MutableStateFlow<PostsPlaceholder?> by lazy { MutableStateFlow(null) }
    // Метод asStateFlow предоставляет неизменяемый поток только для сбора из него данных
    val postsList = _postsList.asStateFlow()
    /** Метод запрашивает список постов */
    fun getPosts() {
        /*
        * Такая конструкция вызова (сама функция не suspend, а внутри себя вызывает suspend)
        * используется для того, чтобы не нужно было городить кучу lifecycleScope в нашем фрагменте.
        **/
        viewModelScope.launch { asyncFetchPosts() }
    }
    /**
     * Метод получения и обработки ответа от сервиса.
     * При помощи конструкции ... = coroutineScope { ... } выполняем блок кода внутри той же
     * корутины, откуда и произошёл вызов этой функции, чтобы не плодить кучу Job и не забивать ими
     * виртуальные потоки. Большим количеством Job ещё и управлять сложнее, а точнее отлавливать ошибки.
     * */
    private suspend fun asyncFetchPosts() = coroutineScope {
        val response = service.fetchPosts()
        if (response.isSuccessful) {
            _postsList.emit(response.body())
        }
    }
}