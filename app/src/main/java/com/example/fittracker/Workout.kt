package com.example.fittracker

data class Workout (var id: Long?, var name: String?, var date: String?) {

    companion object {
        const val TABLE_WORKOUT: String = "TABLE_WORKOUT"
        const val ID: String = "ID_"
        const val NAME: String = "NAME"
        const val DATE: String = "DATE"
    }
}