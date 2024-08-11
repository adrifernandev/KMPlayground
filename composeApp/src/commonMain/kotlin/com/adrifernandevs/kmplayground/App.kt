package com.adrifernandevs.kmplayground

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
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

        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(movies, key = { it.id }) { movie ->
                    MovieItem(movie = movie)
                }
            }
        }
    }
}

@Composable
private fun MovieItem(movie: Movie) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2 / 3f)
                .clip(MaterialTheme.shapes.medium),
            model = movie.poster,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}