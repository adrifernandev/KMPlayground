package com.adrifernandevs.kmplayground.ui.screens.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.adrifernandevs.kmplayground.domain.model.Movie
import com.adrifernandevs.kmplayground.ui.designsystem.components.LoadingIndicator
import com.adrifernandevs.kmplayground.ui.screens.Screen
import com.adrifernandevs.kmplayground.ui.screens.detail.viewmodel.DetailViewModel
import kmplayground.composeapp.generated.resources.Res
import kmplayground.composeapp.generated.resources.go_back
import kmplayground.composeapp.generated.resources.mark_as_favorite
import kmplayground.composeapp.generated.resources.original_language
import kmplayground.composeapp.generated.resources.original_title
import kmplayground.composeapp.generated.resources.popularity
import kmplayground.composeapp.generated.resources.release_date
import kmplayground.composeapp.generated.resources.vote_average
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    viewModel: DetailViewModel,
    onNavigateBack: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Screen {
        Scaffold(
            modifier = Modifier
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                DetailTopBar(
                    title = state.movie?.title ?: "",
                    onNavigateBack = onNavigateBack,
                    scrollBehavior = scrollBehavior
                )
            },
            floatingActionButton = {
                state.movie?.let {
                    FloatingActionButton(
                        onClick = {}
                    ) {
                        IconButton(
                            onClick = { viewModel.onEvent(DetailViewModel.UiEvent.OnFavouriteClicked) }
                        ) {
                            Icon(
                                imageVector = if (it.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = stringResource(Res.string.mark_as_favorite)
                            )
                        }
                    }
                }
            }
        ) { paddingValues ->
            if (state.isLoading) {
                LoadingIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }
            state.movie?.let {
                MovieDetailContent(
                    modifier = Modifier
                        .padding(paddingValues)
                        .verticalScroll(rememberScrollState()),
                    movie = it
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailTopBar(
    title: String,
    onNavigateBack: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = stringResource(Res.string.go_back)
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
private fun MovieDetailContent(
    modifier: Modifier = Modifier,
    movie: Movie
) {
    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f),
            model = movie.backdrop ?: movie.poster,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop
        )
        Text(
            text = movie.overview,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = buildAnnotatedString {
                movieProperty(stringResource(Res.string.release_date), movie.releaseDate)
                movieProperty(stringResource(Res.string.original_title), movie.originalTitle)
                movieProperty(stringResource(Res.string.original_language), movie.originalLanguage)
                movieProperty(stringResource(Res.string.popularity), movie.popularity.toString())
                movieProperty(
                    stringResource(Res.string.vote_average),
                    movie.voteAverage.toString(),
                    isLastLine = true
                )
            }
        )
    }
}

private fun AnnotatedString.Builder.movieProperty(
    name: String,
    value: String,
    isLastLine: Boolean = false
) {
    withStyle(ParagraphStyle(lineHeight = 18.sp)) {
        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
            append("$name: ")
        }
        append(value)
        if (!isLastLine) {
            append("\n")
        }
    }
}