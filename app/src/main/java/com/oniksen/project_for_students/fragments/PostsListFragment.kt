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
    /*
    * Инициализация списка постов через compose State. Обязательно таким образом для того, чтобы compose функция,
    * в которую мы передадим список, знала, что после изменения списка в этой переменной, она должна перерисовать
    * себя. P.s. на этом принципе (рекомпозиция) построен весь compose.
    * */
    private val postsList = mutableStateOf(emptyList<PostsPlaceholderItem>())
    /*
    * Отложенная инициализация локальной базы данных, т.к. в этой области видимости во время выполнения программы,
    * ещё не дотсупен контекст, требуемый для экземпляра класса LocalDB.
    * */
    private lateinit var db: LocalDB

    override fun onAttach(context: Context) {
        super.onAttach(context)

        /*
        * Инициализация локальной базы данных.
        *
        * Почему использовали именно этот метод жизненного цикла фрагмента (onAttach)?
        * Потому что он самый первый, у которого при создании fragment, будет доступен контекст.
        * По идее можно использовать переопределение и другого метода, но главное, чтобы инициализация
        * БД произошла раньше первого её использования. Иначе вызов Exception.
        * */
        db = LocalDB(requireContext())
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostsListFragmentLayoutBinding.inflate(inflater, container, false)

        // Делаем запрос к ViewModel на скачивание постов.
        placeholderViewModel.getPosts()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.composeList.apply {
            // Стандартная конструкция вызова compose функций. И не только во View System, но и в compose файлахю
            setContent {
                MaterialTheme {
                    ComposeList(postsList = postsList.value)
                }
            }
        }

        addListConsumer(placeholderViewModel)
        addMenuListeners()
    }
    /** Метод собирает и обрабатывает результат получения списка постов. */
    private fun addListConsumer(viewModel: JsonPlaceholderViewModel) {
        // Создаём корутину
        lifecycleScope.launch {
            // Собираем данные из потока во ViewModel
            viewModel.postsList.collect { posts ->
                // Обычная проверка на null
                posts?.let {
                    /*
                     * Явно преобразовываем PostsPlaceholder к списку PostsPlaceholderItem.
                     * Он и так по сути им и является (см. код PostsPlaceholder), а точнее и правильнее сказать:
                     * PostsPlaceholder является декоратором для List<PostsPlaceholderItem> и нужен только
                     * для удобочитаемого использования в прграмме.
                     * Снова стандартная практика, чтоб не запутаться во всех списках и прочем подобном.
                     *
                     * Но т.к. я закодил compose функцию на использование именно списков, нужно явно наш
                     * PostsPlaceholder преобразовать обратно в список ¯\_(ツ)_/¯
                     */
                    val listPosts = posts as List<PostsPlaceholderItem>
                    // Меняем именно значение (value) compose-состояния нашего списка постов, чтоб сработала рекомпозиция
                    postsList.value = listPosts
                }
            }
        }
    }
    /** Обработчик нажатия на кнопки меню в toolbar */
    private fun addMenuListeners() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                // Обработчик кнопки сохранения
                R.id.saveBtn -> lifecycleScope.launch {
                    savePosts(postsList.value)
                }
                // Обработчик кнопки показа сохранённого значения
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

    /*
    * Функция suspend потому что работа с БД не должна происходить в основном потоке!!!
    * При помощи конструкции ... = coroutineScope { ... } выполняем код в той же корутине
    * откуда совершается вызов этой функции.
    * */
    private suspend fun savePosts(list: List<PostsPlaceholderItem>) = coroutineScope {
        db.saveList(posts = list)
    }
    /* Логика такая же, как и для savePosts(...) */
    private suspend fun fetchSavedPosts(): List<PostsPlaceholderItem> = coroutineScope {
        return@coroutineScope db.fetchAllPosts()
    }

    companion object {
        // Тэг нужен только для отладки.
        private const val TAG = "posts_list_fragment"

        /** Экземпляр класса AuthFragment */
        private var instance: PostsListFragment? = null

        /** Метод для создания и получения экземпляра этого класса. */
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