package com.rajat.movies.repo.presentation

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toDrawable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.google.gson.Gson
import com.rajat.movies.repo.MoviesApplication
import com.rajat.movies.repo.data.MovieItem

@Composable
fun MovieListScreen(navController: NavHostController) {
    val viewModel: MoviesViewModel = viewModel(
        factory = MoviesViewModelFactory((LocalContext.current.applicationContext as MoviesApplication).appContainer)
    )
    val uiState by viewModel.uiState.collectAsState()
    when (uiState) {
        is UiState.Loading -> CircularProgressIndicator(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize()
        )

        is UiState.Error -> EmptyDataState(viewModel)

        is UiState.RenderMovieList -> MovieListComposable(
            navController,
            viewModel,
            movieList = (uiState as UiState.RenderMovieList).movieList
        )

        is UiState.EmptyList -> EmptyDataState(viewModel)
    }
}

@Composable
fun EmptyDataState(viewModel: MoviesViewModel) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Looks like you are offline or Something Went Wrong!!!")
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { viewModel.retry() }) {
            Text("Retry")
        }

    }
}

@Composable
fun MovieListComposable(
    navController: NavHostController,
    viewModel: MoviesViewModel,
    movieList: List<MovieItem>
) {
    val query by viewModel.searchQuery
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .systemBarsPadding()
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            placeholder = {
                Text("Search movies", color = Color.Gray)
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Gray
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (movieList.isEmpty()) {
            Text(
                text = "No Search Results Found!!!",
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize()
            )
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(movieList.size) { index ->
                    val movie = movieList[index]
                    MovieItemComposable(movie) {
                        val json = Uri.encode(Gson().toJson(movie))
                        navController.navigate("movieDetailsScreen/$json")
                    }
                }
            }
        }
    }
}

@Composable
fun MovieItemComposable(movie: MovieItem, onClick : () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {
            onClick()
        }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(movie.getImageUrl())
                .placeholder(android.graphics.Color.GRAY.toDrawable())
                .error(android.graphics.Color.LTGRAY.toDrawable())
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp)),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = movie.title ?: "",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontWeight = FontWeight.Medium,
                color = Color.Black
            ),
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth()
        )
    }
}