package com.adrifernandevs.kmplayground.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adrifernandevs.kmplayground.Movie
import com.adrifernandevs.kmplayground.movies
import com.adrifernandevs.kmplayground.ui.screens.Screen
import kmplayground.composeapp.generated.resources.Res
import kmplayground.composeapp.generated.resources.go_back
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    movie: Movie,
    onNavigateBack: () -> Unit
) {
    Screen {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(movie.title) },
                    navigationIcon = {
                        IconButton(onClick = onNavigateBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                contentDescription = stringResource(Res.string.go_back)
                            )
                        }
                    }
                )
            }
        ){ paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    model = movie.poster,
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop
                )
                Text(
                    text = movie.title,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}