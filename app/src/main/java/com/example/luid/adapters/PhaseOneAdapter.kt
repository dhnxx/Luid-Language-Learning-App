package com.example.luid.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import android.content.Context
import android.graphics.Color
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.luid.classes.SMLeitner
import com.example.luid.classes.WordAssociationClass
import java.util.*


class PhaseOneAdapter(
    private val recyclerView: RecyclerView,
    private val questionList: ArrayList<WordAssociationClass>,
   private val progressBar: ProgressBar,
   private val context: Context
) :
    RecyclerView.Adapter<PhaseOneAdapter.QuestionViewHolder>() {
    private var tempAns: String = ""
    private var correctAns: String = ""
    private var correctAnswer = 0
    private var score = 0.0


    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val question: TextView = itemView.findViewById(R.id.question)
        val choice1: CardView = itemView.findViewById(R.id.choice1)
        val choice1Text: TextView = itemView.findViewById(R.id.choice1Text)
        val choice1Image: ImageView = itemView.findViewById(R.id.choice1Image)
        val choice2: CardView = itemView.findViewById(R.id.choice2)
        val choice2Text: TextView = itemView.findViewById(R.id.choice2Text)
        val choice2Image: ImageView = itemView.findViewById(R.id.choice2Image)
        val choice3: CardView = itemView.findViewById(R.id.choice3)
        val choice3Text: TextView = itemView.findViewById(R.id.choice3Text)
        val choice3Image: ImageView = itemView.findViewById(R.id.choice3Image)
        val choice4: CardView = itemView.findViewById(R.id.choice4)
        val choice4Text: TextView = itemView.findViewById(R.id.choice4Text)
        val choice4Image: ImageView = itemView.findViewById(R.id.choice4Image)
        val submitButton: Button = itemView.findViewById(R.id.submitButton)
        val usrtxt: TextView = itemView.findViewById(R.id.usrtxt)
        val anstxt: TextView = itemView.findViewById(R.id.anstxt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_association_view, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questionList.size
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {


        val question = questionList[position]

        recyclerView.isNestedScrollingEnabled = false

        holder.question.text = question.questions


        correctAns = question.correct
        holder.anstxt.text = correctAns
        holder.usrtxt.text = tempAns
        // Create a list of choices and shuffle them

        holder.usrtxt.visibility = View.GONE
        holder.anstxt.visibility = View.GONE


        val choices = mutableListOf(
            Choice(question.correct, question.correctImg),
            Choice(question.decoy1, question.decoy1Img),
            Choice(question.decoy2, question.decoy2Img),
            Choice(question.decoy3, question.decoy3Img)
        )

        choices.shuffle()
        holder.choice1Text.text = choices[0].text
        holder.choice1Image.setImageResource(choices[0].imageResId)
        holder.choice2Text.text = choices[1].text
        holder.choice2Image.setImageResource(choices[1].imageResId)
        holder.choice3Text.text = choices[2].text
        holder.choice3Image.setImageResource(choices[2].imageResId)
        holder.choice4Text.text = choices[3].text
        holder.choice4Image.setImageResource(choices[3].imageResId)


        fun cardReset() {
            holder.choice1.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice2.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice3.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice4.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            setCardElevation(holder.choice1, 1f, holder.choice1.context)
            setCardElevation(holder.choice2, 1f, holder.choice2.context)
            setCardElevation(holder.choice3, 1f, holder.choice3.context)
            setCardElevation(holder.choice4, 1f, holder.choice4.context)
        }

        // Set click listeners or perform other operations as needed for the choices
        holder.choice1.setOnClickListener {

            cardReset()
            setCardElevation(holder.choice1, 3f, holder.choice1.context)
            holder.choice1.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAns = holder.choice1Text.text.toString()
            holder.usrtxt.text = tempAns
        }

        holder.choice2.setOnClickListener {
            cardReset()

            setCardElevation(holder.choice2, 3f, holder.choice2.context)
            holder.choice2.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAns = holder.choice2Text.text.toString()
            holder.usrtxt.text = tempAns

        }

        holder.choice3.setOnClickListener {
            cardReset()
            setCardElevation(holder.choice3, 3f, holder.choice3.context)
            holder.choice3.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAns = holder.choice3Text.text.toString()
            holder.usrtxt.text = tempAns
        }

        holder.choice4.setOnClickListener {
            cardReset()
            setCardElevation(holder.choice4, 3f, holder.choice4.context)
            holder.choice4.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            tempAns = holder.choice4Text.text.toString()
            holder.usrtxt.text = tempAns
        }

        holder.submitButton.setOnClickListener {
            if (holder.usrtxt.text == holder.anstxt.text) {
                Toast.makeText(holder.submitButton.context, "Correct!", Toast.LENGTH_SHORT).show()
                //change color of card to green if correct
                if (holder.choice1Text.text == holder.usrtxt.text) {
                    holder.choice1.setCardBackgroundColor(Color.parseColor("#C8FFC8"))
                } else if (holder.choice2Text.text == holder.usrtxt.text) {
                    holder.choice2.setCardBackgroundColor(Color.parseColor("#C8FFC8"))
                } else if (holder.choice3Text.text == holder.usrtxt.text) {
                    holder.choice3.setCardBackgroundColor(Color.parseColor("#C8FFC8"))
                } else if (holder.choice4Text.text == holder.usrtxt.text) {
                    holder.choice4.setCardBackgroundColor(Color.parseColor("#C8FFC8"))
                }
                correctAnswer += 1


            } else {
                Toast.makeText(holder.submitButton.context, "Incorrect!", Toast.LENGTH_SHORT)
                    .show()


            }


            if (position < questionList.size - 1) {
                // proceed to the next question using snapHelper
                recyclerView.smoothScrollToPosition(position + 1)
          progressBar.progress +=  1

            } else {


                // navController.navigate(R.id.action_wordAssociation_to_tabPhaseReview)

                // dialog appear
                // merge temp table to main table
                // show score/stats etc...


            }
            var sm = SMLeitner(context)
            score =  sm.score(correctAnswer,questionList.size)

            println(questionList.size)
            println(correctAnswer)
            println(score)

            tempAns = "" // reset tempAns
            correctAns = "" // reset correctAns
        }


        // update temptable


    }


    data class Choice(val text: String, @DrawableRes val imageResId: Int)

    // Function to set card elevation for a choice
    private fun setCardElevation(choice: CardView, elevationInDp: Float, context: Context) {
        val elevationInPixels = convertDpToPx(elevationInDp, context)
        choice.cardElevation = elevationInPixels
    }

    private fun convertDpToPx(dp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }


}



