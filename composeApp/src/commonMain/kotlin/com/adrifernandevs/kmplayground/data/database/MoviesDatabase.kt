package com.adrifernandevs.kmplayground.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.adrifernandevs.kmplayground.domain.model.Movie

const val MOVIES_DATABASE = "movies.db"

interface DB {
    fun clearAllTables()
}

@Database(entities = [Movie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase(), DB {
    abstract fun moviesDao(): MoviesDao
    override fun clearAllTables() {}
}