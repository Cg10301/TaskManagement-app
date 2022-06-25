package com.example.testdata

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testdata.adapter.TaskAdapter
import com.example.testdata.data.AllData
import com.example.testdata.model.Task
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.layout_task_item.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class NewTask_Activity : AppCompatActivity() {

    var formatDate = SimpleDateFormat("dd-MM-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task)
        val curDate = LocalDate.now()
        val strCurDate = curDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        textDateIn.setText(strCurDate)
        textDateOut.setText(strCurDate)

        textView3.setOnClickListener {
            val getDate : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth, DatePickerDialog.OnDateSetListener{
                datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH,i3)
                val date : String =  formatDate.format(selectDate.time)
                Toast.makeText(this, "Date: "+date, Toast.LENGTH_SHORT  ).show()
                textDateIn.setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        textView6.setOnClickListener {
            val getDate : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth, DatePickerDialog.OnDateSetListener{
                    datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH,i3)
                val date : String =  formatDate.format(selectDate.time)
                Toast.makeText(this, "Date: "+date, Toast.LENGTH_SHORT  ).show()
                textDateOut.setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        btnAdd.setOnClickListener{
            val intent = Intent()
            intent.putExtra("title", txtTitle.text.toString())
            intent.putExtra("content", txtContent.text.toString())
            intent.putExtra("dateIn", textDateIn.text.toString())
            intent.putExtra("dateOut", textDateOut.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        btnExit.setOnClickListener{
            val intent = Intent()
            setResult(Activity.RESULT_CANCELED, intent)
            finish()
        }


    }



}