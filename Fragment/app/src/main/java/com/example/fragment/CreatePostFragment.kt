package com.example.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fragment.databinding.FragmentCreatePostBinding

class CreatePostFragment : Fragment(R.layout.fragment_create_post) {

    private var _binding: FragmentCreatePostBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentCreatePostBinding.bind(view)

        binding.btnBackDetail.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.btnPublish.setOnClickListener {
            Toast.makeText(context, "Postingan berhasil diunggah!", Toast.LENGTH_SHORT).show()
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}