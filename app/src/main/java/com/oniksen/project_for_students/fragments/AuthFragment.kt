package com.oniksen.project_for_students.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oniksen.project_for_students.R
import com.oniksen.project_for_students.databinding.AuthFragmentLayoutBinding

/**
 * В качестве параметра для родительского класса Fragment передаём layout нашего фрагмента для быстрого перехода из класса к его разметке.
 * (значок </> рядом с class)
 * Вообще layout передаётся для того, чтобы не переопределять метод onCreateView, но т.к. мы используем view binding, то обязаны его
 * переопределить) Поэтому из полезностей такого способа создания фрагмента остаётся быстрая навигация, а не долгий поиск в папке
 * со всеми разметками.
 * */
class AuthFragment: Fragment(R.layout.auth_fragment_layout) {
    private var _binding: AuthFragmentLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AuthFragmentLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Обработчик нажатия на кнопку "Авторизация"
        binding.authBtn.setOnClickListener {
            val inputLogin = binding.loginText.text.toString()
            val inputPass = binding.passText.text.toString()

            // Проверка правильности логина и пароля
            if (inputLogin == LOGIN && inputPass == PASSWORD) {
                // Навигация на фрагмент со списком постов
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, PostsListFragment.getInstance())
                    .commit()
            }
        }
    }

    companion object {
        private const val LOGIN = "testlogin"
        private const val PASSWORD = "testpass"

        /** Экземпляр класса AuthFragment */
        private var instance: AuthFragment? = null

        /**
         * Метод для создания и получения экземпляра этого класса. Такой подход хорош для экономии оперативной памяти устройства.
         * Ведь каждый раз при навигации на этот фрагмент (если мы используем fragmentManager) будет создаваться его экземпляр.
         * Но при вызове этого метода, будет браться уже ранее созданый.
         * */
        fun getInstance(): AuthFragment {
            if (instance == null) {
                instance = AuthFragment()
                return instance!!
            } else {
                return instance!!
            }
        }
    }
}