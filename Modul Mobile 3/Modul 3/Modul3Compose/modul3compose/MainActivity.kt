package com.example.modul3compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.core.os.LocaleListCompat
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.modul3compose.ui.theme.Modul3ComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listHotel = listOf(
            Hotel("Kollektiv Hotel", "⭐⭐⭐", "Sukajadi, Bandung", "Rp423.649", R.drawable.kollektiv),
            Hotel("eL Hotel Bandung", "⭐⭐⭐⭐", "Merdeka, Bandung", "Rp724.054", R.drawable.elhotel),
            Hotel("Geary Hotel Bandung", "⭐⭐⭐", "Pasirkaliki, Bandung", "Rp387.061", R.drawable.geary),
            Hotel("Pullman Bandung Grand Central", "⭐⭐⭐⭐⭐", "Cibeunying, Bandung", "Rp1.652.504", R.drawable.pullman),
            Hotel("Grandia Hotel Bandung", "⭐⭐⭐⭐", "Cihampelas, Bandung", "Rp532.350", R.drawable.grandia)
        )

        setContent {
            Modul3ComposeTheme {
                HotelApp(hotels = listHotel)
            }
        }
    }
}

fun changeLanguage(languageCode: String) {
    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(languageCode)
    AppCompatDelegate.setApplicationLocales(appLocale)
}

@Composable
fun HotelApp(hotels: List<Hotel>) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "hotel_list") {
        composable("hotel_list") {
            HotelList(
                hotels = hotels,
                navController = navController,
                onDetailClick = { hotel ->
                    navController.navigate("hotel_detail/${hotel.name}")
                }
            )
        }

        composable("hotel_detail/{hotelName}") { backStackEntry ->
            val hotelName = backStackEntry.arguments?.getString("hotelName")
            val hotel = hotels.find { it.name == hotelName }
            if (hotel != null) {
                HotelDetailScreen(
                    hotel = hotel,
                    onBackClick = {
                        navController.popBackStack()
                    }
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

                Row {
                    Button(
                        onClick = {
                            val uri = Uri.parse("geo:0,0?q=${hotel.name}+${hotel.location}")
                            val mapIntent = Intent(Intent.ACTION_VIEW, uri)
                            context.startActivity(mapIntent)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Maps")
                    }

                    Button(onClick = onDetailClick) {
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
fun HotelDetailScreen(hotel: Hotel, onBackClick: () -> Unit) {
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