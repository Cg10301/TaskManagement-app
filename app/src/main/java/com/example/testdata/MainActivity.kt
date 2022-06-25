package com.example.testdata

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.testdata.adapter.TaskAdapter
import com.example.testdata.data.AllData
import com.example.testdata.model.Task
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDate
import java.time.Month
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity(), TaskAdapter.MyOnClickListener {
    lateinit var allData: AllData;
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("IT333", "onCreated")

        loadData()

        var adapter = TaskAdapter(allData.listTask, this@MainActivity)
        var recyclerView = findViewById<RecyclerView>(R.id.listTaskRecylerView)
        recyclerView.adapter = adapter


        buttonAddTask.setOnClickListener{

            val intent = Intent(this, NewTask_Activity::class.java)
            this.startActivityForResult(intent, 103)

        }


    }

    private fun toast(msg: String){
        Toast.makeText(applicationContext, msg , Toast.LENGTH_SHORT).show()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 103 && resultCode == Activity.RESULT_OK ){
            val title = data?.getStringExtra("title").toString()
            val content = data?.getStringExtra("content").toString()
            val dateIn = data?.getStringExtra("dateIn").toString()
            val dateOut = data?.getStringExtra("dateOut").toString()
            addTask(title, content, dateIn, dateOut)
        }
        else if(requestCode == 101){
            if(resultCode == Activity.RESULT_CANCELED){
                val position = data?.getIntExtra("position", 0)
                if (position != null) {
                    removeTask(position)
                }
            }
            else if(resultCode == Activity.RESULT_OK){
                val position = data?.getIntExtra("position", 0)
                val title = data?.getStringExtra("title").toString()
                val content = data?.getStringExtra("content").toString()
                val state = data?.getIntExtra("state", 1)
                val dateIn = data?.getStringExtra("dateIn").toString()
                val dateOut = data?.getStringExtra("dateOut").toString()
                if (position != null && state != null) {
                    updateTask(title, content, position, state, dateIn, dateOut)
                }
            }
        }
    }

    fun createDefaultData() : AllData {
        var allData = AllData(0, mutableListOf<Task>())

        return allData;
    }

    fun saveData(){
        var pref = getSharedPreferences("allData", MODE_PRIVATE)

        var gson = Gson();
        var textData = gson.toJson(allData)

        Log.d("IT333", "save data ${textData}")
        pref.edit().putString("taskData", textData).commit()

    }

    fun loadData(){
        var pref = getSharedPreferences("allData", MODE_PRIVATE)
        var stringData = pref.getString("taskData", null);
        Log.d("IT333", "stringData = ${stringData}")

        if(stringData == null){
            allData = createDefaultData()
        }else{
            var gson = Gson()
            allData = gson.fromJson<AllData>(stringData, AllData::class.java)
        }

    }

    fun addTask(title: String, content: String, dateIn: String, dateOut: String){
        var task = Task(0, title, content, Task.OPEN, dateIn, dateOut)
        allData.addTask(task)

        var adapter = TaskAdapter(allData.listTask, this@MainActivity)
        var recyclerView = findViewById<RecyclerView>(R.id.listTaskRecylerView)
        recyclerView.adapter = adapter

        saveData()
    }

    fun removeTask(position: Int){

        allData.removeTask(position)

        var adapter = TaskAdapter(allData.listTask, this@MainActivity)
        var recyclerView = findViewById<RecyclerView>(R.id.listTaskRecylerView)
        recyclerView.adapter = adapter

        var pref = getSharedPreferences("allData", MODE_PRIVATE)

        var gson = Gson();
        var textData = gson.toJson(allData)
        pref.edit().remove("textData").commit()
        saveData()
    }

    fun updateTask(title: String, content: String, position: Int, state: Int, dateIn: String, dateOut: String){
        var task = Task(position+1, title, content, state,dateIn, dateOut)
        allData.updateTask(task, position)

        var adapter = TaskAdapter(allData.listTask, this@MainActivity)
        var recyclerView = findViewById<RecyclerView>(R.id.listTaskRecylerView)
        recyclerView.adapter = adapter

        saveData()
    }

    override fun OnClick(position: Int) {

        val intent = Intent(this, EditTask::class.java)
        intent.putExtra("title", "${allData.listTask[position].title}")
        intent.putExtra("content", "${allData.listTask[position].content}")
        intent.putExtra("position", position)
        intent.putExtra("state", allData.listTask[position].state)
        intent.putExtra("dateIn", "${allData.listTask[position].dateIn}")
        intent.putExtra("dateOut", "${allData.listTask[position].dateOut}")

        this.startActivityForResult(intent, 101)

    }

}