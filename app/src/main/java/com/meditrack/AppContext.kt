package com.meditrack

import android.app.Application
import androidx.room.Room
import com.google.firebase.FirebaseApp
import com.meditrack.data.AppDatabase

class AppContext : Application(){
    companion object {
        lateinit var database : AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        // Initialize Firebase (safe to call even if google-services.json is not yet present)
        FirebaseApp.initializeApp(this)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "my_database"
        ).build()
    }
}