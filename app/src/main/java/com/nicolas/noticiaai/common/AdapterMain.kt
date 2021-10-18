package com.nicolas.noticiaai.common

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicolas.noticiaai.databinding.ItemsRecyclerPrincipalBinding
import com.nicolas.noticiaai.domain.model.NoticeUiDomain

class AdapterMain(
    private val listNotice: List<NoticeUiDomain>
) : RecyclerView.Adapter<AdapterMain.ViewHolder>() {

    class ViewHolder(
        binding: ItemsRecyclerPrincipalBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val tvDescriptionNotice: TextView = binding.tvDescriptionNotice
        private val tvSourceName: TextView = binding.tvSourceName
        private val imgContainer: ImageView = binding.imgContainer

        fun bind(notice: NoticeUiDomain) {
            tvDescriptionNotice.text = notice.content
            tvSourceName.text = notice.sourceName
            loadImageUrl(imgContainer, notice.urlToImage)
        }

        private fun loadImageUrl(imageView: ImageView, url: String?) {
            url?.let {
                val imageUri = it.toUri().buildUpon().scheme("https").build()
                Glide.with(imageView.context)
                    .load(imageUri)
                    .centerCrop()
                    .into(imageView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = ItemsRecyclerPrincipalBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listNotice[position])
    }

    override fun getItemCount() = listNotice.size
}