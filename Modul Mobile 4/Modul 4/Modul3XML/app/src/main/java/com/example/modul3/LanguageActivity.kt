package com.example.modul3

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.modul3.databinding.ActivityLanguageBinding
import java.util.Locale

class LanguageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLanguageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLanguageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIndo.setOnClickListener {
            setAppLocale("in")
        }

        binding.btnEnglish.setOnClickListener {
            setAppLocale("en")
        }

        binding.btnBackDetail.setOnClickListener {
            finish()
        }
    }

    private fun setAppLocale(localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)

        val intent = Intent(this, MainActivityXml::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }
}