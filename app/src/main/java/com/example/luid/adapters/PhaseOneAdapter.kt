package com.example.luid.adapters

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
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.luid.classes.WordAssociationClass
import com.google.android.material.color.MaterialColors
import java.util.*
import java.util.Collections.shuffle

class PhaseOneAdapter(private val recyclerView: RecyclerView, private val questionlist: List<WordAssociationClass>) :
    RecyclerView.Adapter<PhaseOneAdapter.QuestionViewHolder>() {
    private var tempAns: String = ""
    private var correctAns: String = ""

    inner class QuestionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

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



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.word_association_view, parent, false)
        return QuestionViewHolder(view)
    }

    override fun getItemCount(): Int {
        return questionlist.size
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        val question = questionlist[position]
        holder.question.text = question.questions



        correctAns = question.correct






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



        // Set click listeners or perform other operations as needed for the choices
        holder.choice1.setOnClickListener {
            // Handle choice 1 selection
            setCardElevation(holder.choice1, 3f, holder.choice1.context)
            setCardElevation(holder.choice2, 1f, holder.choice2.context)
            setCardElevation(holder.choice3, 1f, holder.choice3.context)
            setCardElevation(holder.choice4, 1f, holder.choice4.context)

            //change also the background color of the card
            holder.choice1.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            holder.choice2.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice3.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice4.setCardBackgroundColor(Color.parseColor("#FFFBFF"))


            tempAns = holder.choice1Text.text.toString()
        }

        holder.choice2.setOnClickListener {
            // Handle choice 2 selection
            setCardElevation(holder.choice1, 1f, holder.choice1.context)
            setCardElevation(holder.choice2, 3f, holder.choice2.context)
            setCardElevation(holder.choice3, 1f, holder.choice3.context)
            setCardElevation(holder.choice4, 1f, holder.choice4.context)

            holder.choice1.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice2.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            holder.choice3.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice4.setCardBackgroundColor(Color.parseColor("#FFFBFF"))

            tempAns = holder.choice2Text.text.toString()

        }

        holder.choice3.setOnClickListener {
            // Handle choice 3 selection
            setCardElevation(holder.choice1, 1f, holder.choice1.context)
            setCardElevation(holder.choice2, 1f, holder.choice2.context)
            setCardElevation(holder.choice3, 3f, holder.choice3.context)
            setCardElevation(holder.choice4, 1f, holder.choice4.context)


            holder.choice1.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice2.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice3.setCardBackgroundColor(Color.parseColor("#E9DDFF"))
            holder.choice4.setCardBackgroundColor(Color.parseColor("#FFFBFF"))

            tempAns = holder.choice3Text.text.toString()
        }

        holder.choice4.setOnClickListener {
            // Handle choice 4 selection
            setCardElevation(holder.choice1, 1f, holder.choice1.context)
            setCardElevation(holder.choice2, 1f, holder.choice2.context)
            setCardElevation(holder.choice3, 1f, holder.choice3.context)
            setCardElevation(holder.choice4, 3f, holder.choice4.context)

            holder.choice1.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice2.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice3.setCardBackgroundColor(Color.parseColor("#FFFBFF"))
            holder.choice4.setCardBackgroundColor(Color.parseColor("#E9DDFF"))

            tempAns = holder.choice4Text.text.toString()
        }

        holder.submitButton.setOnClickListener {
            // Handle submit button click
            // Check if the correct choice is selected
            // If correct, show success message
            // If incorrect, show failure message

            if (tempAns == correctAns) {
                Toast.makeText(holder.submitButton.context, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(holder.submitButton.context, "Incorrect!", Toast.LENGTH_SHORT)
                    .show()
            }

            if (position < questionlist.size - 1) {
                // proceed to the next question using snapHelper
                recyclerView.smoothScrollToPosition(position + 1)




            } else {
               // navController.navigate(R.id.action_wordAssociation_to_tabPhaseReview)
            }

        }

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



