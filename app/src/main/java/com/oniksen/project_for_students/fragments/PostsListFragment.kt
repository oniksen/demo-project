package com.oniksen.project_for_students.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.oniksen.project_for_students.R
import com.oniksen.project_for_students.compose.ComposeList
import com.oniksen.project_for_students.databinding.PostsListFragmentLayoutBinding
import com.oniksen.project_for_students.model.dataClasses.PostsPlaceholderItem
import com.oniksen.project_for_students.model.room.LocalDB
import com.oniksen.project_for_students.viewModels.JsonPlaceholderViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class PostsListFragment: Fragment(R.layout.posts_list_fragment_layout) {
    private var _binding: PostsListFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    private val placeholderViewModel: JsonPlaceholderViewModel by activityViewModels()
    private val postsList = mutableStateOf(emptyList<PostsPlaceholderItem>())
    private lateinit var db: LocalDB

    override fun onAttach(context: Context) {
        super.onAttach(context)

        db = LocalDB(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostsListFragmentLayoutBinding.inflate(inflater, container, false)

        placeholderViewModel.getPosts()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeList.apply {
            setContent {
                MaterialTheme {
                    ComposeList(postsList = postsList.value)
                }
            }
        }

        addListConsumer(placeholderViewModel)
        addMenuListeners()
    }
    private fun addListConsumer(viewModel: JsonPlaceholderViewModel) {
        lifecycleScope.launch {
            viewModel.postsList.collect { posts ->
                posts?.let {
                    val listPosts = posts as List<PostsPlaceholderItem>

                    postsList.value = listPosts
                }
            }
        }
    }
    private fun addMenuListeners() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.saveBtn -> lifecycleScope.launch {
                    savePosts(postsList.value)
                }
                R.id.showSaved -> {
                    lifecycleScope.launch {
                        val savedPosts = fetchSavedPosts()
                        Log.d(TAG, "savedPosts: $savedPosts")
                    }
                }
            }
            true
        }
    }

    private suspend fun savePosts(list: List<PostsPlaceholderItem>) = coroutineScope {
        db.saveList(posts = list)
    }
    private suspend fun fetchSavedPosts(): List<PostsPlaceholderItem> = coroutineScope {
        return@coroutineScope db.fetchAllPosts()
    }

    companion object {
        private const val TAG = "posts_list_fragment"
        private var instance: PostsListFragment? = null

        fun getInstance(): PostsListFragment {
            if (instance == null) {
                instance = PostsListFragment()
                return instance!!
            } else {
                return instance!!
            }
        }
    }
}