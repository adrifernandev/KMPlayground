package com.adrifernandevs.kmplayground.di

import com.adrifernandevs.kmplayground.data.database.getDatabaseBuilder
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder(get()) }
}