package com.example.moneywaysapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// AppDatabase.kt to control operation
@Database(
    entities = [User::class, Category::class,  Expense::class],
    version = 4,
    exportSchema = false
)

//Make connection to database
abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao

    //make sure app uses only on db instance
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        //migration to safely move data from each version safely without losing existing data

        // Migration from version 1 to 2
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create categories table if it didn't exist
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS categories (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        userId INTEGER NOT NULL,
                        name TEXT NOT NULL,
                        color TEXT NOT NULL DEFAULT '#FF0000'
                    )
                """)
            }
        }

        // Migration from version 2 to 3
        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create expenses table
                database.execSQL("""
                    CREATE TABLE IF NOT EXISTS expenses (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        userId INTEGER NOT NULL,
                        categoryId INTEGER NOT NULL,
                        date TEXT NOT NULL,
                        startTime TEXT,
                        endTime TEXT,
                        description TEXT NOT NULL,
                        amount REAL NOT NULL,
                        photoPath TEXT
                    )
                """)
            }
        }


        //Migration from version 3 to 4(Add picture)
        val MIGRATION_3_4 =  object : Migration(3,4){
            override fun migrate(database: SupportSQLiteDatabase){
                database.execSQL("ALTER TABLE users ADD COLUMN profilePicUrl TEXT")
            }
        }

        //get room database
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budget_tracker_db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4) //tells Room how to upgrade the DB
                    .build()
                INSTANCE = instance
                instance
            }
        }


    }
}
