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
import androidx.navigation.findNavController
import com.example.luid.fragments.mainmenu.MainActivity


data class Choice(val text: String, @DrawableRes val imageResId: Int)

class WordAssociation : AppCompatActivity() {
    private lateinit var questionList: ArrayList<WordAssociationClass>
    private lateinit var decoy: ArrayList<String>
    private lateinit var decoyimg: ArrayList<String>
    private lateinit var decoyimgtemp: ArrayList<String>
    private lateinit var answers: ArrayList<String>
    private lateinit var answerstemp: ArrayList<String>
    private lateinit var decoytemp: ArrayList<String>
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
    private var context: Context = this
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
    private var langch = ""
    private var answerimg = ""
    private var decimg0 = ""
    private var decimg1 = ""
    private var decimg2 = ""

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
        val sm = SMLeitner()
        sm.lifeSpent(context)

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
                sm.addSession(this, level, phase)
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
        println("DB  TEMP NOT CREATED")

        val selectQuery =
            "SELECT * FROM ${DBConnect.temp_qstion} WHERE level = $level AND phase = $phase AND game_session = $gameSessionNumber"
        val cursor: Cursor
        // CREATE TEMP TABLE QUESTION

        db.execSQL("DROP TABLE IF EXISTS ${DBConnect.temp_qstion}")
        db.execSQL("CREATE TABLE IF NOT EXISTS ${DBConnect.temp_qstion} AS SELECT * FROM ${DBConnect.questions_tb} WHERE level = $level AND phase = $phase")

        println("$gameSessionNumber" + "HELLO")
        var id = ArrayList<Int>()
        var kap = ArrayList<String>()
        var eng = ArrayList<String>()
        var tag = ArrayList<String>()
        var question = ArrayList<String>()
        decoy = ArrayList()
        decoyimg = ArrayList()
        answers = ArrayList()
        decoytemp = ArrayList()
        decoyimgtemp = ArrayList()
        answerstemp = ArrayList()
        cursor = db.rawQuery(selectQuery, null)
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
        println("BEFORE for loop")

        for (i in 0 until tag.size) {
            println(tag[i])

        }
        for (i in 0 until tag.size) {
            decoy.clear()
            answers.clear()
            when ((1..4).random()) {

                1 -> {
                    decoytemp.clear()
                    answerstemp.clear()
                    question.add("What is " + kap[i] + " in Tagalog?")
                    answerstemp.add(tag[i])
                    decoytemp.addAll(tag)

                    for (i in answerstemp) {
                        if (decoytemp.contains(i)) {
                            decoytemp.remove(i)
                        }
                    }
                    decoy = decoytemp
                    answers = answerstemp
                    langch = "tagalog"

                }

                2 -> {
                    decoytemp.clear()
                    answerstemp.clear()
                    question.add("What is " + kap[i] + " in English?")
                    answerstemp.add(eng[i])
                    decoytemp.addAll(eng)
                    for (i in answerstemp) {
                        if (decoytemp.contains(i)) {
                            decoytemp.remove(i)
                        }
                    }
                    decoy = decoytemp
                    answers = answerstemp
                    langch = "english"

                }

                3 -> {
                    decoytemp.clear()
                    answerstemp.clear()
                    question.add("What is " + tag[i] + " in Kapampangan?")
                    answerstemp.add(kap[i])
                    decoytemp.addAll(kap)
                    for (i in answerstemp) {
                        if (decoytemp.contains(i)) {
                            decoytemp.remove(i)
                        }
                    }
                    decoy = decoytemp
                    answers = answerstemp
                    langch = "kapampangan"

                }

                4 -> {
                    decoytemp.clear()
                    answerstemp.clear()
                    question.add("What is " + eng[i] + " in Kapampangan?")
                    answerstemp.add(kap[i])
                    decoytemp.addAll(kap)
                    for (i in answerstemp) {
                        if (decoytemp.contains(i)) {
                            decoytemp.remove(i)
                        }
                    }
                    decoy = decoytemp
                    answers = answerstemp
                    langch = "kapampangan"

                }
// hello

            }

            for (i in 0 until answers.size) {
                println("ANSWERS = ${answerstemp[i]} " + i)
            }
            for (i in 0 until decoy.size) {
                println("DECOY = ${decoytemp[i]}" + " " + i)
            }
            decoy.shuffle()

        //  var langch = choice sample napili kapampangan

            val ansimg = answers[0]
            val searchimg0 = decoy[0]
            val searchimg1 = decoy[1]
            val searchimg2 = decoy[2]
            val cursor1: Cursor
            cursor1 = db.rawQuery("SELECT * FROM ${DBConnect.questions_tb}", null)

            if(cursor1.moveToFirst()){
                do{
                    val ans = cursor.getString(cursor.getColumnIndex("$langch"))
                    if(ans.equals(ansimg, ignoreCase = true)) {
                        val id = cursor1.getInt(cursor1.getColumnIndex("_id"))
                        answerimg = "img$id"
                    }
                    val dec0 = cursor.getString(cursor.getColumnIndex("$langch"))
                    if(dec0.equals(searchimg0, ignoreCase = true)) {
                        val id = cursor1.getInt(cursor1.getColumnIndex("_id"))
                        decimg0 = "img$id"
                    }
                    val dec1 = cursor.getString(cursor.getColumnIndex("$langch"))
                    if(dec1.equals(searchimg1, ignoreCase = true)) {
                        val id = cursor1.getInt(cursor1.getColumnIndex("_id"))
                        decimg1 = "img$id"
                    }
                    val dec2 = cursor.getString(cursor.getColumnIndex("$langch"))
                    if(dec2.equals(searchimg2, ignoreCase = true)) {
                        val id = cursor1.getInt(cursor1.getColumnIndex("_id"))
                        decimg2 = "img$id"
                    }
                } while(cursor1.moveToNext())
            }

            val anszimg = resources.getIdentifier(
                answerimg,
                "drawable",
                packageName
            )

            val image0 = resources.getIdentifier(
                decimg0,
                "drawable",
                packageName
            )

            val image1 = resources.getIdentifier(
                decimg1,
                "drawable",
                packageName
            )

            val image2 = resources.getIdentifier(
                decimg2,
                "drawable",
                packageName
            )

            questionList.add(
                WordAssociationClass(
                    id[i],
                    question[i],
                    answers[0],
                    anszimg,
                    decoy[0],
                    image0,
                    decoy[1],
                    image1,
                    decoy[2],
                    image2
                )
            )
            for (i in 0 until 10) {
                println("MAIN DEC = ${decoy}")
            }
        }
        // clear question temp table after
        cursor.close()
    }

    private fun refreshview(i: Int) {

        // start a stopwatch for each question

        startTime = SystemClock.uptimeMillis()
        handler = Handler()
        handler?.postDelayed(timerRunnable, 0)

        selectAnswer()


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

    private fun submit(i: Int) {

        submitButton.setOnClickListener {
            handler?.removeCallbacks(timerRunnable)
            submitButton.isEnabled = false
            submitButton.visibility = View.INVISIBLE
            nextButton.isEnabled = true
            nextButton.visibility = View.VISIBLE
            time = (elapsedTime / 1000).toInt()
            cardDisable()
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
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()


        }
        builder.setNegativeButton("No") { dialog: DialogInterface, _: Int ->
            // Continue the current operation, such as staying on the current screen
            dialog.dismiss()
        }
        builder.show()
    }

    private fun cardDisable() {

        // disable onclick listener for all cards
        choiceOne.setOnClickListener(null)
        choiceTwo.setOnClickListener(null)
        choiceThree.setOnClickListener(null)
        choiceFour.setOnClickListener(null)
    }
}
