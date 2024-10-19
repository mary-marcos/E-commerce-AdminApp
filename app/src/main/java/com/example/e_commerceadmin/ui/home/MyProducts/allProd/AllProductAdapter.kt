package com.example.e_commerceadmin.ui.home.MyProducts.allProd

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceadmin.databinding.ProductItemBinding
import com.example.e_commerceadmin.model.ProductModel.ProductItem
import com.bumptech.glide.Glide

class AllProductAdapter( val onClick:(ProductItem)-> Unit,
                         val onDeleteClick: (ProductItem) -> Unit):  ListAdapter<ProductItem, AllProductAdapter.ItemViewHolder>(DiffCallback1())  {



    lateinit var binding: ProductItemBinding
    class ItemViewHolder(var binding: ProductItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding=ProductItemBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current=getItem(position)

        holder.binding.productPrice.text = (current.variants?.get(0)?.price ?: 100).toString()+""
        holder.binding.productName.text = current.title
        holder.binding.categoryName.text = "Category: ${current.productType}"
        holder.binding.vendorOfProduct.text = "Vendor: ${current.vendor}"
        holder.itemView.setOnClickListener {
            onClick(current)
        }

        holder.binding.deleteProduct.setOnClickListener {
            onDeleteClick(current)
        }



        var currentImage= current.image?.src?:"https://cdn.shopify.com/s/files/1/0904/6075/0123/files/85cc58608bf138a50036bcfe86a3a362.jpg?v=1728735014"

         Glide.with(holder.itemView.context).load(currentImage).into(holder.binding.productImg)

    }

}

class  DiffCallback1 : DiffUtil.ItemCallback<ProductItem>(){
    override fun areItemsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: ProductItem, newItem: ProductItem): Boolean {
        return oldItem == newItem
    }
}