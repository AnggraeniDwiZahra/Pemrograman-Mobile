package com.example.modul3compose

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modul3compose.ui.theme.Modul3ComposeTheme
import timber.log.Timber

@Composable
fun HotelApp(viewModel: HotelViewModel) {
    val navController = rememberNavController()

    val hotels by viewModel.listHotel.collectAsState()

    NavHost(navController = navController, startDestination = "hotel_list") {
        composable("hotel_list") {
            HotelList(
                hotels = hotels,
                navController = navController,
                onDetailClick = { hotel ->
                    viewModel.onHotelClicked(hotel)
                    navController.navigate("hotel_detail/${hotel.name}")
                }
            )
        }

        composable("hotel_detail/{hotelName}") { backStackEntry ->
            val hotelName = backStackEntry.arguments?.getString("hotelName")
            val hotel = viewModel.getHotelByName(hotelName)
            if (hotel != null) {
                HotelDetailScreen(
                    hotel = hotel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    viewModel = viewModel
                )
            }
        }

        composable("language_screen") {
            LanguageScreen(onBackClick = {
                navController.popBackStack()
            })
        }
    }
}

@Composable
fun HotelList(
    hotels: List<Hotel>,
    navController: NavController,
    onDetailClick: (Hotel) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        item {
            HomeHeader(navController = navController)

            Text(
                text = stringResource(id = R.string.featured_hotels),
                modifier = Modifier.padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.ExtraBold
            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(hotels) { hotel ->
                    HotelHighlightItem(
                        hotel = hotel,
                        onDetailClick = { onDetailClick(hotel) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(id = R.string.all_hotels),
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }

        items(hotels) { hotel ->
            HotelItemRow(
                hotel = hotel,
                onDetailClick = { onDetailClick(hotel) }
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
fun HotelItemRow(hotel: Hotel, onDetailClick: () -> Unit) {
    val context = LocalContext.current
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(id = hotel.imageURL),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = hotel.name, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f), overflow = TextOverflow.Ellipsis)
                    Text(text = hotel.stars, modifier = Modifier.padding(start = 4.dp), fontSize = 12.sp)
                }

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = hotel.location, fontSize = 11.sp, color = Color.Gray)
                    Text(text = hotel.price, fontWeight = FontWeight.SemiBold, color = Color.Blue, fontSize = 13.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            Timber.tag("ACTION").d("Tombol Explicit Intent (Maps) ditekan untuk: ${hotel.name}")
                            val uri = Uri.parse("geo:0,0?q=${hotel.name}+${hotel.location}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(mapIntent)
                        }
                    ) {
                        Text("Maps")
                    }

                    Button(
                        onClick = {
                            Timber.tag("ACTION").d("Tombol Detail ditekan untuk: ${hotel.name}")
                            onDetailClick()
                        }
                    ) {
                        Text("Detail")
                    }
                }
            }
        }
    }
}

@Composable
fun HotelHighlightItem(hotel: Hotel, onDetailClick: () -> Unit) {
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
            Image(
                painter = painterResource(id = hotel.imageURL),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )
            Text(
                text = hotel.name,
                modifier = Modifier.padding(12.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun HotelDetailScreen(hotel: Hotel, onBackClick: () -> Unit, viewModel: HotelViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box {
            Image(
                painter = painterResource(id = hotel.imageURL),
                contentDescription = null,
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
                Text(text = hotel.name, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = hotel.stars, style = MaterialTheme.typography.headlineSmall, textAlign = TextAlign.End)
            }

            Text(text = hotel.location, color = Color.Gray, fontSize = 16.sp)

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(id = R.string.price_per_night), fontWeight = FontWeight.SemiBold)
            Text(text = hotel.price, color = Color.Blue, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold)

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.facilities_desc),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.hotel_description, hotel.name),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.DarkGray
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE8F5E9))
            ) {
                Text(
                    text = stringResource(id = R.string.promo_code, viewModel.promoCode),
                    modifier = Modifier.padding(16.dp),
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HotelPreview() {
    Modul3ComposeTheme() {
        Column {
            val sampleHotel = Hotel(
                "Kollektiv Hotel",
                "⭐⭐⭐",
                "Sukajadi, Bandung",
                "Rp423.649",
                R.drawable.kollektiv
            )
            HotelHighlightItem(hotel = sampleHotel, onDetailClick = {})
            Spacer(modifier = Modifier.height(20.dp))
            HotelItemRow(hotel = sampleHotel, onDetailClick = {})
        }
    }
}