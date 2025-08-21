package com.readboy.flowpractice.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.readboy.flowpractice.databinding.FragmentDownloadBinding
import com.readboy.flowpractice.download.DownloadManager
import com.readboy.flowpractice.download.DownloadStatus
import kotlinx.coroutines.launch

import java.io.File

class DownloadFragment : Fragment() {

    val URL = ""

    private val mBinding: FragmentDownloadBinding by lazy {
        FragmentDownloadBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.btnDownload.setOnClickListener {
            // 需要涉及视图操作，放在viewLifecycleOwner的lifecycleScope里，而不是fragment的lifecycleScope里
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.CREATED) {
                    context?.apply {
                        val file = File(getExternalFilesDir(null)?.path, "test.png")
                        DownloadManager.download(URL, file).collect { status ->
                            when (status) {
                                is DownloadStatus.Progress -> {
                                    mBinding.apply {
                                        progress.progress = status.progress
                                        tvProgress.text = "${status.progress}%"
                                    }
                                }

                                is DownloadStatus.Error -> {
                                    mBinding.progress.progress = 0
                                    Toast.makeText(context, "下载失败", Toast.LENGTH_SHORT).show()
                                }

                                is DownloadStatus.Done -> {
                                    mBinding.progress.progress = 100
                                    mBinding.tvProgress.text = "100%"
                                    Toast.makeText(context, "下载完成", Toast.LENGTH_SHORT).show()
                                }

                                else -> {
                                    Log.d("TAG", "下载失败")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}