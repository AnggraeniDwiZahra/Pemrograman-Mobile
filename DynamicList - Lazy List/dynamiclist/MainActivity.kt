package com.example.dynamiclist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val listItem = listOf(
            ItemModel(1, "Item Pertama", "Deskripsi item kesatu"),
            ItemModel(2, "Item Kedua", "Deskripsi item kedua"),
            ItemModel(3, "Item Ketiga", "Deskripsi item ketiga"),
            ItemModel(4, "Item Keempat", "Deskripsi item keempat"),
            ItemModel(5, "Item Kelima", "Deskripsi item kelima")
        )

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LazyListTaskScreen(listData = listItem)
                }
            }
        }
    }
}

@Composable
fun LazyListTaskScreen(listData: List<ItemModel>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(listData) { index, item ->
            ItemRow(index = index, item = item)
        }
    }
}

@Composable
fun ItemRow(index: Int, item: ItemModel) {
    val context = LocalContext.current
    val backgroundColor = if (index % 2 == 1) Color(0xFFE8F5E9) else Color.White

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(60.dp)
                    .background(Color(0xFFE0E0E0)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Gambar\n${item.id}",
                    color = Color.Black,
                    fontSize = 11.sp,
                    lineHeight = 14.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.judul,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = item.deskripsi,
                    fontSize = 13.sp,
                    color = Color(0xFF757575)
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                Switch(
                    checked = item.isSwitchOn,
                    onCheckedChange = { isChecked ->
                        item.isSwitchOn = isChecked
                        if (isChecked) {
                            Toast.makeText(context, "Switch hidup pada item ${item.id}", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                Button(
                    onClick = {
                        Toast.makeText(context, "Tombol telah ditekan untuk tombol ${item.id}", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.height(36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7)),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 0.dp)
                ) {
                    Text(text = "Aksi", fontSize = 11.sp, color = Color.White)
                }
            }
        }
    }
}