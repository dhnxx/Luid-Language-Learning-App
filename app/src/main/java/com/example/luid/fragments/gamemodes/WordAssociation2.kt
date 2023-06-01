package com.example.luid.fragments.gamemodes

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import com.google.android.material.card.MaterialCardView
import com.example.luid.R
import com.example.luid.classes.SMLeitner
import com.example.luid.classes.WordAssociationClass
import com.example.luid.database.DBConnect
import android.os.SystemClock


data class Choice(val text: String, @DrawableRes val imageResId: Int)

class WordAssociation2 : AppCompatActivity() {
    private lateinit var questionList: ArrayList<WordAssociationClass>
    private lateinit var decoy: ArrayList<String>
    private lateinit var answers: ArrayList<String>
    private lateinit var progressbar: ProgressBar
    private lateinit var questionText: TextView
    private lateinit var timeText: TextView
    private lateinit var choiceOne: MaterialCardView
    private lateinit var choiceTwo: MaterialCardView
    private lateinit var choiceThree: MaterialCardView
    private lateinit var choiceFour: MaterialCardView
    private lateinit var choiceOneImage: ImageView
    private lateinit var choiceTwoImage: ImageView
    private lateinit var choiceThreeImage: ImageView
    private lateinit var choiceFourImage: ImageView
    private lateinit var choiceOneText: TextView
    private lateinit var choiceTwoText: TextView
    private lateinit var choiceThreeText: TextView
    private lateinit var choiceFourText: TextView
    private lateinit var submitButton: Button
    private lateinit var nextButton: Button
    private lateinit var sm: SMLeitner
    private var context:Context = this
    private var level: Int = 0 // intent
    private var phase = 1
    private var timeSpent = 0
    private var tempAnswer = ""
    private var choices = mutableListOf<Choice>()
    private var counter = 0
    private var score = 0.0
    private var correctAnswerCounter = 0
    private var startTime: Long = 0
    private var timeInMilliseconds: Long = 0
    private var elapsedTime: Long = 0
    private var handler: Handler? = null
    private var time = 0
    private var totalTime = 0
    private var avgTime = 0

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
        setContentView(R.layout.activity_word_association2)

        progressbar = findViewById(R.id.progressBar)
        questionText = findViewById(R.id.question)
        timeText = findViewById(R.id.timetxt)
        choiceOne = findViewById(R.id.choice1)
        choiceTwo = findViewById(R.id.choice2)
        choiceThree = findViewById(R.id.choice3)
        choiceFour = findViewById(R.id.choice4)
        choiceOneText = findViewById(R.id.choice1Text)
        choiceTwoText = findViewById(R.id.choice2Text)
        choiceThreeText = findViewById(R.id.choice3Text)
        choiceFourText = findViewById(R.id.choice4Text)
        choiceOneImage = findViewById(R.id.choice1Image)
        choiceTwoImage = findViewById(R.id.choice2Image)
        choiceThreeImage = findViewById(R.id.choice3Image)
        choiceFourImage = findViewById(R.id.choice4Image)
        submitButton = findViewById(R.id.submitButton)
        nextButton = findViewById(R.id.nextButton)

        var i = 0
        level = intent.getIntExtra("level", 0)
        val intent1 = Intent(this, ResultActivity::class.java)

        nextButton.isEnabled = false
        nextButton.visibility = View.INVISIBLE


        questionList = ArrayList()
        phaseone()
        println(questionList)
        progressbar.max = questionList.size
        progressbar.progress = i + 1
        clear()
        selectAnswer()
        refreshview(i)



        nextButton.setOnClickListener {
            if (i < questionList.size - 1) {
                i += 1
                clear()
                refreshview(i)
                progressbar.progress += 1

            } else {
                // Last element of the questionList, navigate to result screen
                // navigate using navController
                submit(i)
               avgTime = (totalTime / questionList.size)
                println("AVG TIME: $avgTime")
                intent1.putExtra("level", level)
                intent1.putExtra("phase", phase)
                intent1.putExtra("score", score)
                intent1.putExtra("totalItems", questionList.size)
                intent1.putExtra("totalTime", totalTime)
                intent1.putExtra("avgTime", avgTime)
                startActivity(intent1)
            }
        }


    }

    override fun onBackPressed() {
        showConfirmationDialog()
    }



    @SuppressLint("Range")
    private fun phaseone() {
        val sm = SMLeitner()
        sm.validateQuestionBank(context, level, phase)
        var gameSessionNumber = sm.getLatestGameSessionNumber(context, level, phase)

        var db = DBConnect(applicationContext).readableDatabase
        val selectQuery =
            "SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase"
        val cursor: Cursor
        cursor = db.rawQuery(selectQuery, null)
        // CREATE TEMP TABLE QUESTION
        db.execSQL("DROP TABLE IF EXISTS ${DBConnect.temp_qstion}")
        db.execSQL("CREATE TABLE IF NOT EXISTS ${DBConnect.temp_qstion} AS SELECT * FROM ${DBConnect.questions_tb} WHERE level = level AND phase = phase")
        var id = ArrayList<Int>()
        var kap = ArrayList<String>()
        var eng = ArrayList<String>()
        var tag = ArrayList<String>()
        var question = ArrayList<String>()
        decoy = ArrayList()
        answers = ArrayList()
        if (cursor.moveToFirst()) {
            do {
                var idlist = cursor.getInt(cursor.getColumnIndex("_id"))
                var kaplist = cursor.getString(cursor.getColumnIndex("kapampangan"))
                var taglist = cursor.getString(cursor.getColumnIndex("tagalog"))
                var englist = cursor.getString(cursor.getColumnIndex("english"))

                id.add(idlist)
                kap.add(kaplist)
                tag.add(taglist)
                eng.add(englist)

            } while (cursor.moveToNext())
        }
        for (i in 0 until 10) {
            decoy.clear()

            when ((1..4).random()) {

                1 -> {
                    decoy.clear()

                    question.add("What is " + kap[i] + " in Tagalog?")
                    answers.add(tag[i])

                    decoy.addAll(tag)
                    for (i in answers) {
                        for (j in tag) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if (!answers.contains(j)) {
                                    decoy.add(j)
                                }
                            }
                        }
                    }
                }

                2 -> {
                    decoy.clear()

                    question.add("What is " + kap[i] + " in English?")
                    answers.add(eng[i])

                    decoy.addAll(eng)
                    for (i in answers) {
                        for (j in eng) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if (!answers.contains(j)) {
                                    decoy.add(j)
                                }
                            }
                        }
                    }

                }

                3 -> {
                    decoy.clear()

                    question.add("What is " + tag[i] + " in Kapampangan?")
                    answers.add(kap[i])
                    decoy.addAll(kap)
                    for (i in answers) {
                        for (j in kap) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if (!answers.contains(j)) {
                                    decoy.add(j)
                                }
                            }
                        }
                    }

                }

                4 -> {
                    decoy.clear()
                    question.add("What is " + eng[i] + " in Kapampangan?")
                    answers.add(kap[i])

                    decoy.addAll(kap)
                    for (i in answers) {
                        for (j in kap) {
                            if (decoy.contains(i)) {
                                decoy.remove(i)
                                if (!answers.contains(j)) {
                                    decoy.add(j)
                                }
                            }
                        }
                    }

                }


            }


            decoy.shuffle()
            var randInd = ArrayList<Int>()
            for (k in 1..decoy.size) {
                randInd.add(k)
            }
            questionList.add(
                WordAssociationClass(
                    id[i],
                    question[i],
                    answers[i],
                    R.drawable.home,
                    decoy[randInd[0]],
                    R.drawable.home,
                    decoy[randInd[1]],
                    R.drawable.home,
                    decoy[randInd[2]],
                    R.drawable.home
                )
            )
        }
        // clear question temp table after
        cursor.close()
    }

    private fun refreshview(i: Int) {

        // start a stopwatch for each question
        startTime = SystemClock.uptimeMillis()
        handler = Handler()
        handler?.postDelayed(timerRunnable, 0)


        nextButton.isEnabled = false
        nextButton.visibility = View.INVISIBLE
        submitButton.isEnabled = true
        submitButton.visibility = View.VISIBLE

        questionText.text = questionList[i].questions

        choices = mutableListOf(
            Choice(questionList[i].correct, questionList[i].correctImg),
            Choice(questionList[i].decoy1, questionList[i].decoy1Img),
            Choice(questionList[i].decoy2, questionList[i].decoy2Img),
            Choice(questionList[i].decoy3, questionList[i].decoy3Img)
        )
        choices.shuffle()

        choiceOneText.text = choices[0].text
        choiceOneImage.setImageResource(choices[0].imageResId)
        choiceTwoText.text = choices[1].text
        choiceTwoImage.setImageResource(choices[1].imageResId)
        choiceThreeText.text = choices[2].text
        choiceThreeImage.setImageResource(choices[2].imageResId)
        choiceFourText.text = choices[3].text
        choiceFourImage.setImageResource(choices[3].imageResId)

        counter = 0

        println("------------------------")
        println(questionList[i].correct)
        println(questionList[i].decoy1)
        println(questionList[i].decoy2)
        println(questionList[i].decoy3)

        submit(i)

    }
    private fun submit(i: Int){

        submitButton.setOnClickListener {
            handler?.removeCallbacks(timerRunnable)
            submitButton.isEnabled = false
            submitButton.visibility = View.INVISIBLE
            nextButton.isEnabled = true
            nextButton.visibility = View.VISIBLE
            selectAnswer()
            time = (elapsedTime/ 1000).toInt()
            checkAnswer(i)
            val sm = SMLeitner()
            score = sm.scoreCalc(correctAnswerCounter, questionList.size)
            totalTime += time
            println("Score = $score")
            println("Time = $time")
            println("Total Time = $totalTime")
            // smLeitner algo and temptable here



        }
    }
// hello
    private fun clear() {
        choiceOne.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
        choiceTwo.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
        choiceThree.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
        choiceFour.setCardBackgroundColor(Color.parseColor("#FFFBFF"))

    }

    private fun selectAnswer() {

        //create a listener for all of the cards and change the color of the card to purple
        choiceOne.setOnClickListener {
            clear()
            choiceOne.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAnswer = choices[0].text

        }

        choiceTwo.setOnClickListener {
            clear()
            choiceTwo.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAnswer = choices[1].text

        }

        choiceThree.setOnClickListener {
            clear()
            choiceThree.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAnswer = choices[2].text

        }

        choiceFour.setOnClickListener {
            clear()
            choiceFour.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAnswer = choices[3].text

        }


    }

    private fun checkAnswer(i: Int) {
        val sm = SMLeitner()

        if (tempAnswer == questionList[i].correct) {
            println("Correct")
            correctAnswerCounter++
            // make the selected card green
            when (tempAnswer) {
                choices[0].text -> {
                    choiceOne.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
                choices[1].text -> {
                    choiceTwo.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
                choices[2].text -> {
                    choiceThree.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
                choices[3].text -> {
                    choiceFour.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
            }
            sm.smLeitnerCalc(context, questionList[i].id, level, phase, true, time)



        } else {
            println("Wrong")
            // make the selected card red and show the correct answer
            when (tempAnswer) {
                choices[0].text -> {
                    choiceOne.setCardBackgroundColor(Color.parseColor("#FFB6C1"))
                }
                choices[1].text -> {
                    choiceTwo.setCardBackgroundColor(Color.parseColor("#FFB6C1"))
                }
                choices[2].text -> {
                    choiceThree.setCardBackgroundColor(Color.parseColor("#FFB6C1"))
                }
                choices[3].text -> {
                    choiceFour.setCardBackgroundColor(Color.parseColor("#FFB6C1"))
                }
            }

            sm.smLeitnerCalc(context, questionList[i].id, level, phase, false, time)

            when (questionList[i].correct) {
                choices[0].text -> {
                    choiceOne.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
                choices[1].text -> {
                    choiceTwo.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
                choices[2].text -> {
                    choiceThree.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
                choices[3].text -> {
                    choiceFour.setCardBackgroundColor(Color.parseColor("#CFFFD5"))
                }
            }

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
