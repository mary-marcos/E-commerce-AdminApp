package com.example.e_commerceadmin.ui.home.MyProducts.NewProduct

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceadmin.R
import com.example.e_commerceadmin.databinding.ImgItemBinding
import com.example.e_commerceadmin.model.ProductModel.ImagesItem

class ImagesAdapter() : ListAdapter<ImagesItem, ImagesAdapter.AddImagesViewHolder>(AddImagesDiffUtil) {

    class AddImagesViewHolder(val addImagesBinding: ImgItemBinding) :
        RecyclerView.ViewHolder(addImagesBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddImagesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val addImagesBinding = ImgItemBinding.inflate(inflater, parent, false)
        return AddImagesViewHolder(addImagesBinding)
    }

    override fun onBindViewHolder(holder: AddImagesViewHolder, position: Int) {
        val current = getItem(position)
        holder.addImagesBinding.apply {
            val imageUrl = current.src
                ?: "https://cdn.shopify.com/s/files/1/0703/5830/2955/files/8cd561824439482e3cea5ba8e3a6e2f6.jpg?v=1716233144"
            Glide.with(holder.itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.add_prod_main_img)
                .error(R.drawable.search_off)
                .into(imageOfProductItem)

            deleteImage.setOnClickListener {
               // onDeleteClick(current)
            }
        }
    }
}

object AddImagesDiffUtil : DiffUtil.ItemCallback<ImagesItem>() {
    override fun areItemsTheSame(oldItem: ImagesItem, newItem: ImagesItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ImagesItem, newItem: ImagesItem): Boolean {
        return oldItem == newItem
    }
}
