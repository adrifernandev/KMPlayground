package com.adrifernandevs.kmplayground.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.adrifernandevs.kmplayground.domain.model.Movie
import com.adrifernandevs.kmplayground.ui.designsystem.components.LoadingIndicator
import com.adrifernandevs.kmplayground.ui.permissions.PermissionRequestEffect
import com.adrifernandevs.kmplayground.ui.screens.Screen
import com.adrifernandevs.kmplayground.ui.screens.home.viewmodel.HomeViewModel
import dev.icerock.moko.permissions.Permission
import kmplayground.composeapp.generated.resources.Res
import kmplayground.composeapp.generated.resources.app_name
import kmplayground.composeapp.generated.resources.favorite
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3Api::class, KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    onMovieClicked: (Movie) -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {
    PermissionRequestEffect(Permission.COARSE_LOCATION) {
        viewModel.onEvent(HomeViewModel.UiEvent.OnUiReady)
    }

    Screen {
        val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(stringResource(Res.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
            },
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
        ) { paddingValues ->
            val state by viewModel.state.collectAsState()
            if (state.isLoading) {
                LoadingIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            LazyVerticalGrid(
                modifier = Modifier.padding(paddingValues),
                columns = GridCells.Adaptive(128.dp),
                contentPadding = PaddingValues(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.movies, key = { it.id }) { movie ->
                    MovieItem(
                        movie = movie,
                        onMovieClicked = { onMovieClicked(movie) }
                    )
                }
            }
        }
    }
}

@Composable
private fun MovieItem(
    movie: Movie,
    onMovieClicked: (Movie) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onMovieClicked(movie) },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
                    .clip(MaterialTheme.shapes.medium),
                model = movie.poster,
                contentDescription = movie.title,
                contentScale = ContentScale.Crop
            )

            if (movie.isFavorite) {
                Icon(
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopEnd),
                    imageVector = Icons.Default.Favorite,
                    contentDescription = stringResource(Res.string.favorite),
                    tint = Color.White
                )
            }
        }

        Text(
            modifier = Modifier.fillMaxWidth(),
            text = movie.title,
            style = MaterialTheme.typography.bodySmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}