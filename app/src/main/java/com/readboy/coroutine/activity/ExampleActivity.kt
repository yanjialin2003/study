package com.readboy.coroutine.activity

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.readboy.coroutine.R
import com.readboy.coroutine.databinding.ActivityExampleBinding
import com.readboy.coroutine.viewmodel.ExampleViewModel

class ExampleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityExampleBinding
    private val vm: ExampleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.exampleViewModel = vm
        binding.lifecycleOwner = this

        binding.btn.setOnClickListener {
            vm.getUser("XXX")
        }
    }
}