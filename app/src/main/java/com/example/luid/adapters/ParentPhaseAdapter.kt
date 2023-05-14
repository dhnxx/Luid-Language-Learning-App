package com.example.luid.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.ParentPhase



class ParentPhaseAdapter(private val parentList: List<ParentPhase>) :
    RecyclerView.Adapter<ParentPhaseAdapter.ParentViewHolder>() {

    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val phaseTitle: TextView = itemView.findViewById(R.id.parentTitle)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.childRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.parentphasecard, parent, false)
        return ParentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return parentList.size
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {

        val parentPhase = parentList[position]
        holder.phaseTitle.text = parentPhase.title
        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager =
            LinearLayoutManager(
                holder.itemView.context,
                GridLayoutManager(holder.itemView.context, 1).orientation,
                false
            )




        holder.childRecyclerView.adapter = ChildPhaseAdapter(parentPhase.PhaseList)

        val adapter = ChildPhaseAdapter(parentPhase.PhaseList)
        holder.childRecyclerView.adapter = adapter

    }

}