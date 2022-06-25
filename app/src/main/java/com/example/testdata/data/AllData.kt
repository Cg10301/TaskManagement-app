package com.example.testdata.data

import com.example.testdata.model.Task
import com.google.gson.Gson

data class AllData(
    var maxTaskId: Int,
    var listTask: MutableList<Task>
) {
    public fun addTask(task: Task) {
        maxTaskId += 1
        task.id = maxTaskId
        listTask.add(task)
    }

    public fun removeTask(id: Int){
        maxTaskId -=1
        listTask.removeAt(id)
    }

    public fun updateTask(task: Task, id: Int){
        listTask[id] = task
    }

    public fun toJsonString() : String {
        var gson = Gson()
        return  gson.toJson(this)
    }

}
