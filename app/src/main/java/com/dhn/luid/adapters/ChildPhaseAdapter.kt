package com.dhn.luid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhn.luid.R
import com.dhn.luid.classes.ChildPhase

class ChildPhaseAdapter(private val childList: List<ChildPhase>): RecyclerView.Adapter<ChildPhaseAdapter.ChildViewHolder>() {

    inner class ChildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val childTitle: TextView = itemView.findViewById(R.id.childTitle)
        val childAltTitle : TextView = itemView.findViewById(R.id.childAltTitle)
        val childDescription: TextView = itemView.findViewById(R.id.childDescription)
        val childImage: ImageView = itemView.findViewById(R.id.childImage)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.childphasecard, parent, false)
        return ChildViewHolder(view)
    }

    override fun getItemCount(): Int {
        return childList.size
    }

    override fun onBindViewHolder(holder: ChildViewHolder, position: Int) {
        val childPhase = childList[position]
        holder.childTitle.text = childPhase.title
        holder.childAltTitle.text = childPhase.altTitle
        holder.childDescription.text = childPhase.description
        holder.childImage.setImageResource(childPhase.image)
        val childButton = holder.itemView.findViewById<Button>(R.id.childButton)

        // Disable the card view if isEnabled is false
        holder.itemView.isEnabled = childPhase.isEnabled

        if (!childPhase.isEnabled) {
            // Set the disabled appearance for the card
            holder.itemView.alpha = 0.5f // Example: Reduce the opacity of the card
            holder.itemView.isClickable = false // Disable click events on the card
            // Add any additional styling you want for the disabled state
            childButton.isEnabled = false
        } else {
            // Set the enabled appearance for the card
            holder.itemView.alpha = 1.0f // Example: Set the opacity back to normal
            holder.itemView.isClickable = true // Enable click events on the card
            // Add any additional styling you want for the enabled state
            childButton.isEnabled = true
            childButton.setOnClickListener(childPhase.onClickListener)
        }


    }
}
