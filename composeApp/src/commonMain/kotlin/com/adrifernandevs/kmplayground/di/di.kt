package com.adrifernandevs.kmplayground.di

import androidx.room.RoomDatabase
import com.adrifernandevs.kmplayground.BuildConfig
import com.adrifernandevs.kmplayground.data.database.MoviesDao
import com.adrifernandevs.kmplayground.data.database.MoviesDatabase
import com.adrifernandevs.kmplayground.data.repository.MoviesRepository
import com.adrifernandevs.kmplayground.data.service.MoviesService
import com.adrifernandevs.kmplayground.ui.screens.detail.viewmodel.DetailViewModel
import com.adrifernandevs.kmplayground.ui.screens.home.viewmodel.HomeViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module

val appModule = module {
    single(named("tmdbApiKey")) { BuildConfig.TMDB_API_KEY }
    single<MoviesDao> {
        val dbBuilder = get<RoomDatabase.Builder<MoviesDatabase>>()
        dbBuilder.build().moviesDao()
    }
}

val dataModule = module {
    factoryOf(::MoviesRepository)
    factoryOf(::MoviesService)
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                })
            }
            install(DefaultRequest) {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.themoviedb.org"
                    parameters.append("api_key", BuildConfig.TMDB_API_KEY)
                }
            }
        }
    }
}

val viewModelsModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::DetailViewModel)
}

expect val nativeModule: Module

fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule, dataModule, viewModelsModule, nativeModule)
    }
}