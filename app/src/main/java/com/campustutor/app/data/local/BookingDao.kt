package com.campustutor.app.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlin.collections.List

/**
 * Data access for saved bookings.
 */
@Dao
interface BookingDao {
    /** Save a booking request **/
    @Insert
    suspend fun insert(booking: BookingEntity): Long

    /** Get all bookings **/
    @Query("SELECT * FROM bookings ORDER BY id DESC")
    suspend fun getAll(): List<BookingEntity>
}