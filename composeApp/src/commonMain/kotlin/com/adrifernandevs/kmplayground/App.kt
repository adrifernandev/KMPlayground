package com.adrifernandevs.kmplayground

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.adrifernandevs.kmplayground.ui.navigation.Navigation
import com.adrifernandevs.kmplayground.ui.screens.detail.DetailScreen
import com.adrifernandevs.kmplayground.ui.screens.home.HomeScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalCoilApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .crossfade(true)
                .logger(DebugLogger())
                .build()
        }
        Navigation()
    }
}