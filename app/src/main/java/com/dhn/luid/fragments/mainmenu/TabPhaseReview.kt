package com.dhn.luid.fragments.mainmenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dhn.luid.R
import com.dhn.luid.adapters.TabPRAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class TabPhaseReview : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: TabPRAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab_phase_review, container, false)

        val bottomNav = activity?.findViewById<View>(R.id.bottomNavigationView)
        bottomNav?.isEnabled = false
        bottomNav?.visibility = View.GONE

        tabLayout = view.findViewById(R.id.tabLayout)
        viewPager = view.findViewById(R.id.viewPager2)
        adapter = TabPRAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Phase"
                1 -> tab.text = "Review"
            }
        }.attach()

        viewPager.isUserInputEnabled = false;

        return view
    }
}
