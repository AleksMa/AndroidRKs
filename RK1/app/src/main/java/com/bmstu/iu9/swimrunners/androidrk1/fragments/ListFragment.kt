package com.bmstu.iu9.swimrunners.androidrk1.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.bmstu.iu9.swimrunners.androidrk1.R
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {
    private lateinit var viewOfLayout: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewOfLayout = inflater.inflate(R.layout.fragment_list, container, false)
        viewOfLayout.buttonOpenSecond.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_hostFragment_to_secondFragment)
        )
        return viewOfLayout
    }
}