package com.example.luid.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.luid.PhaseFragment
import com.example.luid.ReviewFragment

class PhaseReviewAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> PhaseFragment()
            1 -> ReviewFragment()
            else -> PhaseFragment()
        }
    }
}
