package com.adrifernandevs.kmplayground.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<MoviesDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(MOVIES_DATABASE)
    return Room.databaseBuilder(appContext, dbFile.absolutePath)
}