package com.chocolatecake.todoapp.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import com.chocolatecake.todoapp.databinding.FragmentHomeBinding
import com.chocolatecake.todoapp.ui.fragment.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    override val inflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding =
        FragmentHomeBinding::inflate
}