package com.dhn.luid.adapters

import android.database.sqlite.SQLiteDatabase
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhn.luid.R
import com.dhn.luid.classes.ParentPhase
import com.dhn.luid.classes.SMLeitner


class ParentPhaseAdapter(
    private val parentList: List<ParentPhase>,
    private val db: SQLiteDatabase
) :
    RecyclerView.Adapter<ParentPhaseAdapter.ParentViewHolder>() {


    inner class ParentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val phaseTitle: TextView = itemView.findViewById(R.id.parentTitle)
        val childRecyclerView: RecyclerView = itemView.findViewById(R.id.childRecyclerView)
        val indicator: ImageView = itemView.findViewById(R.id.indicator)
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
        val sm = SMLeitner()
        sm.ifPassed(db, parentPhase.level, parentPhase.phase)

        holder.phaseTitle.text = parentPhase.title
        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager =
            LinearLayoutManager(
                holder.itemView.context,
                GridLayoutManager(holder.itemView.context, 1).orientation,
                false
            )




        if (sm.ifPassed(db, parentPhase.level, parentPhase.phase)) {
            holder.indicator.setImageResource(R.drawable.check)
        } else {
            holder.indicator.setImageResource(R.drawable.wrong)
        }


        holder.childRecyclerView.adapter = ChildPhaseAdapter(parentPhase.PhaseList)

        val adapter = ChildPhaseAdapter(parentPhase.PhaseList)
        holder.childRecyclerView.adapter = adapter

    }

}