package com.example.luid.fragments.mainmenu.gamemodes

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adapters.PhaseOneAdapter
import com.example.luid.classes.WordAssociationClass
import com.example.luid.database.DBConnect
import com.example.luid.classes.PhaseOneClass
import com.example.luid.database.DBConnect.Companion.questions_tb
import com.example.luid.database.DBConnect.Companion.temp_qstion


class WordAssociation : AppCompatActivity() {
    private lateinit var questionList: ArrayList<WordAssociationClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseOneAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_association)

        // intent is used for communicating between fragments to separate activities
        val selectPhase = intent.getStringExtra("Phase")

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        // getQuestionList(selectPhase)
        phaseone()
        adapter = PhaseOneAdapter(recyclerView, questionList)
        recyclerView.adapter = adapter

    }

    @SuppressLint("Range")
    private fun phaseone() {

        var level = 1
        var phase = 1
        var db = DBConnect(applicationContext).readableDatabase
        val selectQuery = "SELECT * FROM $questions_tb WHERE level = $level AND phase = $phase"
        val cursorte: Cursor
        cursorte = db.rawQuery(selectQuery, null)
        val cv = ContentValues()


        try {
            if (cursorte.moveToFirst()) {
                do {
                    var idDB = cursorte.getString(0)
                    var levelDB = cursorte.getString(1)
                    var phaseDB = cursorte.getString(2)
                    var questions = cursorte.getString(3)
                    var kapampanganDB = cursorte.getString(4)
                    var englishDB = cursorte.getString(5)
                    var tagalogDB = cursorte.getString(6)
                    var translationDB = cursorte.getString(7)
                    var game_sessionDB = cursorte.getString(8)
                    var easiness_factorDB = cursorte.getString(9)
                    var intervalDB = cursorte.getString(10)
                    var diff_lvlDB = cursorte.getString(11)
                    var times_reviewedDB = cursorte.getString(12)
                    var visibility = cursorte.getString(13)


                    cv.put("_id", idDB)
                    cv.put("level", levelDB)
                    cv.put("phase", phaseDB)
                    cv.put("question", questions)
                    cv.put("kapampangan", kapampanganDB)
                    cv.put("english", englishDB)
                    cv.put("tagalog", tagalogDB)
                    cv.put("translation", translationDB)
                    cv.put("game_session", game_sessionDB)
                    cv.put("easiness_factor", easiness_factorDB)
                    cv.put("interval", intervalDB)
                    cv.put("difficulty_level", diff_lvlDB)
                    cv.put("times_viewed", times_reviewedDB)
                    cv.put("visibility", visibility)

                    db.insert(DBConnect.temp_qstion, null, cv)

                } while (cursorte.moveToNext())
            }
            cursorte?.close()

        } catch (e: Exception) {
            e.printStackTrace()
        }
        /*


        val answers = ArrayList<String>()
        val decoy = ArrayList<String>()


        val tagdecoy = PhaseOneClass().getTagalog(cursorans)
        val correctImage= PhaseOneClass().getImg(cursorans)

        val decoyImage = PhaseOneClass().getImg(cursorans)
*/

        val queryans = "SELECT * FROM $temp_qstion "
        val cursorans: Cursor
        cursorans = db.rawQuery(queryans, null)
        var kap = PhaseOneClass().getKapampangan(cursorans)
        var prompt = PhaseOneClass().getPrompt(cursorans)
        var eng = PhaseOneClass().getEnglish(cursorans)
        var tag = PhaseOneClass().getTagalog(cursorans)
        var question = ArrayList<String>()
        var img = PhaseOneClass().getImg(cursorans)
        val decoy = ArrayList<String>()
        val decoyImage = ArrayList<String>()
        val answer = ArrayList <String>()

        val correctImage = ArrayList<Int>()

                for (i in 0 until prompt.size) {
                    when ((1..4).random()) {
                        1 -> {
                            questionList.add(questionListAdd(kap[i], prompt[i], tag, img[i], img))





                        }

                        2 -> {
                            question.add("What is " + kap[i] + " in English?")
                            answer.add(eng[i])
                            decoy.add(eng[i])


                        }

                        3 -> {
                            question.add("What is " + eng[i] + " in Kapampangan?")
                            answer.add(kap[i])
                            decoy.add(kap[i])


                        }

                        4 -> {
                            question.add("What is " + tag[i] + " in Kapampangan?")
                            answer.add(kap[i])
                            decoy.add(kap[i])




                        }

                    }
                }



    }

    } // phase1 end parenthesis


fun questionListAdd(answer : String, question : String, decoy : ArrayList<String>,
                    correctImage : Int, decoyImage : ArrayList<Int>) : WordAssociationClass {


    decoy.removeAll(listOf(answer).toSet())
    decoyImage.removeAll(listOf(correctImage).toSet())

    var randInd = ArrayList<Int>()
    var temp : WordAssociationClass
    for (i in 1 .. decoy.size){
        randInd.add(i)
    }
    randInd.shuffle()

    temp = WordAssociationClass(
            question,
            answer,
            correctImage,
            decoy[randInd[0]],
            decoyImage[randInd[0]],
            decoy[randInd[1]],
            decoyImage[randInd[1]],
            decoy[randInd[2]],
            decoyImage[randInd[2]]
        )

    return temp

}





//////////////

/*
        // Use the exclusive range operator until to avoid index out of bounds
        for (i in 0 until questions.size) {
            questionList.add(
                WordAssociationClass(
                    questions[i],
                    answers[i],
                    R.drawable.home,
                    decoy[0],
                    R.drawable.home,
                    decoy[1],
                    R.drawable.home,
                    decoy[2],
                    R.drawable.home
                )
            )
        }
    }
}


// Add questions to the list
// mag loloop yung query dito na mag aadd ng question sa list
// question.add("What the dog doing")
// Add answers to the list
// connected ang query dito sa questions
// answers.add("Bork")
// Add decoys to the list
// ang query dito is lahat ng words (ang problema dito is yung different choices based sa question
// ex. what is tagalog of ebun? dapat ang choices is tagalog din
// baka magkaroon tayo ng when case dito depende sa questions
//   decoy.add("Bork")
//addtolist.add(WordAssociationClass(questions, correct, correctImg, decoy1, decoy1Img, decoy2, decoy2Img, decoy3, decoy3Img))
// Remove any decoy that matches an answer,
// eto matic na matatanggal yung mga sagot sa choices, para walang duplicate sa choices
   if (cursor.moveToFirst()) {
               do {
                   val tagword = cursor.getString(cursor.getColumnIndex("$tagWord"))
                   val kapword = cursor.getString(cursor.getColumnIndex("$kapWord"))
                   val engword = cursor.getString(cursor.getColumnIndex("$engWord"))
                   val randquestiontype = (1..2).random()
                   when(randquestiontype) {
                       1 -> {
                           when(randquestiontype) {
                               1 -> {questions.add("What is " + kapword + " in " + tagword)


                               }
                               2 -> {questions.add("What is " + kapword + " in " + engword)}
                           }
                       }
                       2 -> {
                           when(randquestiontype){
                               1 -> {questions.add("What is " + kapword + " in " + tagword)}
                               2 -> {questions.add("What is "+ tagword +" in "+ kapword)}
                           }
                       }
                   }



                   answers.add(engword)
                   decoy.add(kapword)
               } while (cursor.moveToNext())

          }




    private fun getQuestionList(switchState: String?) {
        when (switchState) {
            "Phase 1.1" -> {
                questionlist.add(
                    WordAssociationClass(
                        "What is the color of the sky?",
                        "Blue",
                        R.drawable.home,
                        "Red",
                        R.drawable.settings,
                        "Green",
                        R.drawable.about,
                        "Yellow",
                        R.drawable.profile
                    )
                )
            }
            "Phase 1.2" -> {
                questionlist.add(
                    WordAssociationClass(
                        "What is the capital of France?",
                        "Paris",
                        R.drawable.home,
                        "Madrid",
                        R.drawable.settings,
                        "Berlin",
                        R.drawable.about,
                        "London",
                        R.drawable.profile
                    )
                )
            }
        }
    }
*/