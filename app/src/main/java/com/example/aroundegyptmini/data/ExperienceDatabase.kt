package com.example.aroundegyptmini.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aroundegyptmini.model.ExperienceEntity
import com.example.aroundegyptmini.network.ExperienceDao


@Database(entities = [ExperienceEntity::class], version = 1, exportSchema = false)
abstract class ExperienceDatabase : RoomDatabase() {

    abstract fun experienceDao(): ExperienceDao

    companion object {
        @Volatile
        private var Instance: ExperienceDatabase? = null

        fun getDatabase(context: Context): ExperienceDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, ExperienceDatabase::class.java, "experience_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}