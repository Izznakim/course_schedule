package com.dicoding.courseschedule.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

//TODO 3 : Define room database class [SOLVED]
@Database(entities = [Course::class], version = 1)
abstract class CourseDatabase : RoomDatabase() {

    abstract fun courseDao(): CourseDao

    companion object {

        @Volatile
        private var instance: CourseDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): CourseDatabase {
            return synchronized(this) {
                instance ?: Room.databaseBuilder(context, CourseDatabase::class.java, "courses.db")
                    .allowMainThreadQueries()
                    .build()
            }
        }

    }
}
