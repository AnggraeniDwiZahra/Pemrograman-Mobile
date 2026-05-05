package com.example.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.fragment.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment(R.layout.fragment_following) {

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFollowingBinding.bind(view)

        binding.btnBackDetail.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}