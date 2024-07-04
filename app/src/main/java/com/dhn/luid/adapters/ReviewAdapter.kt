package com.dhn.luid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhn.luid.R
import com.dhn.luid.classes.Review

class ReviewAdapter (private val reviewList: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>()

{
    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val kapWord: TextView = itemView.findViewById(R.id.kapWord)
        val engWord: TextView = itemView.findViewById(R.id.engWord)
        val tagWord: TextView = itemView.findViewById(R.id.tagWord)
        val indicator: ImageView = itemView.findViewById(R.id.indicator)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.reviewcard, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {

        val review = reviewList[position]
        holder.kapWord.text = review.kapWord
        holder.engWord.text = review.engWord
        holder.tagWord.text = review.tagWord

    }

}