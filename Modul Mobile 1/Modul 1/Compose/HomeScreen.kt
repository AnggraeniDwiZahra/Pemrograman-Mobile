package com.example.diceroller

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.diceroller.ui.theme.DiceRollerTheme
import androidx.compose.foundation.layout.Row

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var result1: Int by remember { mutableStateOf(0) }
    var result2: Int by remember { mutableStateOf(0) }

    fun getDiceResource(result: Int): Int {
        return when(result) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }

    val onClickRoll: () -> Unit = {
        result1 = (1..6).random()
        result2 = (1..6).random()

        val message = if (result1 == result2) {"Selamat, anda dapat dadu double!"}
        else {"Anda belum beruntung!"}

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = getDiceResource(result1)),
                    contentDescription = "Dadu 1",
                    modifier = Modifier.size(150.dp)
                )
                Spacer(modifier = Modifier.size(16.dp))
                Image(
                    painter = painterResource(id = getDiceResource(result2)),
                    contentDescription = "Dadu 2",
                    modifier = Modifier.size(150.dp)
                )
            }
            Spacer(modifier = Modifier.size(30.dp))
            Button(onClick = onClickRoll) {
                Text(text = "Roll")

            }
        }
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    DiceRollerTheme {
        HomeScreen()
    }
}