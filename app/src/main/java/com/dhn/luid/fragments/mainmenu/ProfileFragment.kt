package com.dhn.luid.fragments.mainmenu

import android.content.Context
import android.database.Cursor
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Base64
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import com.dhn.luid.R
import com.dhn.luid.classes.Achievement
import com.dhn.luid.database.DBConnect
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

class ProfileFragment : Fragment() {
    private lateinit var name: TextView
    private lateinit var fbauth: FirebaseAuth


    private lateinit var dbref: DatabaseReference
    private lateinit var textview: TextView
    private lateinit var textview1: TextView
    private lateinit var textview2: TextView
    private lateinit var textview3: TextView
    private lateinit var avatarImage: ImageView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Dynamic achievements card view code.
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        fbauth = FirebaseAuth.getInstance()
        name = view.findViewById(R.id.userName)

        if (fbauth.currentUser != null) {
            val sharedPref = requireContext().getSharedPreferences("loginPrefs", 0)
            val uname = sharedPref.getString("uname", "")
            name.text = "$uname"
        } else {
            name.text = "Guest"
        }


        val achvLinearLayout = view.findViewById<LinearLayout>(R.id.achvlinearLayout)
        val numberOfCards = 5
        val achContent = getAchContent()

        textview1 = view.findViewById(R.id.textStats1)
        textview2 = view.findViewById(R.id.textStats2)
        textview3 = view.findViewById(R.id.textStats3)
        avatarImage = view.findViewById(R.id.avatarImage)

//
        currentlvl()
        dayStreak()
        accuracy()


        for (i in 0 until numberOfCards) {
            val cardView = layoutInflater.inflate(R.layout.achvcardview, achvLinearLayout, false)
            cardView.elevation = 10f
            val progressBar = cardView.findViewById<ProgressBar>(R.id.achvprogressBar)
            val progressTextView = cardView.findViewById<TextView>(R.id.achvprogresstxt)
            val achTitleTextView = cardView.findViewById<TextView>(R.id.achvtitle)
            val descriptionTextView = cardView.findViewById<TextView>(R.id.achvdesc)
            val achvImage = cardView.findViewById<ImageView>(R.id.achvImage)

            when (i) {
                0 -> {
                    progressBar.progress = achContent[0].currLevel.toInt()
                    progressBar.max = achContent[0].maxValue.toInt()
                    progressTextView.text = "${achContent[0].currLevel}/${achContent[0].maxValue}"
                    achTitleTextView.text = achContent[0].achName
                    descriptionTextView.text = achContent[0].description
                    achvImage.setImageResource(R.drawable.fire)
                }

                1 -> {
                    progressBar.progress = achContent[1].currLevel.toInt()
                    progressBar.max = achContent[1].maxValue.toInt()
                    progressTextView.text = "${achContent[1].currLevel}/${achContent[1].maxValue}"
                    achTitleTextView.text = achContent[1].achName
                    descriptionTextView.text = achContent[1].description
                    achvImage.setImageResource(R.drawable.aim)
                }

                2 -> {
                    progressBar.progress = achContent[2].currLevel.toInt()
                    progressBar.max = achContent[2].maxValue.toInt()
                    progressTextView.text = "${achContent[2].currLevel}/${achContent[2].maxValue}"
                    achTitleTextView.text = achContent[2].achName
                    descriptionTextView.text = achContent[2].description
                    achvImage.setImageResource(R.drawable.trophy)
                }

                3 -> {
                    progressBar.progress = achContent[3].currLevel.toInt()
                    progressBar.max = achContent[3].maxValue.toInt()
                    progressTextView.text = "${achContent[3].currLevel}/${achContent[3].maxValue}"
                    achTitleTextView.text = achContent[3].achName
                    descriptionTextView.text = achContent[3].description
                    achvImage.setImageResource(R.drawable.clock)
                }

                4 -> {
                    progressBar.progress = achContent[4].currLevel.toInt()
                    progressBar.max = achContent[4].maxValue.toInt()
                    progressTextView.text = "${achContent[4].currLevel}/${achContent[4].maxValue}"
                    achTitleTextView.text = achContent[4].achName
                    descriptionTextView.text = achContent[4].description
                    achvImage.setImageResource(R.drawable.instagram_like_notification)
                }
            }
            achvLinearLayout.addView(cardView)
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        avatarImage = view.findViewById(R.id.avatarImage)
    }

    override fun onResume() {
        super.onResume()

        // retrieve image from shared pref
        val sharedPreferences = requireContext().getSharedPreferences("sharedpref", Context.MODE_PRIVATE)
        val base64Image = sharedPreferences.getString("imageKey", "")

        // Convert the base64-encoded string to a Bitmap
        val decodedByteArray = Base64.decode(base64Image, Base64.DEFAULT)
        val bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)

        // Set the retrieved bitmap as the image for the avatarImage ImageView
        avatarImage.setImageBitmap(bitmap)
    }

    fun getAchContent(): List<Achievement> {
        val list = mutableListOf<Achievement>()
        val selectQuery = "SELECT * FROM achievements"
        val db = DBConnect(requireContext()).writableDatabase
        val cursor: Cursor = db.rawQuery(selectQuery, null)

        try {
            val achName = cursor.getColumnIndex("achievement_name")
            val description = cursor.getColumnIndex("description")
            val currLevel = cursor.getColumnIndex("current_level")
            val currProg = cursor.getColumnIndex("current_progress")
            val maxValue = cursor.getColumnIndex("maximum_value")

            while (cursor.moveToNext()) {
                val name = cursor.getString(achName)
                var desc = cursor.getString(description)
                var clevel = cursor.getInt(currLevel).toString()
                val cProg = cursor.getInt(currProg).toString()
                val mxval = cursor.getInt(maxValue).toString()

                if (clevel.toInt() == 0) clevel = 1.toString()

                var parts = desc.split("=")
                var upddesc = "${parts[0]} $clevel ${parts[1]} $cProg ${parts[2]}"


                val content = Achievement(name, upddesc, clevel, mxval)
                list.add(content)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            cursor.close()
            db.close()
        }

        return list
    }



    fun currentlvl() {
        val db = DBConnect(context).readableDatabase
        val cursor = db.rawQuery("SELECT current_level FROM achievements WHERE _id = 3", null)
        if (cursor.moveToFirst()) {
            val currLevel = cursor.getInt(0)
            textview1.text = currLevel.toString()
        }
        cursor.close()
        db.close()
    }

    fun dayStreak() {
        val db = DBConnect(context).readableDatabase
        val countCursor = db.rawQuery("SELECT current_level FROM achievements WHERE _id = 1", null)
        val totalCount = if (countCursor.moveToFirst()) {
            countCursor.getInt(0)
        } else {
            0
        }
        countCursor.close()

        val sumCursor = db.rawQuery("SELECT SUM(score) FROM user_records", null)
        val sum = if (sumCursor.moveToFirst()) {
            sumCursor.getInt(0)
        } else {
            0
        }
        sumCursor.close()

        var daystreak = 0
        if (totalCount > 0) {
            daystreak = sum / totalCount
        }

        db.close()
        textview3.text = daystreak.toString()
    }

    fun accuracy() {
        val db = DBConnect(context).readableDatabase
        // Get row count
        val countQuery = "SELECT COUNT(score) FROM user_records"
        val countCursor = db.rawQuery(countQuery, null)
        val totalCount = if (countCursor.moveToFirst()) {
            countCursor.getInt(0)
        } else {
            0
        }
        countCursor.close()

        // Get sum of the score
        val sumQuery = "SELECT SUM(score) FROM user_records"
        val sumCursor = db.rawQuery(sumQuery, null)
        val sum = if (sumCursor.moveToFirst()) {
            sumCursor.getInt(0)
        } else {
            0
        }
        sumCursor.close()

        var accuracy = 0
        if (totalCount > 0) {
            accuracy = sum / totalCount
        }

        db.close()
        textview2.text = "$accuracy%"
    }


}











