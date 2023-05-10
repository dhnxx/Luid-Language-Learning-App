package com.example.luid.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.LevelSelection
import com.example.luid.R

class  LevelAdapter(private val levelList: List<LevelSelection>) :
    RecyclerView.Adapter<LevelAdapter.LevelViewHolder>() {

    class LevelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val levelId: TextView = itemView.findViewById(R.id.level_id)
        val levelTitle: TextView = itemView.findViewById(R.id.level_title)
        val levelImage: ImageView = itemView.findViewById(R.id.level_image)
        val levelDescription: TextView = itemView.findViewById(R.id.level_description)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.eachlevelview, parent, false)
        return LevelViewHolder(view)
    }



    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levelList[position]
        holder.levelId.text = level.levelID
        holder.levelTitle.text = level.levelTitle
        holder.levelImage.setImageResource(level.levelImage)
        holder.levelDescription.text = level.levelDescription

        holder.itemView.setOnClickListener {
            // onclicklistener for each item in the level selection
            Toast.makeText(holder.itemView.context, "Item clicked at position $position", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return levelList.size
    }
}




