package com.example.luid.adapters

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.database.DBConnect
import kotlin.collections.ArrayList

class PhaseThreeAdapter(
    private val recyclerView: RecyclerView,
    private val questionList: ArrayList<com.example.luid.classes.SentenceConstruction>,
) :
    RecyclerView.Adapter<PhaseThreeAdapter.QuestionViewHolder>() {

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

    @SuppressLint("Range")
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



            if (answer == question.sentence) {

                Toast.makeText(holder.itemView.context, "Correct!", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(holder.itemView.context, "Incorrect!", Toast.LENGTH_SHORT).show()
            }
            if (position < questionList.size - 1) {
                // proceed to the next question using snapHelper
                recyclerView.smoothScrollToPosition(position + 1)
                holder.progressbar.progress = holder.progressbar.progress + 1

            } else {
                // navController.navigate(R.id.action_wordAssociation_to_tabPhaseReview)

                var db = DBConnect(holder.itemView.context).readableDatabase

                var cursor: Cursor

                cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = 3 AND phase = 3", null)

                if(cursor.moveToNext()){

                    val cv = ContentValues()

                    do {

                        val id = cursor.getLong(cursor.getColumnIndex("_id"))

                        val gamesession = cursor.getInt(cursor.getColumnIndex("game_session"))

                        val EaFa = cursor.getDouble(cursor.getColumnIndex("easiness_factor"))

                        val interval = cursor.getInt(cursor.getColumnIndex("interval"))

                        val difflevel = cursor.getInt(cursor.getColumnIndex("difficulty_level"))

                        val timviewed = cursor.getInt(cursor.getColumnIndex("times_viewed"))

                        cv.put("game_session", gamesession)
                        cv.put("easiness_factor", EaFa)
                        cv.put("interval", interval)
                        cv.put("difficulty_level", difflevel)
                        cv.put("times_viewed", timviewed)

                        db.update("${DBConnect.questions_tb}", cv, "_id = $id", null)

                    } while(cursor.moveToNext())
                }
                db.execSQL("DROP TABLE IF EXISTS ${DBConnect.temp_qstion}")
                db.close()
                cursor.close()

            }
        }

        holder.clearButton.setOnClickListener {
            holder.answerLabel.text.clear()
        }
        var db = DBConnect(holder.itemView.context).readableDatabase
        var cursor: Cursor
        cursor = db.rawQuery("SELECT * FROM ${DBConnect.temp_qstion} WHERE level = 3 AND phase = 3", null)
        val gs = 0
        val ef = 0
        val inter = 0
        val difflvl = 0
        val tv = 0
        if(cursor.moveToFirst()){
            do {
                val cv = ContentValues()
                val id = cursor.getLong(cursor.getColumnIndex("_id"))
                // val gamesession = cursor.getInt(cursor.getColumnIndex("game_session"))
                cv.put("game_session", gs)
                // val EaFa = cursor.getDouble(cursor.getColumnIndex("easiness_factor"))
                cv.put("easiness_factor", ef)
                // val interval = cursor.getInt(cursor.getColumnIndex("interval"))
                cv.put("interval", inter)
                // val difflevel = cursor.getInt(cursor.getColumnIndex("difficulty_level"))
                cv.put("difficulty_level", difflvl)
                // val timviewed = cursor.getInt(cursor.getColumnIndex("times_viewed"))
                cv.put("times_viewed", tv)
                db.update("${DBConnect.temp_qstion}", cv, "_id = $id", null)
            } while(cursor.moveToNext())
            cursor.close()
        }

    }
    // Inside your class, preferably in the constructor or an initialization method

}