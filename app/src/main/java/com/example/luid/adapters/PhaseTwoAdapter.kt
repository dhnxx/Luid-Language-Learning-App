package com.example.luid.adaptersQ

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.SentenceFragment
import java.util.ArrayList

class PhaseTwoAdapter(
    private val recyclerView: RecyclerView,
    private val questionList: ArrayList<SentenceFragment>
) :
    RecyclerView.Adapter<PhaseTwoAdapter.QuestionViewHolder>() {

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


        holder.question.text = question.sentence
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





            holder.submitButton.setOnClickListener {



                if (holder.answerLabel.text.replace("\\s+".toRegex(), "") == question.sentence.replace("\\s+".toRegex(), "")) {
                    holder.answerLabel.setTextColor(Color.parseColor("#037d50"))
                } else {
                    holder.answerLabel.setTextColor(Color.parseColor("#FF0000"))
                }
                println(holder.answerLabel.text.replace("\\s+".toRegex(), ""))
                println(question.sentence.replace("\\s+".toRegex(), ""))
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