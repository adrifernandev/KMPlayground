package com.adrifernandevs.kmplayground

import androidx.compose.ui.window.ComposeUIViewController
import com.adrifernandevs.kmplayground.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = { initKoin() }
) {
    App()
}