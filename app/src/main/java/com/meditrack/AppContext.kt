package com.meditrack

import android.app.Application
import androidx.room.Room
import com.meditrack.data.DB

class AppContext : Application(){
    companion object {
        lateinit var database : DB
    }

    override fun onCreate() {
        super.onCreate()
        val db = Room.databaseBuilder(
            applicationContext,
            DB::class.java,
            "my_database"
        ).build()
    }
}