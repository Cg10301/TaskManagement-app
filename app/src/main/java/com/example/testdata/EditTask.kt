package com.example.testdata

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TaskInfo
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.example.testdata.data.AllData
import com.example.testdata.model.Task
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_edit_task.*
import kotlinx.android.synthetic.main.activity_new_task.*
import java.text.SimpleDateFormat
import java.util.*

class EditTask : AppCompatActivity() {
    private var position : Int = 0
    var formatDate = SimpleDateFormat("dd-MM-yyyy")
    private lateinit var allData: AllData;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val intent = intent
        val title : String  = intent.getStringExtra("title").toString()
        val content : String =  intent.getStringExtra("content").toString()
        position = intent.getIntExtra("position", 0)
        val state: Int = intent.getIntExtra("state", 0)
        val dateIn: String  = intent.getStringExtra("dateIn").toString()
        val dateOut: String = intent.getStringExtra("dateOut").toString()

        var txtEditTitle = findViewById<EditText>(R.id.txtEditTitle)
        var txtEditContent = findViewById<EditText>(R.id.txtEditContent)
        var radioGroup: RadioGroup = findViewById(R.id.groupButton)

        var task = Task(0, title, content, state, dateIn, dateOut)


        //loadData()

        //saveData()


        if(state == Task.OPEN)
            radioGroup.check(R.id.option1)
        else if(state == Task.DOING)
            radioGroup.check(R.id.option2)
        else
            radioGroup.check(R.id.option3)
        txtEditTitle.setText(title)
        txtEditContent.setText(content)
        txtEditDateIn.setText(dateIn)
        txtEditDateOut.setText(dateOut)

        textView8.setOnClickListener {
            val getDate : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth, DatePickerDialog.OnDateSetListener{
                    datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH,i3)
                val date : String =  formatDate.format(selectDate.time)
                Toast.makeText(this, "Date: "+date, Toast.LENGTH_SHORT  ).show()
                txtEditDateIn.setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }
        textView9.setOnClickListener {
            val getDate : Calendar = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, android.R.style.Theme_Material_Dialog_NoActionBar_MinWidth, DatePickerDialog.OnDateSetListener{
                    datePicker, i, i2, i3 ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, i)
                selectDate.set(Calendar.MONTH, i2)
                selectDate.set(Calendar.DAY_OF_MONTH,i3)
                val date : String =  formatDate.format(selectDate.time)
                Toast.makeText(this, "Date: "+date, Toast.LENGTH_SHORT  ).show()
                txtEditDateOut.setText(date)
            }, getDate.get(Calendar.YEAR), getDate.get(Calendar.MONTH), getDate.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        btnDelete.setOnClickListener{
            var builder = AlertDialog.Builder(this@EditTask)
            builder.apply {
                setMessage("Do you want to delete this task?")
                setTitle("Delete")
                setPositiveButton("OK"){_, _ ->
                    toast("Task has been deleted!")
                    delete()

                }
                setNegativeButton("Cancel"){_, _ ->
                    toast("Cancel")
                }
            }
            val dialog = builder.create()
            dialog.show()

        }

        btnSave.setOnClickListener {
            update()
        }


    }

    private fun toast(msg: String){
        Toast.makeText(applicationContext, msg , Toast.LENGTH_SHORT).show()
    }

    fun delete(){
        val intent = Intent()
        intent.putExtra("position", position)
        setResult(Activity.RESULT_CANCELED, intent)
        finish()
    }

    fun update(){
        val radioGroup: RadioGroup = findViewById(R.id.groupButton)
        val intent = Intent()
        intent.putExtra("title", "${txtEditTitle.text}")
        intent.putExtra("content", "${txtEditContent.text}")
        intent.putExtra("position", position)
        intent.putExtra("dateIn", "${txtEditDateIn.text}")
        intent.putExtra("dateOut", "${txtEditDateOut.text}")
        when(radioGroup.checkedRadioButtonId){
            R.id.option1 -> intent.putExtra("state", 1)
            R.id.option2 -> intent.putExtra("state", 2)
            R.id.option3 -> intent.putExtra("state", 3)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    fun saveData(){
        var pref = getSharedPreferences("allData${position}", MODE_PRIVATE)

        var gson = Gson();
        var textData = gson.toJson(allData)

        Log.d("IT333", "save data ${textData}")
        pref.edit().putString("taskData${position}", textData).commit()

    }
    fun loadData(){
        var pref = getSharedPreferences("allData${position}", MODE_PRIVATE)
        var stringData = pref.getString("taskData${position}", null);
        Log.d("IT333", "stringData = ${stringData}")

        if(stringData == null){
            Log.d("IT333", "Chua co lich su luu")

        }else{
            var gson = Gson()
            allData = gson.fromJson<AllData>(stringData, AllData::class.java)
        }

    }


}


