package com.example.modul3

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HotelAdapter(
    private val listHotel: List<Hotel>,
    private val onItemClick: (Hotel) -> Unit
) : RecyclerView.Adapter<HotelAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_hotel_name)
        val tvLocation: TextView = itemView.findViewById(R.id.tv_hotel_location)
        val tvPrice: TextView = itemView.findViewById(R.id.tv_hotel_price)
        val ratingHotel: RatingBar = itemView.findViewById(R.id.rating_hotel)
        val btnMaps: Button = itemView.findViewById(R.id.btn_maps)
        val btnDetail: Button = itemView.findViewById(R.id.btn_detail)
        val viewPagerImage: androidx.viewpager2.widget.ViewPager2 = itemView.findViewById(R.id.viewPagerImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_hotel, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val hotel = listHotel[position]
        val context = holder.itemView.context
        val carouselAdapter = ImageAdapter(listOf(hotel), false, onItemClick)

        holder.viewPagerImage.adapter = carouselAdapter
        holder.tvName.text = hotel.name
        holder.tvLocation.text = hotel.location
        holder.tvPrice.text = hotel.price
        holder.ratingHotel.rating = hotel.stars.length.toFloat()

        holder.btnMaps.setOnClickListener {
            timber.log.Timber.d("LOG_EVENT: Tombol Maps (Explicit Intent) diklik untuk hotel: ${hotel.name}")

            val query = "geo:0,0?q=${hotel.name} ${hotel.location}"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(query))
            context.startActivity(intent)
        }

        holder.btnDetail.setOnClickListener {
            timber.log.Timber.d("LOG_EVENT: Tombol Detail diklik untuk hotel: ${hotel.name}")
            onItemClick(hotel)
        }
    }

    override fun getItemCount(): Int = listHotel.size
}

class ImageAdapter(
    private val listHotel: List<Hotel>,
    private val showText: Boolean = false,
    private val onItemClick: (Hotel) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.imageView)
        val tvName: TextView = view.findViewById(R.id.tvHotelName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.viewpager_item, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val hotel = listHotel[position]

        holder.img.setImageResource(hotel.images[0])

        if (showText) {
            holder.tvName.visibility = View.VISIBLE
            holder.tvName.text = hotel.name
        } else {
            holder.tvName.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            timber.log.Timber.d("LOG_EVENT: Item ${hotel.name} diklik. Navigasi ke Detail dijalankan.")

            onItemClick(hotel)
        }
    }

    override fun getItemCount(): Int = listHotel.size
}