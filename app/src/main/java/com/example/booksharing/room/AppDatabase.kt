package com.example.booksharing.room

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDataEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userDataDao(): UserDataDAO

    companion object {
        private var appDB: AppDatabase? = null

        fun getDB(context: Context): AppDatabase {
            if (appDB == null) {
                appDB = androidx.room.Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
            }
            return appDB!!
        }
    }
}