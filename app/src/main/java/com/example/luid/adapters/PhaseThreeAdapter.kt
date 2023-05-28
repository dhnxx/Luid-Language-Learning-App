package com.example.luid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.SMLeitner
import com.example.luid.database.DBConnect
import kotlin.collections.ArrayList

class PhaseThreeAdapter(
    private val recyclerView: RecyclerView,
    private val questionList: ArrayList<com.example.luid.classes.SentenceConstruction>,
    private val context: Context
) :
    RecyclerView.Adapter<PhaseThreeAdapter.QuestionViewHolder>() {
    private var sm = SMLeitner(context)

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val question = itemView.findViewById(R.id.question) as TextView
        val questionImage = itemView.findViewById(R.id.questionImage) as ImageView
        val answerLabel = itemView.findViewById(R.id.answerLabel) as EditText
        val flexboxLayout =
            itemView.findViewById(R.id.flexboxLayout) as com.google.android.flexbox.FlexboxLayout
        val clearButton = itemView.findViewById(R.id.clearButton) as Button
        val submitButton = itemView.findViewById(R.id.submitButton) as Button
        var progressbar: ProgressBar = itemView.findViewById(R.id.progressBar)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.sentencecontruction_view, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {

        return questionList.size

    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionList[position]

        recyclerView.isNestedScrollingEnabled = false

        holder.progressbar.progress = 1
        holder.progressbar.max = questionList.size


        holder.question.text = question.question
        holder.questionImage.setImageResource(question.sentenceImage)

        holder.answerLabel.hint = "Enter your answer here"

        holder.submitButton.setOnClickListener {
            val answer = holder.answerLabel.text.toString()
            var db = DBConnect(context).readableDatabase
            var cursor = db.rawQuery("SELECT * FROM questiontable_tmp WHERE $correctAns = kapampangan OR $correctAns = tagalog OR $correctAns = english", null)
            var id = cursor.getInt(0)
            cursor.close()
            db.close()


            if (answer == question.sentence) {

                Toast.makeText(holder.itemView.context, "Correct!", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(holder.itemView.context, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
            if (position < questionList.size - 1) {
                // proceed to the next question using snapHelper
                recyclerView.smoothScrollToPosition(position + 1)
                holder.progressbar.progress = holder.progressbar.progress + 1
                sm.smLeitnerCalc(context, id, level, phase, true, timeSpent)

            } else {
                // navController.navigate(R.id.action_wordAssociation_to_tabPhaseReview)
                sm.smLeitnerCalc(context, id, level, phase, false, timeSpent)


            }
        }

        holder.clearButton.setOnClickListener {
            holder.answerLabel.text.clear()
        }


    }
}