package com.example.dynamiclist

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val listData: List<ItemModel>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardView: CardView = view.findViewById(R.id.card_view)
        val ivAvatar: ImageView = view.findViewById(R.id.iv_avatar)
        val tvAvatarText: TextView = view.findViewById(R.id.tv_avatar_text)
        val tvJudul: TextView = view.findViewById(R.id.tv_judul)
        val tvDeskripsi: TextView = view.findViewById(R.id.tv_deskripsi)
        val switchItem: Switch = view.findViewById(R.id.switch_item)
        val btnAksi: Button = view.findViewById(R.id.btn_aksi)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        val context = holder.itemView.context

        holder.tvJudul.text = item.judul
        holder.tvDeskripsi.text = item.deskripsi
        holder.tvAvatarText.text = "Gambar\n${item.id}"

        if (position % 2 == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#E8F5E9"))
        } else {
            holder.cardView.setCardBackgroundColor(Color.WHITE)
        }

        holder.switchItem.setOnCheckedChangeListener(null)
        holder.switchItem.isChecked = item.isSwitchOn

        holder.switchItem.setOnCheckedChangeListener { _, isChecked ->
            item.isSwitchOn = isChecked
            if (isChecked) {
                Toast.makeText(context, "Switch hidup pada item ${item.id}", Toast.LENGTH_SHORT).show()
            }
        }

        holder.btnAksi.setOnClickListener {
            Toast.makeText(context, "Tombol telah ditekan untuk tombol ${item.id}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int = listData.size
}