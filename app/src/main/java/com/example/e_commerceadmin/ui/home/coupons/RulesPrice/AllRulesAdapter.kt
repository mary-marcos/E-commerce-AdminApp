package com.example.e_commerceadmin.ui.home.coupons.RulesPrice

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.e_commerceadmin.constant.Helpers.GetTime
import com.example.e_commerceadmin.databinding.ProductItemBinding
import com.example.e_commerceadmin.databinding.RulePriceItemBinding
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.ProductModel.ProductItem
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.AllProductAdapter

class AllRulesAdapter (val onClick:(PriceRule)-> Unit,
                       val onItemClicked:(Long)-> Unit,
                       val onDeleteClick: (PriceRule) -> Unit):  ListAdapter<PriceRule, AllRulesAdapter.ItemViewHolder>(DiffCallback1())

{



    lateinit var binding: RulePriceItemBinding
    class ItemViewHolder(var binding: RulePriceItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding= RulePriceItemBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current=getItem(position)


        holder.binding.titleTextview.text = current.title
        holder.binding.valueTextview.text = if (current.value_type == "fixed_amount") {
            "${current.value} EGP"
        } else {
            "${current.value}%"
        }

        holder.binding.createdAtTextview.text = GetTime.formatDateString(current.created_at)

        holder.binding.startAtTextview.text = Html.fromHtml("<b>From:</b> ${GetTime.formatDateString(current.starts_at)}")
        Log.i("TAG", "onBindViewHolder: ${GetTime.formatDateString(current.created_at)}/////${current.created_at}")
        holder.binding.endsAtTextview.text = if (current.ends_at.isNullOrEmpty()) {
            Html.fromHtml("<b>To:</b> Not determined yet")
        } else {
            Html.fromHtml("<b>To:</b> ${GetTime.formatDateString(current.ends_at)}")
        }

        holder.binding.ruleCardView.setOnClickListener {
            onItemClicked(current.id?:0L)

        }
        holder.binding.editImage.setOnClickListener {
            onClick(current)
        }

        holder.binding.deleteImage.setOnClickListener {
           onDeleteClick(current)
        }
    }
}



class  DiffCallback1 : DiffUtil.ItemCallback<PriceRule>(){
    override fun areItemsTheSame(oldItem: PriceRule, newItem: PriceRule): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: PriceRule, newItem: PriceRule): Boolean {
        return oldItem == newItem
    }
}