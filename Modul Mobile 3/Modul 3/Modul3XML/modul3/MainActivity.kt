package com.example.modul3

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.modul3.databinding.ActivityMainBinding
import java.util.Locale

class MainActivityXml : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val hotelList = listOf(
        Hotel("Kollektiv Hotel", "⭐⭐⭐", "Sukajadi, Bandung", "Rp423.649", listOf(R.drawable.kollektiv)),
        Hotel("eL Hotel Bandung", "⭐⭐⭐⭐", "Merdeka, Bandung", "Rp724.054", listOf(R.drawable.elhotel)),
        Hotel("Geary Hotel Bandung","⭐⭐⭐", "Pasirkaliki, Bandung", "Rp387.061", listOf(R.drawable.geary)),
        Hotel("Pullman Bandung Grand Central", "⭐⭐⭐⭐⭐", "Cibeunying, Bandung", "Rp1.652.504", listOf(R.drawable.pullman)),
        Hotel("Grandia Hotel Bandung", "⭐⭐⭐⭐", "Cihampelas, Bandung", "Rp532.350", listOf(R.drawable.grandia))
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLanguage.setOnClickListener {
            showLanguageDialog()
        }

        binding.rvHotels.layoutManager = LinearLayoutManager(this)
        binding.rvHotels.adapter = HotelAdapter(hotelList)

        binding.viewPagerHeader.adapter = ImageAdapter(hotelList, true)
        binding.viewPagerHeader.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        setupDots(5)
        updateDots(0, binding.dotsContainer)

        binding.viewPagerHeader.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position, binding.dotsContainer)
            }
        })
    }

    private fun showLanguageDialog() {
        val languages = arrayOf("Bahasa Indonesia", "English")
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.language_selector))
        builder.setItems(languages) { dialog, which ->
            when (which) {
                0 -> setAppLocale("in")
                1 -> setAppLocale("en")
            }
            dialog.dismiss()
        }
        builder.show()
    }

    private fun setAppLocale(localeCode: String) {
        val locale = Locale(localeCode)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        recreate()
    }

    private fun setupDots(size: Int) {
        binding.dotsContainer.removeAllViews()
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(this)
            dots[i]?.setImageResource(R.drawable.tab_selector)
            val params = LinearLayout.LayoutParams(25, 25)
            params.setMargins(8, 0, 8, 0)
            binding.dotsContainer.addView(dots[i], params)
        }
    }

    private fun updateDots(position: Int, container: LinearLayout) {
        for (i in 0 until container.childCount) {
            val dot = container.getChildAt(i) as ImageView
            dot.isSelected = (i == position)
        }
    }
}
