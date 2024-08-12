package com.adrifernandevs.kmplayground.di

import com.adrifernandevs.kmplayground.data.database.getDatabaseBuilder
import com.adrifernandevs.kmplayground.data.datasource.IosRegionDataSource
import com.adrifernandevs.kmplayground.data.repository.RegionDataSource
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder() }
    factoryOf(::IosRegionDataSource) bind RegionDataSource::class
}