package com.adrifernandevs.kmplayground.di

import android.location.Geocoder
import com.adrifernandevs.kmplayground.data.database.getDatabaseBuilder
import com.adrifernandevs.kmplayground.data.datasource.AndroidRegionDataSource
import com.adrifernandevs.kmplayground.data.repository.RegionDataSource
import com.google.android.gms.location.LocationServices
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual val nativeModule = module {
    single { getDatabaseBuilder(get()) }
    factory { Geocoder(get()) }
    factory { LocationServices.getFusedLocationProviderClient(androidContext()) }
    factoryOf(::AndroidRegionDataSource) bind RegionDataSource::class
}