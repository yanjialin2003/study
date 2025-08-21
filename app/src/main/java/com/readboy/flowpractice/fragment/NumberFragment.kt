package com.readboy.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.readboy.flowpractice.databinding.FragmentNumberBinding
import com.readboy.flowpractice.viewmodel.NumberViewModel
import kotlinx.coroutines.launch

class NumberFragment : Fragment() {
    private val mBinding: FragmentNumberBinding by lazy {
        FragmentNumberBinding.inflate(layoutInflater)
    }

    private val mNumberViewModel by viewModels<NumberViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            btnIncrement.setOnClickListener {
                mNumberViewModel.increment()
            }
            btnDecrement.setOnClickListener {
                mNumberViewModel.decrement()
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            mNumberViewModel.number.collect {
                mBinding.tvNumber.text = "$it"
            }
        }
    }
}