package com.readboy.flowpractice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.readboy.flowpractice.R
import com.readboy.flowpractice.databinding.FragmentNumberBinding
import com.readboy.flowpractice.databinding.FragmentSharedFlowBinding
import com.readboy.flowpractice.viewmodel.NumberViewModel
import com.readboy.flowpractice.viewmodel.SharedFlowViewModel
import kotlinx.coroutines.launch
import kotlin.getValue

class SharedFlowFragment : Fragment() {
    private val mBinding: FragmentSharedFlowBinding by lazy {
        FragmentSharedFlowBinding.inflate(layoutInflater)
    }

    private val mSharedFlowViewModel by viewModels<SharedFlowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            btnStart.setOnClickListener {
                mSharedFlowViewModel.startRefresh()
            }
            btnStop.setOnClickListener {
                mSharedFlowViewModel.stopRefresh()
            }
        }
    }
}