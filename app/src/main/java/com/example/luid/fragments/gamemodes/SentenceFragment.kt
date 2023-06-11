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
import com.example.luid.database.DBConnect.Companion.user_records_tb
import com.example.luid.fragments.mainmenu.MainActivity

class SentenceFragment : AppCompatActivity() {
    private lateinit var questionList: ArrayList<SentenceFragment>
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
    private var level: Int = 0 // intent
    private var phase = 2
    private var startTime: Long = 0
    private var timeInMilliseconds: Long = 0
    private var elapsedTime: Long = 0
    private var handler: Handler? = null
    private var time = 0
    private var totalTime = 0
    private var avgTime = 0
    private var score = 0.0
    private var correctAnswerCounter = 0
    private var id = ArrayList<Int>()
    private var lowestGameSession = 0


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

        level = intent.getIntExtra("level", 0)
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
        val sm = SMLeitner()

        lowestGameSession = sm.getLowestGameSessionNumber(context, level, phase)

        var db = DBConnect(context).writableDatabase
        var cursor = db.rawQuery(
            "SELECT * FROM $user_records_tb WHERE level = $level AND phase = $phase",
            null
        )

        val colScore = cursor.getColumnIndex("score")
        val colTimeSpent = cursor.getColumnIndex("time_spent")



        if (cursor.count < 1) {
            sm.addSession(context, level, phase)
        } else {
            cursor.moveToLast()

            val currScore = cursor.getInt(colScore)
            val currTimeSpent = cursor.getInt(colTimeSpent)

            if (!(currScore == 0 && currTimeSpent == 0)) {
                sm.addSession(context, level, phase)
            }
        }


        cursor.close()

        sm.lifeSpent(context)
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
                intent2.putExtra("level", level)
                intent2.putExtra("phase", phase)
                intent2.putExtra("score", score)
                intent2.putExtra("totalItems", questionList.size + 1)
                intent2.putExtra("totalTime", totalTime)
                intent2.putExtra("avgTime", avgTime)
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

    override fun onPause() {
        super.onPause()

        lastRowReset()
        val intent2 = Intent(this, MainActivity::class.java)
        intent2.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(intent2)

    }


    private fun phasetwo() {
        val sm = SMLeitner()
        val ph2 = PhaseTwoClass()
        sm.validateQuestionBank(context, level, phase)
        var gameSessionNumber = sm.getLatestGameSessionNumber(context, level, phase)

        var db = DBConnect(context).writableDatabase
        // CREATE TEMP TABLE QUESTION
        db.execSQL("DROP TABLE IF EXISTS ${DBConnect.temp_qstion}")
        db.execSQL("CREATE TABLE IF NOT EXISTS ${DBConnect.temp_qstion} AS SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase")

        var cursor = db.rawQuery(
            "SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase  AND game_session = $gameSessionNumber",
            null
        )


        if (cursor.moveToFirst()) {
            var idCol = cursor.getColumnIndex("_id")
            do {
                var idlist = cursor.getInt(idCol)
                id.add(idlist)
            } while (cursor.moveToNext())
        }

        println("COUNT : ${cursor.count}")
        println("LEVEL : $level")
        println("PHASE : $phase")

        cursor.close()
        db.close()


        var kap = ph2.getKapampangan(context, level, phase)
        var eng = ph2.getEnglish(context, level, phase)
        var tag = ph2.getTagalog(context, level, phase)
        var img = ph2.getImg(context, level, phase)
        var imgList = ArrayList<Int>()
        var answers = ArrayList<String>()
        var questions = ArrayList<String>()


        for (i in kap) {
            println(i)
        }

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

        for (i in 0 until kap.size) {
            questionList.add(
                SentenceFragment(
                    id[i],
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


        val id2 = questionList[i].id
        val answerimg = "zimg$id2"
        val answerimgid = resources.getIdentifier(answerimg, "drawable", packageName)


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
            questionImage.setImageResource(answerimgid)
        }


    }

    private fun submit(i: Int) {
        val sm = SMLeitner()
        val question = questionList[i]


        submitButton.setOnClickListener {
            handler?.removeCallbacks(timerRunnable)

            time = (elapsedTime / 1000).toInt()

            if (answerLabel.text.replace(
                    "\\s+".toRegex(),
                    ""
                ) == question.sentence.replace("\\s+".toRegex(), "")
            ) {
                answerLabel.setTextColor(Color.parseColor("#037d50"))
                println("CORRECT")
                correctAnswerCounter++

                sm.smLeitnerCalc(context, questionList[i].id, level, phase, true, time)
            } else {
                answerLabel.setTextColor(Color.parseColor("#FF0000"))
                println("WRONG")

                sm.smLeitnerCalc(context, questionList[i].id, level, phase, false, time)
            }
            println(answerLabel.text.replace("\\s+".toRegex(), ""))
            println(question.sentence.replace("\\s+".toRegex(), ""))


            totalTime += time
            score = sm.scoreCalc(correctAnswerCounter, questionList.size)
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

            lastRowReset()

            dialog.dismiss()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }
        builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            // Continue the current operation, such as staying on the current screen
            dialog.dismiss()
        }
        builder.show()
    }

    private fun lastRowReset() {

        // Resetting of last row in user_records when user closes the game prematurely
        var ldb = DBConnect(context).writableDatabase
        var cursor = ldb.rawQuery(
            "SELECT * FROM $user_records_tb WHERE level = $level AND phase = $phase",
            null
        )
        var cv = ContentValues()

        var colId = cursor.getColumnIndex("_id")

        println("COUNT IN DIALOG : ${cursor.count}")

        if (cursor.count > 1) {
            cursor.moveToLast()
            var id = cursor.getInt(colId)
            cursor.moveToPrevious()
            println("PREV ID IN DIALOG : ${id}")
            cv.put("game_session_number", lowestGameSession)
            cv.put("score", 0)
            cv.put("time_spent", 0)

            ldb.update("$user_records_tb", cv, "_id = $id", null)

        } else {
            cursor.moveToFirst()
            var id = cursor.getInt(colId)
            cv.put("game_session_number", lowestGameSession)
            cv.put("score", 0)
            cv.put("time_spent", 0)

            ldb.update("$user_records_tb", cv, "_id = $id", null)
        }

        cv.clear()
        cursor.close()
        ldb.close()


    }
}