package com.oniksen.project_for_students.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.oniksen.project_for_students.R
import com.oniksen.project_for_students.databinding.AuthFragmentLayoutBinding

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

        binding.authBtn.setOnClickListener {
            val inputLogin = binding.loginText.text.toString()
            val inputPass = binding.passText.text.toString()

            if (inputLogin == LOGIN && inputPass == PASSWORD) {
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, PostsListFragment.getInstance())
                    .commit()
            }
        }
    }

    companion object {
        private const val LOGIN = "testlogin"
        private const val PASSWORD = "testpass"

        private var instance: AuthFragment? = null

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