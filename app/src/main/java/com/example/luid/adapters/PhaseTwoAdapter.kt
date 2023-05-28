package com.example.luid.adaptersQ

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.SentenceFragment
import java.util.ArrayList
import com.example.luid.classes.SMLeitner
import com.example.luid.database.DBConnect

class PhaseTwoAdapter(
    private val recyclerView: RecyclerView,
    private val questionList: ArrayList<SentenceFragment>,
    private val progressBar: ProgressBar,
    private val context: Context,
    private val level: Int,
    private val phase: Int,
    private val timeSpent: Int
) :
    RecyclerView.Adapter<PhaseTwoAdapter.QuestionViewHolder>() {
    private var sm = SMLeitner(context)

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val question = itemView.findViewById(R.id.question) as TextView
        val questionImage = itemView.findViewById(R.id.questionImage) as ImageView
        val answerLabel = itemView.findViewById(R.id.answerLabel) as TextView
        val flexboxLayout =
            itemView.findViewById(R.id.flexboxLayout) as com.google.android.flexbox.FlexboxLayout
        val clearButton = itemView.findViewById(R.id.clearButton) as Button
        val submitButton = itemView.findViewById(R.id.submitButton) as Button

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sentencefragmentview, parent, false)


        return QuestionViewHolder(view)

    }

    override fun getItemCount(): Int {
        return questionList.size

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {




        val question = questionList[position]
        val deconstructedWords = question.getDeconstructedSentence()

        holder.question.text = question.question
        holder.questionImage.setImageResource(question.sentenceImage)



        for (word in deconstructedWords) {
            val wordView = TextView(holder.itemView.context)
            wordView.text = word
            wordView.textSize = 20f
            wordView.setPadding(10, 10, 10, 10)
            wordView.setBackgroundResource(R.drawable.rounded_corner)

            wordView.setOnClickListener {
                holder.answerLabel.text =
                    holder.answerLabel.text.toString() + wordView.text.toString() + " "
                wordView.isEnabled = false

            }


            val params = com.google.android.flexbox.FlexboxLayout.LayoutParams(
                com.google.android.flexbox.FlexboxLayout.LayoutParams.WRAP_CONTENT,
                com.google.android.flexbox.FlexboxLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(10, 10, 10, 10)
            wordView.layoutParams = params
            holder.flexboxLayout.addView(wordView)


            // submit button listener
            holder.submitButton.setOnClickListener {

              var correctAns = ""
                var db = DBConnect(context).readableDatabase
                var cursor = db.rawQuery(
                    "SELECT * FROM questiontable_tmp WHERE kapampangan = ?  OR tagalog = ? OR english = ?",
                    arrayOf("$correctAns","$correctAns","$correctAns")
                )
                cursor.moveToFirst()
                var id = cursor.getInt(0)
                cursor.close()
                db.close()

                if (holder.answerLabel.text.replace("\\s+".toRegex(), "") == question.sentence.replace("\\s+".toRegex(), "")) {
                    holder.answerLabel.setTextColor(Color.parseColor("#037d50"))
                } else {
                    holder.answerLabel.setTextColor(Color.parseColor("#FF0000"))
                }
                println(holder.answerLabel.text.replace("\\s+".toRegex(), ""))
                println(question.sentence.replace("\\s+".toRegex(), ""))

                if (position < questionList.size - 1) {
                    // proceed to the next question using snapHelper
                    recyclerView.smoothScrollToPosition(position + 1)
                    progressBar.progress +=1

                    sm.smLeitnerCalc(context, id, level, phase, true, timeSpent)





                } else {
                    // navController.navigate(R.id.action_wordAssociation_to_tabPhaseReview)
                    sm.smLeitnerCalc(context, id, level, phase, false, timeSpent)
                }
            }

        }

        holder.clearButton.setOnClickListener {
            holder.answerLabel.text = ""
            for (i in 0 until holder.flexboxLayout.childCount) {
                val child = holder.flexboxLayout.getChildAt(i)
                child.isEnabled = true


            }
        }
    }
}