package com.example.modul3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.modul3.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("EXTRA_HOTEL_NAME")
        val image = intent.getIntExtra("EXTRA_HOTEL_IMAGE", 0)
        val rating = intent.getStringExtra("EXTRA_HOTEL_RATING")
        val location = intent.getStringExtra("EXTRA_HOTEL_LOCATION")
        val price = intent.getStringExtra("EXTRA_HOTEL_PRICE")
        val desc = intent.getStringExtra("EXTRA_HOTEL_DESC")

        binding.tvNameDetail.text = name
        binding.imgDetail.setImageResource(image)
        binding.tvRatingDetail.text = rating
        binding.tvLocationDetail.text = location
        binding.tvPriceDetail.text = price
        binding.tvDescDetail.text = desc

        val factory = ViewModelFactory("PROMO_PELAJAR")
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]
        viewModel.promo.observe(this) { promoText ->
            binding.tvPromoLabel.text = "${getString(R.string.promo_code)} $promoText"
        }

        binding.btnBackDetail.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}