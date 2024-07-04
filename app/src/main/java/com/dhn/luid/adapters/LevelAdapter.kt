package com.dhn.luid.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhn.luid.classes.LevelSelection
import com.dhn.luid.R
import com.dhn.luid.classes.SMLeitner
import com.dhn.luid.database.DBConnect


class LevelAdapter(private val levelList: List<LevelSelection>, private val contextExt: Context) :
    RecyclerView.Adapter<LevelAdapter.LevelViewHolder>() {

    // var for listeners to be used in home fragment
    private var onItemClick: ((LevelSelection) -> Unit)? = null

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //  val levelId: TextView = itemView.findViewById(R.id.level_id)
        val levelTitle: TextView = itemView.findViewById(R.id.level_title)
        val levelTitleAlt: TextView = itemView.findViewById(R.id.level_titleAlt)
        val levelImage: ImageView = itemView.findViewById(R.id.level_image)
        val levelDescription: TextView = itemView.findViewById(R.id.level_description)
        val button: Button = itemView.findViewById(R.id.levelButton)
        val progressBar: ProgressBar = itemView.findViewById(R.id.achvprogressBar)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.eachlevelview, parent, false)
        return LevelViewHolder(view)

    }


    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val db = DBConnect(contextExt).readableDatabase
        val sm = SMLeitner()

        val level = levelList[position]
        var lvl = level.level

        // holder.levelId.text = level.levelID
        holder.levelTitle.text = level.levelTitle
        holder.levelTitleAlt.text = level.levelTitleAlt
        holder.levelImage.setImageResource(level.levelImage)
        holder.levelDescription.text = level.levelDescription
        holder.progressBar.progress = 0
        holder.progressBar.max = 3


        // Disable the card view if isEnabled is false
        holder.itemView.isEnabled = level.isEnabled

        if (!level.isEnabled) {
            // Set the disabled appearance for the card
            holder.itemView.alpha = 0.5f // Example: Reduce the opacity of the card
            holder.itemView.isClickable = false // Disable click events on the card
            holder.button.isEnabled = false
        } else {
            // Set the enabled appearance for the card
            holder.itemView.alpha = 1.0f // Example: Set the opacity back to normal
            holder.itemView.isClickable = true // Enable click events on the card
            holder.button.isEnabled = true
        }



        if (sm.ifPassed(db, lvl, 1)) {
            holder.progressBar.progress = 1
            if (sm.ifPassed(db, lvl, 2)) {
                holder.progressBar.progress = 2
                if (sm.ifPassed(db, lvl, 3)) {
                    holder.progressBar.progress = 3
                }
            }
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




