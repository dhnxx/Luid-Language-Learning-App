package com.example.luid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.StoryClass

class StoryAdapter(private val storyList: List<StoryClass>) :
    RecyclerView.Adapter<StoryAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val otherLayout: LinearLayout = itemView.findViewById(R.id.otherLayout)
        val userLayout: LinearLayout = itemView.findViewById(R.id.userLayout)
        val otherImage: ImageView = itemView.findViewById(R.id.otherImage)
        val userImage: ImageView = itemView.findViewById(R.id.userImage)
        val otherName: TextView = itemView.findViewById(R.id.otherName)
        val userName: TextView = itemView.findViewById(R.id.userName)
        val otherMessage: TextView = itemView.findViewById(R.id.otherMessage)
        val userMessage: TextView = itemView.findViewById(R.id.userMessage)
        val otherMessageAlt: TextView = itemView.findViewById(R.id.otherMessageAlt)
        val userMessageAlt: TextView = itemView.findViewById(R.id.userMessageAlt)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.storyview, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return storyList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val currentItem = storyList[position]

        holder.otherLayout.visibility = View.GONE
        holder.userLayout.visibility = View.GONE

        if (currentItem.boolean) {
            holder.otherLayout.visibility = View.GONE
            holder.userLayout.visibility = View.VISIBLE
            holder.userImage.setImageResource(currentItem.charImage)
            holder.userName.text = currentItem.char
            holder.userMessage.text = currentItem.message
            if (currentItem.messageAlt == "") {
                holder.userMessageAlt.visibility = View.GONE
            } else {
                holder.userMessageAlt.text = currentItem.messageAlt
            }
        } else {
            holder.otherLayout.visibility = View.VISIBLE
            holder.userLayout.visibility = View.GONE
            holder.otherImage.setImageResource(currentItem.charImage)
            holder.otherName.text = currentItem.char
            holder.otherMessage.text = currentItem.message
            if (currentItem.messageAlt == "") {
                holder.otherMessageAlt.visibility = View.GONE
            } else {
                holder.otherMessageAlt.text = currentItem.messageAlt
            }
        }

    }


}