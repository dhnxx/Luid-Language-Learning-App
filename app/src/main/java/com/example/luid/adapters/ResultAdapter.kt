package com.example.luid.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.luid.R
import com.example.luid.classes.ResultScreen

class ResultAdapter(private val resultList: ArrayList<ResultScreen>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val word = itemView.findViewById(R.id.word) as TextView
        val ef = itemView.findViewById(R.id.ef) as TextView
        val df = itemView.findViewById(R.id.df) as TextView
        val status = itemView.findViewById(R.id.indicator) as ImageView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.resultcard, parent, false)
        return ResultViewHolder(view)
    }

    override fun getItemCount(): Int {
      return resultList.size
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {


val result = resultList[position]
        holder.word.text = result.word
        holder.ef.text = result.finalEF.toString()
        holder.df.text = result.finalDF.toString()

        /*
        if(result.status == 1){
            holder.status.setImageResource(R.drawable.ic_baseline_check_circle_24)
        }else{
            holder.status.setImageResource(R.drawable.ic_baseline_cancel_24)
        }

*/



    }

}