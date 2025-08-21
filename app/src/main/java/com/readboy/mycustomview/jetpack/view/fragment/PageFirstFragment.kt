package com.readboy.mycustomview.jetpack.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.readboy.mycustomview.R

class PageFirstFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_page_first, container, false)
    }

    lateinit var btn1: Button
    lateinit var btn2: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn1 = view.findViewById(R.id.jump_to_page2)
        btn2 = view.findViewById(R.id.back_main_page)

        btn1.setOnClickListener {
            val navOption = navOptions {
                anim {
                    enter = R.anim.common_slide_in_right
                    exit = R.anim.common_slide_out_left
                    popEnter = R.anim.common_slide_in_left
                    popExit = R.anim.common_slide_out_right
                }
            }
            findNavController().navigate(R.id.nav_graph_page_second, null, navOption)
        }

        btn2.setOnClickListener {
            findNavController().navigateUp()
        }
    }
}