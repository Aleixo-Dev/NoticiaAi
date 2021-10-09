package com.nicolas.noticiaai.presentation.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.nicolas.noticiaai.databinding.HomeFragmentBinding
import com.nicolas.noticiaai.domain.model.NoticeUiDomain
import dagger.hilt.android.AndroidEntryPoint
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.nicolas.noticiaai.common.*
import java.net.URI
import java.net.URL

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var imageUri: Uri? = null

    private val getContent =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            imageUri = it
            binding.profileImage.setImageURI(it)
        }

    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupListeners()
        observerNoticeSports()
        observerNoticeTechnology()
        observerNoticeScience()
        getValueNameKey()
    }

    @SuppressLint("SetTextI18n")
    private fun getValueNameKey() = binding.apply {
        val sharedPreferences =
            activity?.getSharedPreferences(
                Constants.USER_NAME_APP,
                Context.MODE_PRIVATE
            )
        val name = sharedPreferences?.getString(Constants.USER_NAME, null)
        tvNameUser.text = "Oi,$name"
    }

    private fun observerNoticeScience() {
        viewModel.science.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NoticeUiState.Loading -> {
                    LoadingUtils.showDialog(requireContext(), state.isLoading)
                }
                is NoticeUiState.Success -> {
                    LoadingUtils.hideDialog()
                    setupRecyclerViewSubPrincipal(state.notices)
                }
                is NoticeUiState.Error -> {
                    showToast(state.error)
                }
            }
        }
    }

    private fun observerNoticeSports() {
        viewModel.sports.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NoticeUiState.Loading -> {
                    LoadingUtils.showDialog(requireContext(), state.isLoading)
                }
                is NoticeUiState.Success -> {
                    LoadingUtils.hideDialog()
                    setupRecyclerView(state.notices)
                }
                is NoticeUiState.Error -> {
                    showToast(state.error)
                }
            }
        }
    }

    private fun observerNoticeTechnology() {
        viewModel.technology.observe(viewLifecycleOwner) { state ->
            when (state) {
                is NoticeUiState.Loading -> {
                    LoadingUtils.showDialog(requireContext(), state.isLoading)
                }
                is NoticeUiState.Success -> {
                    LoadingUtils.hideDialog()
                    setupRecyclerViewSub(state.notices)
                }
                is NoticeUiState.Error -> {
                    showToast(state.error)
                }
            }
        }
    }

    private fun setupListeners() = binding.apply {
        profileImage.setOnClickListener {
            getImageGallery()
        }
    }

    private fun getImageGallery() {
        getContent.launch("image/*")
    }

    private fun shareNotice(textNotice: String, imageUriNotice: URI) {
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, imageUriNotice)
            putExtra(
                Intent.EXTRA_TEXT,
                textNotice
            )
            type = "image/*"
        }
        startActivity(Intent.createChooser(shareIntent, "Share image via:"))
    }

    private fun setupRecyclerViewSubPrincipal(notices: List<NoticeUiDomain>) = binding.apply {
        recyclerSubPrincipal.run {
            setHasFixedSize(true)
            adapter = AdapterMain(notices)
        }
    }

    private fun setupRecyclerView(notices: List<NoticeUiDomain>) = binding.apply {
        recyclerPrincipal.run {
            setHasFixedSize(true)
            adapter = AdapterMain(notices)
        }
    }

    private fun setupRecyclerViewSub(notices: List<NoticeUiDomain>) = binding.apply {
        recyclerSub.run {
            setHasFixedSize(true)
            adapter = AdapterSub(notices) {
                val url = URL(it.urlToImage)
                val uri = url.toURI()
                shareNotice(it.title, uri)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}