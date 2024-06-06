package com.oniksen.project_for_students.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholderItem

@Composable
fun ComposeList(
    postsList: List<PostsPlaceholderItem>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(postsList) {post ->
            Post(objectData = post)
        }
    }
}
@Composable
private fun Post(objectData: PostsPlaceholderItem) {
    Text(text = objectData.title)
}