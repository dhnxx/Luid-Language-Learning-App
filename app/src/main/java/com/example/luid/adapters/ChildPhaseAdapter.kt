package com.example.luid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.ChildPhase

class ChildPhaseAdapter(private val childList: List<ChildPhase>): RecyclerView.Adapter<ChildPhaseAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val childTitle : TextView = itemView.findViewById(R.id.childTitle)
        val childDescription : TextView = itemView.findViewById(R.id.childDescription)
        val childImage : ImageView = itemView.findViewById(R.id.childImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.childphasecard, parent, false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {

        val childPhase = childList[position]
        holder.childTitle.text = childList[position].title
        holder.childDescription.text = childList[position].description
        holder.childImage.setImageResource(childList[position].image)
    }
}