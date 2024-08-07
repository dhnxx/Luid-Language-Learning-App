package com.dhn.luid.fragments.mainmenu

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhn.luid.R
import com.dhn.luid.adapters.ReviewAdapter
import com.dhn.luid.adapters.LevelSwitchStateViewModel
import com.dhn.luid.classes.ParentPhase
import com.dhn.luid.classes.Review
import com.dhn.luid.database.DBConnect
import com.dhn.luid.database.DBConnect.Companion.diffs
import com.dhn.luid.database.DBConnect.Companion.engWord
import com.dhn.luid.database.DBConnect.Companion.kapWord
import com.dhn.luid.database.DBConnect.Companion.level
import com.dhn.luid.database.DBConnect.Companion.questions_tb
import com.dhn.luid.database.DBConnect.Companion.tagWord
import com.dhn.luid.database.DBConnect.Companion.vsbty


// import com.example.luid.database.DBHelper

class ReviewFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private val reviewList = ArrayList<ParentPhase>()
    private lateinit var dbHelper: DBConnect
    private lateinit var data: List<Review>

    //view model to store switch state
    private lateinit var levelSwitchStateViewModel: LevelSwitchStateViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_review, container, false)
        recyclerView = view.findViewById(R.id.reviewRecyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        // Retrieve ViewModel
        levelSwitchStateViewModel =
            ViewModelProvider(requireActivity())[LevelSwitchStateViewModel::class.java]


        val reviewList = getWord()
        val adapter = ReviewAdapter(reviewList)
        recyclerView.adapter = adapter

        return view
    }


    private fun getWord(): List<Review> {
        val list = mutableListOf<Review>()

        val switchState = levelSwitchStateViewModel.getSwitchState().value

        val selectQuery = generateSelectQuery(switchState)

        val db = DBConnect(requireContext()).readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
            cursor?.let {
                val kapIndex = it.getColumnIndex(kapWord)
                val engIndex = it.getColumnIndex(engWord)
                val tagIndex = it.getColumnIndex(tagWord)
                val vsbtyIndex = it.getColumnIndex(vsbty)
                val dfIndex = it.getColumnIndex(diffs)

                while (it.moveToNext()) {
                    val kap = it.getString(kapIndex)
                    val eng = it.getString(engIndex)
                    val tag = it.getString(tagIndex)
                    val vsbty = it.getInt(vsbtyIndex)
                    val df = it.getInt(dfIndex)

                    val review = Review(kap, eng, tag, df)
                    list.add(review)

                }
            }


        } catch (e: Exception) {

            e.printStackTrace()
        } finally {
            cursor?.close()
            db.close()
        }

        return list
    }

    private fun generateSelectQuery(switchState: String?): String {
        return when (switchState) {
            "Level 1" -> "SELECT * FROM $questions_tb WHERE $level = 1 AND $vsbty = 1"
            "Level 2" -> "SELECT * FROM $questions_tb WHERE $level = 2 AND $vsbty = 1"
            "Level 3" -> "SELECT * FROM $questions_tb WHERE $level = 3 AND $vsbty = 1"
            "Level 4" -> "SELECT * FROM $questions_tb WHERE $level = 4 AND $vsbty = 1"
            else -> ""
        }

    }
}
