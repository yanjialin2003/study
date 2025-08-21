package com.readboy.flowpractice.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.readboy.flowpractice.adapter.UserAdapter
import com.readboy.flowpractice.databinding.FragmentUserBinding
import com.readboy.flowpractice.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class UserFragment : Fragment() {
    private val mBinding: FragmentUserBinding by lazy {
        FragmentUserBinding.inflate(layoutInflater)
    }

    private val mUserViewModel by viewModels<UserViewModel>()
    private lateinit var mUserAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mUserAdapter = UserAdapter(requireContext())
        mBinding.rvUser.adapter = mUserAdapter
        mBinding.rvUser.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        mBinding.apply {
            btnInsert.setOnClickListener {
                mUserViewModel.insertUser(
                    etUid.text.toString(),
                    etName.text.toString(),
                    etAge.text.toString()
                )
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                mUserViewModel.getAllUser().collect { value ->
                    mUserAdapter.setData(value)
                }
            }
        }
    }
}