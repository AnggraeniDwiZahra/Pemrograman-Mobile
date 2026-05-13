package com.example.modul3

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.modul3.databinding.FragmentHomeBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HotelViewModel by viewModels {
        HotelViewModelFactory("PROMO_MHS_ULM")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvHotels.layoutManager = LinearLayoutManager(requireContext())

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.hotels.collectLatest { list ->
                    if (list.isNotEmpty()) {
                        binding.rvHotels.adapter = HotelAdapter(list) { hotel ->
                            viewModel.onHotelClicked(hotel)
                        }

                        binding.viewPagerHeader.adapter = ImageAdapter(list, true) { hotel ->
                            viewModel.onHotelClicked(hotel)
                        }

                        setupDots(list.size)
                        updateDots(0, binding.dotsContainer)
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.navigateToDetail.collect { hotel ->
                    hotel?.let {
                        val intent = Intent(requireContext(), DetailActivity::class.java)

                        intent.putExtra("EXTRA_HOTEL_NAME", it.name)
                        intent.putExtra("EXTRA_HOTEL_IMAGE", it.images[0])
                        intent.putExtra("EXTRA_HOTEL_RATING", it.stars)
                        intent.putExtra("EXTRA_HOTEL_LOCATION", it.location)
                        intent.putExtra("EXTRA_HOTEL_PRICE", it.price)
                        intent.putExtra("EXTRA_HOTEL_DESC", getString(R.string.hotel_description, it.name))

                        startActivity(intent)

                        viewModel.onDetailNavigated()
                    }
                }
            }
        }

        binding.btnLanguage.setOnClickListener {
            timber.log.Timber.d("LOG_EVENT: Tombol Explicit Intent (Bahasa) ditekan.")

            val intent = Intent(requireContext(), LanguageActivity::class.java)
            startActivity(intent)
        }

        binding.viewPagerHeader.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position, binding.dotsContainer)
            }
        })
    }

    private fun setupDots(size: Int) {
        binding.dotsContainer.removeAllViews()
        val dots = arrayOfNulls<ImageView>(size)
        for (i in 0 until size) {
            dots[i] = ImageView(requireContext())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}