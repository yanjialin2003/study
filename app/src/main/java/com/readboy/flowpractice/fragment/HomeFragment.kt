package com.readboy.flowpractice.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.readboy.flowpractice.R
import com.readboy.flowpractice.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private val mBinding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.apply {
            btnFlowAndDownload.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_downloadFragment)
            }
            btnFlowAndRoom.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_userFragment)
            }
            btnFlowAndRetrofit.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_articleFragment)
            }
            btnStartFlow.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_numberFragment)
            }
            btnShareFlow.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_sharedFlowFragment)
            }
        }
    }
}