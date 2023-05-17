package com.example.luid.fragments.mainmenu

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.ParentPhaseAdapter
import com.example.luid.adapters.ReviewAdapter
import com.example.luid.classes.ParentPhase
import com.example.luid.classes.Review
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.engWord
import com.example.luid.database.DBConnect.Companion.kapWord
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.tagWord
import com.example.luid.database.DBConnect.Companion.vsbty


// import com.example.luid.database.DBHelper

class ReviewFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val reviewList = ArrayList<ParentPhase>()
    private val adapter = ParentPhaseAdapter(reviewList)
    private lateinit var dbHelper: DBConnect
    private lateinit var data: List<Review>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_review, container, false)
        recyclerView = view.findViewById(R.id.reviewRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val reviewList = getword()
        val adapter = ReviewAdapter(reviewList)
        recyclerView.adapter = adapter

        return view
    }

    private fun getword(): List<Review> {

        val list = mutableListOf<Review>()
        val selectQuery = "SELECT * FROM $questions_tb WHERE $vsbty = 1"
        val db = DBConnect(requireContext()).readableDatabase

        var cursor: Cursor = db.rawQuery(selectQuery, null)

        try {


            if (cursor.moveToFirst()) {
                val kapIndex = cursor.getColumnIndex(kapWord)
                val engIndex = cursor.getColumnIndex(engWord)
                val tagIndex = cursor.getColumnIndex(tagWord)
                val vsbtyIndex = cursor.getColumnIndex(vsbty)

                 do{
                    val kap = cursor.getString(kapIndex)
                    val eng = cursor.getString(engIndex)
                    val tag = cursor.getString(tagIndex)
                    val vsbty = cursor.getInt(vsbtyIndex)

                    if (vsbty == 1) {
                        val review = Review(kap, eng, tag)
                        list.add(review)
                    }
                }while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }

        return list
    }


}