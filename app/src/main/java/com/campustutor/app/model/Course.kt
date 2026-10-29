package com.campustutor.app.model

data class Course(
    val id: Int,
    val name: String,
    val code: String,
    val icon: String,
    val description: String
)

data class Tutor(
    val id: Int,
    val courseId: Int,
    val name: String,
    val subject: String,
    val major: String,
    val maxBookingMinutes: Int,
    val description: String
)