package com.example.tugasapi

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvHasilApi = findViewById<TextView>(R.id.tvHasilApi)

        lifecycleScope.launch {
            try {
                val respon = NetworkClient.apiService.getTugas()

                val teksTampilan = """
                    Status Message: ${respon.message}
                    Response Code: ${respon.code}
                    
                    Isi Data API:
                    "${respon.data.isi_tugas}"
                """.trimIndent()

                tvHasilApi.text = teksTampilan

            } catch (e: Exception) {
                tvHasilApi.text = "Gagal memuat API:\n${e.localizedMessage}"
            }
        }
    }
}