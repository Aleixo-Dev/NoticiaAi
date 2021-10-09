package com.nicolas.noticiaai.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nicolas.noticiaai.databinding.ItemsRecyclerSubBinding
import com.nicolas.noticiaai.domain.model.NoticeUiDomain

class AdapterSub(
    private val noticeList: List<NoticeUiDomain>,
    private val clickNotice: ((notice: NoticeUiDomain) -> Unit)

) : RecyclerView.Adapter<AdapterSub.ViewHolder>() {

    class ViewHolder(
        binding: ItemsRecyclerSubBinding,
        private val clickNotice: ((notice: NoticeUiDomain) -> Unit)
    ) : RecyclerView.ViewHolder(binding.root) {

        private val tvDescriptionSlider: TextView = binding.tvDescriptionSlider
        private val tvSourceSlider: TextView = binding.tvSourceSlider
        private val imageViewSlider: ImageView = binding.imageViewSlider

        fun bind(notice: NoticeUiDomain) {
            loadImageUrl(imageViewSlider, notice.urlToImage)
            tvDescriptionSlider.text = notice.description
            tvSourceSlider.text = notice.sourceName
            itemView.setOnClickListener {
                clickNotice.invoke(notice)
            }
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
        val layout =
            ItemsRecyclerSubBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ViewHolder(layout, clickNotice)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noticeList[position])
    }

    override fun getItemCount() = noticeList.size
}