package com.example.testdata.model

import java.time.LocalDate

data class Task(var id: Int, var title: String, var content: String, var state: Int, var dateIn: String, var dateOut: String) {
    companion object STATE {
        val OPEN: Int = 1
        val DOING: Int = 2
        val DONE: Int = 3
    }
}
