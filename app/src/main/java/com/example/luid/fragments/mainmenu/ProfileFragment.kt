package com.example.luid.fragments.mainmenu

import android.database.Cursor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.example.luid.R
import com.example.luid.classes.Achievement
import com.example.luid.database.DBConnect
import com.example.luid.database.DBConnect.Companion.achievements_tb


class ProfileFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Dynamic achievements card view code.
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val achvLinearLayout = view.findViewById<LinearLayout>(R.id.achvlinearLayout)
        val numberOfCards = 5
        val achContent = getAchContent()

        for (i in 0 until numberOfCards) {
            val cardView = layoutInflater.inflate(R.layout.achvcardview, achvLinearLayout, false)
            val titleTextView = cardView.findViewById<TextView>(R.id.achvcurrlvl)
            val progressBar = cardView.findViewById<ProgressBar>(R.id.achvprogressBar)
            val progressTextView = cardView.findViewById<TextView>(R.id.achvprogresstxt)
            val achTitleTextView = cardView.findViewById<TextView>(R.id.achvtitle)
            val descriptionTextView = cardView.findViewById<TextView>(R.id.achvdesc)

            when (i) {
                0 -> {
                    titleTextView.text = achContent[0].currLevel
                    //progressBar
                    progressTextView.text = "${achContent[0].currLevel}/${achContent[0].maxValue}"
                    achTitleTextView.text = achContent[0].achName
                    descriptionTextView.text = achContent[0].description
                }
                1 -> {
                    titleTextView.text = achContent[1].currLevel
                    //progressBar
                    progressTextView.text = "${achContent[1].currLevel}/${achContent[1].maxValue}"
                    achTitleTextView.text = achContent[1].achName
                    descriptionTextView.text = achContent[1].description
                }
                2 -> {
                    titleTextView.text = achContent[2].currLevel
                    //progressBar
                    progressTextView.text = "${achContent[2].currLevel}/${achContent[2].maxValue}"
                    achTitleTextView.text = achContent[2].achName
                    descriptionTextView.text = achContent[2].description
                }
                3 -> {
                    titleTextView.text = achContent[3].currLevel
                    //progressBar
                    progressTextView.text = "${achContent[3].currLevel}/${achContent[3].maxValue}"
                    achTitleTextView.text = achContent[3].achName
                    descriptionTextView.text = achContent[3].description
                }
                4 -> {
                    titleTextView.text = achContent[4].currLevel
                    //progressBar
                    progressTextView.text = "${achContent[4].currLevel}/${achContent[4].maxValue}"
                    achTitleTextView.text = achContent[4].achName
                    descriptionTextView.text = achContent[4].description
                }
            }
            achvLinearLayout.addView(cardView)
        }
        return view
    }

    fun getAchContent(): List<Achievement>{
        val list = mutableListOf<Achievement>()
        val selectQuery = "SELECT * FROM achievements"
        val db = DBConnect(requireContext()).writableDatabase
        val cursor : Cursor = db.rawQuery(selectQuery, null)

        try{
            val achName = cursor.getColumnIndex("achievement_name")
            val description = cursor.getColumnIndex("description")
            val currLevel = cursor.getColumnIndex("current_level")
            val maxValue = cursor.getColumnIndex("maximum_value")

            while (cursor.moveToNext()){
                val name = cursor.getString(achName)
                val desc = cursor.getString(description)
                val clevel = cursor.getInt(currLevel).toString()
                val mxval = cursor.getInt(maxValue).toString()

                val content = Achievement(name, desc, clevel, mxval)
                list.add(content)
            }

        }catch (e: Exception){
            e.printStackTrace()
        }finally {
            cursor.close()
            db.close()
        }

        return list
    }

}









