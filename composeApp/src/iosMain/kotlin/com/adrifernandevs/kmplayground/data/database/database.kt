package com.adrifernandevs.kmplayground.data.database

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): RoomDatabase.Builder<MoviesDatabase> {
    val dbFilePath = NSHomeDirectory() + "/$MOVIES_DATABASE"
    return Room.databaseBuilder<MoviesDatabase>(
        name = dbFilePath,
        factory = { MoviesDatabase::class.instantiateImpl() }
    ).setDriver(BundledSQLiteDriver())
}