package com.adrifernandevs.kmplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.adrifernandevs.kmplayground.data.database.getDatabaseBuilder
import com.adrifernandevs.kmplayground.utils.EnableTransparentStatusBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnableTransparentStatusBar()
            val db = getDatabaseBuilder(this).build().moviesDao()
            App(db)
        }
    }
}