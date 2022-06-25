package com.example.testdata.adapter

import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.testdata.R
import com.example.testdata.model.Task
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

class TaskAdapter(val dataset: MutableList<Task>,
                    val listener: MyOnClickListener
) : RecyclerView.Adapter<TaskAdapter.ItemViewHolder>(){
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    inner class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {


        val titleTextView: TextView = view.findViewById(R.id.titleTask);
        val contentTextView: TextView = view.findViewById(R.id.contentTask);
        val constraintLayout: ConstraintLayout = view.findViewById(R.id.constraintLayout)
        val txvState: TextView = view.findViewById(R.id.txvState)
        val txtDateIn: TextView = view.findViewById(R.id.txtDateIn)
        val txtDateOut: TextView = view.findViewById(R.id.txtDateOut)
        val textView5:TextView = view.findViewById(R.id.textView5)
        val textView7:TextView = view.findViewById(R.id.textView7)
        init {
            itemView.setOnClickListener{
                val position = adapterPosition
                listener.OnClick(position)
            }

//            buttonState.setOnClickListener {
//                val position = adapterPosition
//                listener.OnClickBtnState(position)
//            }
        }


    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_task_item, parent, false)



        return ItemViewHolder(adapterLayout)

    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = dataset[position]
        val curdate = LocalDate.now()
        val dateOut = LocalDate.parse(item.dateOut, formatter)
        val dateIn = LocalDate.parse(item.dateIn, formatter)

        holder.titleTextView.text = item.title;
        holder.contentTextView.text = item.content;
        holder.txtDateIn.text = item.dateIn
        holder.txtDateOut.text = item.dateOut

        if(dataset[position].state == Task.OPEN) {
            if(curdate > dateOut) {
                holder.txvState.setText("NOT DONE")
                holder.constraintLayout.setBackgroundColor(Color.parseColor("#FF3333"))
            }
            else{
                holder.txvState.setText("OPEN")
                holder.constraintLayout.setBackgroundColor(Color.parseColor("#666666"))
            }
        }else if(dataset[position].state == Task.DOING){
            if(curdate > dateOut) {
                holder.txvState.setText("NOT DONE")
                holder.constraintLayout.setBackgroundColor(Color.parseColor("#FF3333"))
            }else{
                holder.txvState.setText("DOING")
                holder.constraintLayout.setBackgroundColor(Color.parseColor("#FF9933"))
            }

        }else{
            holder.txvState.setText("DONE")
            holder.constraintLayout.setBackgroundColor(Color.parseColor("#9999FF"))
            holder.titleTextView.apply{
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.contentTextView.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.textView5.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.textView7.apply {
                paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            }
            holder.txtDateIn.apply { paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }
            holder.txtDateOut.apply { paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG }

        }
    }



    override fun getItemCount() = dataset.size;

    interface MyOnClickListener{
        fun OnClick(position: Int)

    }
}