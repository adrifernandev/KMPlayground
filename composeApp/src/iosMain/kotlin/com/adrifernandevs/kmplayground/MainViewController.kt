package com.adrifernandevs.kmplayground

import androidx.compose.ui.window.ComposeUIViewController
import com.adrifernandevs.kmplayground.data.database.getDatabaseBuilder

fun MainViewController() = ComposeUIViewController {
    val database = getDatabaseBuilder().build().moviesDao()
    App(database)
}