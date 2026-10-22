package com.campustutor.app.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single booking request
 * saved to the SQLite bookings table.
 */
@Entity(tableName = "bookings")
data class BookingEntity(
    /* primary key, DB auto-increment */
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    /* tutor number */
    val tutorId: Int,
    /* student name */
    val studentName: String,
    /* topic */
    val topic: String,
    /* booked duration in days */
    val bookingDays: Int,
    /* booking status */
    val status: String
)