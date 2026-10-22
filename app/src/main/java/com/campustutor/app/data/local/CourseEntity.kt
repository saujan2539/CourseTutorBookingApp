package com.campustutor.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single course
 * saved to the SQLite courses table.
 */
@Entity(tableName = "courses")
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val code: String,
    val description: String
)