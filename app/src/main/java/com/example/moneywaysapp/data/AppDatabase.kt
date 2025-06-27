package com.example.moneywaysapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room

// AppDatabase.kt to control operation
@Database(
    entities = [User::class, Category::class,  Expense::class],
    version = 3,
    exportSchema = false
)

//Make connection to database
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    //make sure app uses only on db instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        //get room database
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budget_tracker_db"
                )
                    .fallbackToDestructiveMigration() // Add this line
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
