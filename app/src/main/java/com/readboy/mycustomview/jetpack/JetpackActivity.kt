package com.readboy.mycustomview.jetpack

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.readboy.mycustomview.jetpack.viewModel.MyViewModel
import com.readboy.mycustomview.R
import com.readboy.mycustomview.databinding.ActivityJetpackBinding

class JetpackActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJetpackBinding
    private lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 设置Data Binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jetpack)
        viewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        // 将ViewModel绑定到布局
        binding.myViewModel = viewModel
        binding.lifecycleOwner = this
    }
}