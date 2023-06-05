package com.example.luid.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.classes.LevelSelection
import com.example.luid.R

class  LevelAdapter(private val levelList: List<LevelSelection>) :
    RecyclerView.Adapter<LevelAdapter.LevelViewHolder>() {

    // var for listeners to be used in home fragment
    private var onItemClick : ((LevelSelection) -> Unit)? = null

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val levelId: TextView = itemView.findViewById(R.id.level_id)
        val levelTitle: TextView = itemView.findViewById(R.id.level_title)
        val levelTitleAlt: TextView = itemView.findViewById(R.id.level_titleAlt)
        val levelImage: ImageView = itemView.findViewById(R.id.level_image)
        val levelDescription: TextView = itemView.findViewById(R.id.level_description)
        val button:Button = itemView.findViewById(R.id.levelButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eachlevelview, parent, false)
        return LevelViewHolder(view)
    }



    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levelList[position]
        holder.levelId.text = level.levelID
        holder.levelTitle.text = level.levelTitle
        holder.levelTitleAlt.text = level.levelTitleAlt
        holder.levelImage.setImageResource(level.levelImage)
        holder.levelDescription.text = level.levelDescription

        // Disable the card view if isEnabled is false
        holder.itemView.isEnabled = level.isEnabled

        if (!level.isEnabled) {
            // Set the disabled appearance for the card
            holder.itemView.alpha = 0.5f // Example: Reduce the opacity of the card
            holder.itemView.isClickable = false // Disable click events on the card
            // Add any additional styling you want for the disabled state
        } else {
            // Set the enabled appearance for the card
            holder.itemView.alpha = 1.0f // Example: Set the opacity back to normal
            holder.itemView.isClickable = true // Enable click events on the card
            // Add any additional styling you want for the enabled state
        }

        //disable the card view click
        holder.itemView.isClickable = false


        holder.button.setOnClickListener {
            onItemClick?.invoke(level)
        }
    }

    override fun getItemCount(): Int {
        return levelList.size
    }

    //function to be passed to the HomeFragment
    fun setOnItemClickListener(listener: (LevelSelection) -> Unit) {
        onItemClick = listener
    }
}




