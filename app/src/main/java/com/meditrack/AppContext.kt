package com.meditrack

import android.app.Application
import androidx.room.Room
import com.meditrack.data.AppDatabase

class AppContext : Application(){
    companion object {
        lateinit var database : AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }
}