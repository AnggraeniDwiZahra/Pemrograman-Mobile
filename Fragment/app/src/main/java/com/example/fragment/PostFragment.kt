package com.example.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.FragmentPostBinding

class PostFragment : Fragment(R.layout.fragment_post) {

    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPostBinding.bind(view)

        binding.btnAddPost.setOnClickListener {
            val action = PostFragmentDirections.actionPostFragmentToCreatePostFragment()
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}