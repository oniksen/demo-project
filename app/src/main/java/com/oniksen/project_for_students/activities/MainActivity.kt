package com.oniksen.project_for_students.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.oniksen.project_for_students.R
import com.oniksen.project_for_students.databinding.ActivityMainBinding
import com.oniksen.project_for_students.fragments.AuthFragment
import com.oniksen.project_for_students.viewModels.JsonPlaceholderViewModel

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var placeholderViewModel: JsonPlaceholderViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initViewModel()
        openAuthFragment()
    }
    private fun initViewModel() {
        placeholderViewModel = ViewModelProvider(this)[JsonPlaceholderViewModel::class.java]
    }
    private fun openAuthFragment() {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, AuthFragment.getInstance())
            .commit()
    }
}