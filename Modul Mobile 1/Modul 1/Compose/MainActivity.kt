package com.example.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.diceroller.ui.theme.DiceRollerTheme

// Aktifkan setContentView di bawah ini untuk menjalankan versi XML
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val rollButton: Button = findViewById(R.id.roll_button)
        val diceImage1: ImageView = findViewById(R.id.dice_image_1)
        val diceImage2: ImageView = findViewById(R.id.dice_image_2)

        rollButton.setOnClickListener {
            rollDice(diceImage1, diceImage2)
        }
    }

    private fun rollDice(img1: ImageView, img2: ImageView) {
        val result1 = (1..6).random()
        val result2 = (1..6).random()

        img1.setImageResource(getDiceResource(result1))
        img2.setImageResource(getDiceResource(result2))

        val message = if (result1 == result2) {"Selamat, anda dapat dadu double!"}
        else {"Anda belum beruntung!"}

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()    }

    private fun getDiceResource(number: Int): Int {
        return when (number) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            6 -> R.drawable.dice_6
            else -> R.drawable.dice_0
        }
    }
}

// Hapus komentar setContent di bawah ini untuk menjalankan versi Jetpack Compose
/*
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DiceRollerTheme {
            HomeScreen() // Panggil fungsi dari file HomeScreen.kt
            }
        }
    }
} */