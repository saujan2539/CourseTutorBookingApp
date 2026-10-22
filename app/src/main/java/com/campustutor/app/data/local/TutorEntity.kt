package com.campustutor.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A tutor linked to a course,
 * saved to the SQLite tutors table.
 */
@Entity(tableName = "tutors")
data class TutorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val courseId: Long,
    val name: String,
    val subject: String,
    val major: String
)