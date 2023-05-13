package com.example.luid.fragments.mainmenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.ParentPhaseAdapter
import com.example.luid.adapters.SwitchStateViewModel
import com.example.luid.classes.ParentPhase
import com.example.luid.database.DBHelper

class ReviewFragment : Fragment() {


    //view model to store switch state
    private lateinit var switchStateViewModel: SwitchStateViewModel

    // for Review list display
    private lateinit var recyclerView: RecyclerView
    private val reviewList = ArrayList<ParentPhase>()
    private val adapter = ParentPhaseAdapter(reviewList)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_review, container, false)

        recyclerView = view.findViewById(R.id.reviewRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //Database initialization
        val db = DBHelper(requireContext())






        return view
    }


}