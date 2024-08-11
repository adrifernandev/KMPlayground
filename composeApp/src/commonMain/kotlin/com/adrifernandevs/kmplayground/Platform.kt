package com.adrifernandevs.kmplayground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform