package com.nicolas.noticiaai.presentation.profile

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.request.RequestOptions
import com.nicolas.noticiaai.R
import com.nicolas.noticiaai.common.showToast
import com.nicolas.noticiaai.databinding.ProfileFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var imageUri: Uri? = null

    private val viewModel: ProfileViewModel by viewModels()

    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            imageUri = uri
            binding.imgProfile.setImageURI(imageUri)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()

        getUserProfile()
    }

    private fun getUserProfile() {
        viewModel.user.observe(viewLifecycleOwner) { user ->
            binding.apply {
                inputProfileName.setText(user.name)
                showImageProfile(user.image)
            }
        }
    }

    private fun showImageProfile(url: String?) = binding.apply {
        url?.let {
            val imageUri = it.toUri().buildUpon().scheme("https").build()
            Glide.with(imgProfile.context)
                .load(imageUri)
                .placeholder(R.drawable.progress_animation)
                .centerCrop()
                .into(imgProfile)
        }
    }

    private fun setupListeners() = binding.apply {
        imgProfile.setOnClickListener {
            getImageFromGallery()
        }
    }

    private fun getImageFromGallery() {
        getContent.launch("image/*")
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}