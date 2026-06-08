package com.example.modul3compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.modul3compose.data.MovieEntity
import com.example.modul3compose.data.Resource
import timber.log.Timber
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun MovieApp(viewModel: MovieViewModel) {
    val navController = rememberNavController()
    val moviesState by viewModel.moviesState.collectAsState()

    NavHost(navController = navController, startDestination = "movie_list") {
        composable("movie_list") {
            when (val state = moviesState) {
                is Resource.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                is Resource.Success -> {
                    MovieList(
                        movies = state.data,
                        navController = navController,
                        viewModel = viewModel,
                        onDetailClick = { movie ->
                            viewModel.onMovieClicked(movie)
                            navController.navigate("movie_detail/${movie.id}")
                        }
                    )
                }
                is Resource.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: ${state.message}", color = Color.Red, textAlign = TextAlign.Center)
                    }
                }
            }
        }

        composable("movie_detail/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId")?.toIntOrNull()
            val movie = viewModel.getMovieById(movieId)
            if (movie != null) {
                MovieDetailScreen(
                    movie = movie,
                    onBackClick = { navController.popBackStack() }
                )
            }
        }

        composable("language_screen") {
            LanguageScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

@Composable
fun MovieList(
    movies: List<MovieEntity>,
    navController: NavController,
    viewModel: MovieViewModel,
    onDetailClick: (MovieEntity) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            HomeHeader(navController = navController)

            Text(
                text = stringResource(R.string.featured_movies),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(movies) { movie ->
                    MovieHighlightItem(
                        movie = movie,
                        onDetailClick = { onDetailClick(movie) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.all_movies),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(movies) { movie ->
            MovieItemRow(
                movie = movie,
                viewModel = viewModel,
                onDetailClick = { onDetailClick(movie) }
            )
        }
    }
}

@Composable
fun HomeHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { navController.navigate("language_screen") }) {
            Icon(
                imageVector = Icons.Default.Language,
                contentDescription = "Change Language"
            )
        }
    }
}

@Composable
fun MovieItemRow(
    movie: MovieEntity,
    viewModel: MovieViewModel,
    onDetailClick: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f),
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "⭐ ${movie.voteAverage}",
                        fontSize = 12.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Release: ${movie.releaseDate}",
                        fontSize = 11.sp,
                        color = Color.Gray
                    )

                    Text(
                        text = stringResource(R.string.popular),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.Blue,
                        fontSize = 13.sp
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        8.dp,
                        Alignment.End
                    )
                ) {

                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val trailerUrl =
                                        viewModel.getTrailerUrl(movie.id)

                                    if (trailerUrl != null) {
                                        val intent = Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(trailerUrl)
                                        )

                                        context.startActivity(intent)
                                    }
                                } catch (e: Exception) {
                                    Timber.e(
                                        e,
                                        "Gagal membuka trailer"
                                    )
                                }
                            }
                        }
                    ) {
                        Text(stringResource(R.string.trailer))
                    }

                    Button(
                        onClick = {
                            onDetailClick()
                        }
                    ) {
                        Text(stringResource(R.string.detail))
                    }
                }
            }
        }
    }
}

@Composable
fun MovieHighlightItem(movie: MovieEntity, onDetailClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .width(300.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(20.dp))
            .clickable { onDetailClick() },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = movie.title,
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun MovieDetailScreen(movie: MovieEntity, onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Button(
                onClick = onBackClick,
                modifier = Modifier.padding(16.dp),
                shape = RoundedCornerShape(50)
            ) {
                Text(text = stringResource(id = R.string.back_button))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = movie.title, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "⭐ ${movie.voteAverage}", style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.End)
            }

            Text(text = "Release Date: ${movie.releaseDate}", color = Color.Gray, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.overview),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = movie.overview,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )
        }
    }
}

/*@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MoviePreview() {
    Modul3ComposeTheme {
        val dummyMovies = listOf(
            MovieEntity(
                id = 1,
                title = "The Dark Knight",
                overview = "When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.",
                posterPath = null, // Menggunakan null karena tipe datanya di Entity adalah String?
                releaseDate = "2008-07-16",
                voteAverage = 8.5
            ),
            MovieEntity(
                id = 2,
                title = "Inception",
                overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
                posterPath = null,
                releaseDate = "2010-07-15",
                voteAverage = 8.3
            ),
            MovieEntity(
                id = 3,
                title = "Interstellar",
                overview = "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
                posterPath = null,
                releaseDate = "2014-11-05",
                voteAverage = 8.4
            )
        )

        MovieList(
            movies = dummyMovies,
            navController = rememberNavController(),
            onDetailClick = {}
        )
    }
}*/