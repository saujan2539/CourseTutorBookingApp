package com.campustutor.app.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [BookingEntity::class, CourseEntity::class, TutorEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /**
     * Handles BookingDao connection
     */
    abstract fun bookingDao(): BookingDao

    companion object {
        private var instance: AppDatabase? = null

        /** Uses the same DB across the whole app */
        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "course_tutor_database"
                ).fallbackToDestructiveMigration().build()
                instance = _instance
                _instance
            }
        }
    }
}