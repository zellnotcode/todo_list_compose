package com.example.todolistubersnap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class TaskDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var INSTANCE: TaskDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): TaskDatabase {
            if (INSTANCE == null) {
                synchronized(TaskDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        TaskDatabase::class.java,
                        "note_database"
                    ).build()
                }
            }
            return INSTANCE as TaskDatabase
        }
    }
}