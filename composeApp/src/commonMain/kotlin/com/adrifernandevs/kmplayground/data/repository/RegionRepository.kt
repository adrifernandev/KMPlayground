package com.adrifernandevs.kmplayground.data.repository

const val DEFAULT_APP_REGION = "US"

interface RegionDataSource {
    suspend fun fetchRegion(): String
}

class RegionRepository(
    private val regionDataSource: RegionDataSource
) {
    suspend fun fetchRegion(): String {
        return regionDataSource.fetchRegion()
    }
}