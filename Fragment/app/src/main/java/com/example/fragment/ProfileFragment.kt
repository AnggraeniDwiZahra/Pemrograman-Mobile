package com.example.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.fragment.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentProfileBinding.bind(view)

        binding.btnSettings.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingFragment)
        }

        binding.btnEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding.btnFollowers.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_followersFragment)
        }

        binding.btnFollowing.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_followingFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}