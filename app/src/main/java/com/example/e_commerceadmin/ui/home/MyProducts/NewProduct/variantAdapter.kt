package com.example.e_commerceadmin.ui.home.MyProducts.NewProduct

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceadmin.databinding.SpecificationItemBinding
import com.example.e_commerceadmin.model.ProductModel.VariantsItem

class variantAdapter()  : ListAdapter<VariantsItem, variantAdapter.VariantsViewHolder>(VariantsDiffUtil) {
    class VariantsViewHolder(val variantItemBinding: SpecificationItemBinding)
        : RecyclerView.ViewHolder(variantItemBinding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VariantsViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val variantsItemBinding = SpecificationItemBinding.inflate(inflater, parent,false)
        return VariantsViewHolder(variantsItemBinding)
    }

    override fun onBindViewHolder(holder: VariantsViewHolder, position: Int) {
        val current = getItem(position)

        holder.variantItemBinding.colorName.text = current.title
        holder.variantItemBinding.colorPrice.text = "Price: ${current.price}"

        holder.variantItemBinding.deleteVariant.setOnClickListener {
          //  onClick(current)
        }
    }
}

object VariantsDiffUtil : DiffUtil.ItemCallback<VariantsItem>() {
    override fun areItemsTheSame(oldItem: VariantsItem, newItem: VariantsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: VariantsItem, newItem: VariantsItem): Boolean {
        return oldItem == newItem
    }

}
