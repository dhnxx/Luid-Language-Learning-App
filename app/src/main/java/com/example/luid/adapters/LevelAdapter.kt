package com.example.luid.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.LevelSelection
import com.example.luid.R

class  LevelAdapter(private val levelList: List<LevelSelection>,) :
    RecyclerView.Adapter<LevelAdapter.LevelViewHolder>() {

    // var for listeners to be used in home fragment
    private var onItemClick : ((LevelSelection) -> Unit)? = null

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
        val text = level.levelID
        holder.levelId.text = level.levelID
        holder.levelTitle.text = level.levelTitle
        holder.levelImage.setImageResource(level.levelImage)
        holder.levelDescription.text = level.levelDescription

        holder.itemView.setOnClickListener {
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




