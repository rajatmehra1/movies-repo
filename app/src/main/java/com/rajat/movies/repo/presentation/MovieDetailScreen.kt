package com.rajat.movies.repo.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.NavHostController
import com.rajat.movies.repo.data.MovieItem

@Composable
fun MovieDetailScreen(
    navHostController: NavHostController, movie: MovieItem
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navHostController.popBackStack()
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(movie.getImageUrl())
                .placeholder(android.graphics.Color.GRAY.toDrawable())
                .error(android.graphics.Color.LTGRAY.toDrawable()).crossfade(true).build(),
            contentDescription = movie.title + " Poster",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(8.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title ?: "", style = TextStyle(
                fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = movie.overview ?: "", style = TextStyle(
                fontSize = 16.sp, color = Color.Black
            )
        )
    }
}