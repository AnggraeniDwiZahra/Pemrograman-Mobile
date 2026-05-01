package com.example.modul3

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.WindowInsets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val name = intent.getStringExtra("EXTRA_HOTEL_NAME")
        val image = intent.getIntExtra("EXTRA_HOTEL_IMAGE", 0)
        val rating = intent.getStringExtra("EXTRA_HOTEL_RATING")
        val location = intent.getStringExtra("EXTRA_HOTEL_LOCATION")
        val price = intent.getStringExtra("EXTRA_HOTEL_PRICE")
        val desc = intent.getStringExtra("EXTRA_HOTEL_DESC")

        findViewById<TextView>(R.id.tv_name_detail).text = name
        findViewById<ImageView>(R.id.img_detail).setImageResource(image)
        findViewById<TextView>(R.id.tv_rating_detail).text = rating
        findViewById<TextView>(R.id.tv_location_detail).text = location
        findViewById<TextView>(R.id.tv_price_detail).text = price
        findViewById<TextView>(R.id.tv_desc_detail).text = desc

        findViewById<Button>(R.id.btn_back_detail).setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}