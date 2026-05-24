package com.example.dynamiclist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : androidx.activity.ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listItem = listOf(
            ItemModel(1, "Item Pertama", "Deskripsi item kesatu"),
            ItemModel(2, "Item Kedua", "Deskripsi item kedua"),
            ItemModel(3, "Item Ketiga", "Deskripsi item ketiga"),
            ItemModel(4, "Item Keempat", "Deskripsi item keempat"),
            ItemModel(5, "Item Kelima", "Deskripsi item kelima")
        )

        val recyclerView = findViewById<RecyclerView>(R.id.rv_contoh)

        recyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = MyAdapter(listItem)
        recyclerView.adapter = adapter
    }
}