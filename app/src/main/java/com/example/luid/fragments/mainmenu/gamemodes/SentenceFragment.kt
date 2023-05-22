package com.example.luid.fragments.mainmenu.gamemodes

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.adaptersQ.PhaseTwoAdapter
import com.example.luid.classes.SentenceFragment


class SentenceActivity : AppCompatActivity() {

    private lateinit var questionList: ArrayList<SentenceFragment>
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PhaseTwoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sentencefragment)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        questionList = ArrayList()
        getQuestionList()
        adapter = PhaseTwoAdapter(recyclerView, questionList)
        recyclerView.adapter = adapter

    }


    private fun getQuestionList() {
    questionList.add(
        SentenceFragment(
            "What is Good Morning Neighbours in Kapampangan?", "Magandang Umaga Kapitbahay", R.drawable.home,

        )

    )

        questionList.add(
            SentenceFragment(
                "What is Good Morning Neighbours in Kapampangan?", "Magandang Umaga Kapitbahay", R.drawable.home,

                )

        )


    }
}



/*
        val deconstructedWords =
            arrayListOf("Hello", "World", "This", "Is", "A", "Test", "lorem", "monster", "ipsum")
        deconstructedWords.shuffle()

        val clearbutton = findViewById<TextView>(R.id.clearButton)

        val flexboxLayout = findViewById<FlexboxLayout>(R.id.flexboxLayout)
        val textlabel = findViewById<EditText>(R.id.textLabel1)
        textlabel.keyListener = null


        for (word in deconstructedWords) {
            val wordTextView = TextView(this)
            wordTextView.text = word
            wordTextView.textSize = 20f
            wordTextView.background = ResourcesCompat.getDrawable(
                resources,
                R.drawable.rounded_corner,
                null
            )
            wordTextView.backgroundTintList = ResourcesCompat.getColorStateList(
                resources,
                R.color.md_theme_light_primaryContainer,
                null
            )
            wordTextView.setPadding(20, 10, 20, 10)
            wordTextView.setOnClickListener {
                textlabel.append(wordTextView.text as String + " ")
                wordTextView.isEnabled = false

            }

            val layoutParams = FlexboxLayout.LayoutParams(
                FlexboxLayout.LayoutParams.WRAP_CONTENT,
                FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.setMargins(10, 15, 10, 15)


            wordTextView.layoutParams = layoutParams
            flexboxLayout.addView(wordTextView)
        }

        clearbutton.setOnClickListener {
            textlabel.text.clear()
            for (i in 0 until flexboxLayout.childCount) {
                val child = flexboxLayout.getChildAt(i)
                child.isEnabled = true
            }


        }
    }
    */

