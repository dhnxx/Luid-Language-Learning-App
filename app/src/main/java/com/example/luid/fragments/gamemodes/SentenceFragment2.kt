package com.example.luid.fragments.gamemodes

import android.app.AlertDialog
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.example.luid.R
import com.example.luid.classes.PhaseTwoClass
import com.example.luid.classes.SMLeitner
import com.example.luid.classes.SentenceFragment
import com.example.luid.database.DBConnect

class SentenceFragment2 : AppCompatActivity() {
    private lateinit var questionList: ArrayList<com.example.luid.classes.SentenceFragment>
    private lateinit var questionText: TextView
    private lateinit var questionImage: ImageView
    private lateinit var answerLabel: TextView
    private lateinit var flexboxLayout: com.google.android.flexbox.FlexboxLayout
    private lateinit var clearButton: Button
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button
    private lateinit var timeText: TextView
    private lateinit var progressbar: ProgressBar
    private var context: Context = this
    private var startTime: Long = 0
    private var timeInMilliseconds: Long = 0
    private var elapsedTime: Long = 0
    private var handler: Handler? = null
    private var time = 0
    private var totalTime = 0
    private var avgTime = 0
    private var score = 0.0
    private var correctAnswerCounter = 0


    private val timerRunnable = object : Runnable {
        override fun run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime
            elapsedTime = timeInMilliseconds

            val seconds = (elapsedTime / 1000).toInt()
            val minutes = seconds / 60

            val formattedTime = String.format("%02d:%02d", minutes % 60, seconds % 60)
            timeText.text = formattedTime

            handler?.postDelayed(this, 0)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentence_fragment2)

        val intent2 = Intent(this, ResultActivity::class.java)

        progressbar = findViewById(R.id.progressBar)
        timeText = findViewById(R.id.timetxt)
        questionText = findViewById(R.id.question)
        questionImage = findViewById(R.id.questionImage)
        answerLabel = findViewById(R.id.answerLabel)
        flexboxLayout = findViewById(R.id.flexboxLayout)
        clearButton = findViewById(R.id.clearButton)
        submitButton = findViewById(R.id.submitButton)
        nextButton = findViewById(R.id.nextButton)

        var i = 0


        questionList = ArrayList()

        phasetwo()
        refresh(i)
        submit(i)



        nextButton.setOnClickListener {
            if (i < questionList.size - 1) {
                i++
                refresh(i)
                submit(i)
                progressbar.progress = i + 1
            } else {

                submit(i)
                avgTime = (totalTime / questionList.size)
                println("AVG TIME: $avgTime")
                intent2.putExtra("score", score)
                intent2.putExtra("totalTime", totalTime)
                startActivity(intent2)
            }


        }

        clearButton.setOnClickListener {
            answerLabel.text = ""
            for (i in 0 until flexboxLayout.childCount) {
                val child = flexboxLayout.getChildAt(i)
                child.isEnabled = true
            }


        }
    }

    override fun onBackPressed() {
        showConfirmationDialog()
    }

    private fun phasetwo() {

        var level = 1
        var phase = 2
        var db = DBConnect(context).readableDatabase
        var cursor = db.rawQuery(
            "SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase",
            null
        )
        val cv = ContentValues()

        try {
            if (cursor.moveToFirst()) {
                do {

                    cv.put("_id", cursor.getInt(0))
                    cv.put("level", cursor.getString(1))
                    cv.put("phase", cursor.getString(2))
                    cv.put("question", cursor.getString(3))
                    cv.put("kapampangan", cursor.getString(4))
                    cv.put("english", cursor.getString(5))
                    cv.put("tagalog", cursor.getString(6))
                    cv.put("translation", cursor.getString(7))
                    cv.put("game_session", cursor.getString(8))
                    cv.put("easiness_factor", cursor.getString(9))
                    cv.put("interval", cursor.getString(10))
                    cv.put("difficulty_level", cursor.getString(11))
                    cv.put("times_viewed", cursor.getString(12))
                    cv.put("visibility", cursor.getString(13))
                    cv.put("drawable", cursor.getString(14))
                    db.insert("${DBConnect.temp_qstion}", null, cv)
                } while (cursor.moveToNext())
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }

        cv.clear()
        cursor.close()
        db.close()

        var kap = PhaseTwoClass().getKapampangan(context, level, phase)
        var eng = PhaseTwoClass().getEnglish(context, level, phase)
        var tag = PhaseTwoClass().getTagalog(context, level, phase)
        var img = PhaseTwoClass().getImg(context, level, phase)
        var imgList = ArrayList<Int>()
        var answers = ArrayList<String>()
        var questions = ArrayList<String>()

        print("KAP SIZE: ${kap.size}\n")
        print("ENG SIZE: ${eng.size}\n")
        print("TAG SIZE: ${tag.size}\n")
        print("IMG SIZE: ${img.size}\n")

        for (i in 0 until kap.size) {
            when ((1..4).random()) {
                1 -> {
                    // Kapampangan -> Tagalog
                    questions.add("What is this phrase in Tagalog?\n${kap[i]}")
                    answers.add(tag[i])
                    imgList.add(img[i])
                }

                2 -> {
                    // Kapampangan -> English
                    questions.add("What is this phrase in English?\n${kap[i]}")
                    answers.add(eng[i])
                    imgList.add(img[i])
                }

                3 -> {
                    // Tagalog -> Kapampanngan
                    questions.add("What is this phrase in Kapampangan?\n${tag[i]}")
                    answers.add(kap[i])
                    imgList.add(img[i])
                }

                4 -> {
                    // English -> Kapampanngan
                    questions.add("What is this phrase in Kapampangan?\n${eng[i]}")
                    answers.add(kap[i])
                    imgList.add(img[i])
                }
            }

        }

        var randInd = ArrayList<Int>()
        for (k in 1 until kap.size) {
            randInd.add(k)
        }

        for (i in 1 until kap.size) {
            questionList.add(
                SentenceFragment(
                    questions[i],
                    answers[i],
//                    imgList[i]
                    R.drawable.home
                )
            )
        }


    }

    private fun refresh(i: Int) {

        nextButton.isEnabled = false
        nextButton.visibility = View.INVISIBLE
        submitButton.isEnabled = true
        submitButton.visibility = View.VISIBLE


        flexboxLayout.removeAllViews()
        answerLabel.text = ""
        answerLabel.setTextColor(Color.parseColor("#1C1B1F"))

        val question = questionList[i]
        val deconstructedWords = question.getDeconstructedSentence()

        progressbar.max = questionList.size
        progressbar.progress = i + 1
        // start a stopwatch for each question

        startTime = SystemClock.uptimeMillis()
        handler = Handler()
        handler?.postDelayed(timerRunnable, 0)



        for (word in deconstructedWords) {
            val wordView = TextView(context)
            wordView.text = word
            wordView.textSize = 20f
            wordView.setPadding(10, 10, 10, 10)
            wordView.setBackgroundResource(R.drawable.rounded_corner)

            wordView.setOnClickListener {
                answerLabel.text =
                    answerLabel.text.toString() + wordView.text.toString() + " "
                wordView.isEnabled = false

            }


            val params = com.google.android.flexbox.FlexboxLayout.LayoutParams(
                com.google.android.flexbox.FlexboxLayout.LayoutParams.WRAP_CONTENT,
                com.google.android.flexbox.FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 10, 10, 10)
            wordView.layoutParams = params
            flexboxLayout.addView(wordView)



            questionText.text = questionList[i].question

        }


    }

    private fun submit(i: Int) {

        val question = questionList[i]


        submitButton.setOnClickListener {
            handler?.removeCallbacks(timerRunnable)
            if (answerLabel.text.replace(
                    "\\s+".toRegex(),
                    ""
                ) == question.sentence.replace("\\s+".toRegex(), "")
            ) {
                answerLabel.setTextColor(Color.parseColor("#037d50"))
                println("CORRECT")
                correctAnswerCounter++
            } else {
                answerLabel.setTextColor(Color.parseColor("#FF0000"))
                println("WRONG")
            }
            println(answerLabel.text.replace("\\s+".toRegex(), ""))
            println(question.sentence.replace("\\s+".toRegex(), ""))

            time = (elapsedTime / 1000).toInt()
            totalTime += time
            val sm = SMLeitner(context)
            score = sm.score(correctAnswerCounter, questionList.size)
            println("Score = $score")
            println("Time = $time")
            println("Total Time = $totalTime")

            nextButton.visibility = View.VISIBLE
            nextButton.isEnabled = true
            submitButton.visibility = View.INVISIBLE
            submitButton.isEnabled = false

        }


        }
    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm")
        builder.setMessage("Are you sure you want to go back? Any unsaved progress will be lost.")
        builder.setPositiveButton("Yes") { dialog: DialogInterface, _: Int ->
            // Handle the back action here
            super.onBackPressed()
            dialog.dismiss()
        }
        builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            // Continue the current operation, such as staying on the current screen
            dialog.dismiss()
        }
        builder.show()
    }
}