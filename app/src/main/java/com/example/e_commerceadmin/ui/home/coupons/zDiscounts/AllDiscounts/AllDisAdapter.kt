package com.example.e_commerceadmin.ui.home.coupons.zDiscounts.AllDiscounts

import android.content.Context
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.e_commerceadmin.constant.Helpers.GetTime
import com.example.e_commerceadmin.databinding.DiscountItemBinding
import com.example.e_commerceadmin.databinding.RulePriceItemBinding
import com.example.e_commerceadmin.model.CoponsModel.DiscountCode
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.AllRulesAdapter
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.DiffCallback1

class AllDisAdapter(val onClick:(DiscountCode)-> Unit,
                    val onItemClicked:(Long)-> Unit,
                    val onDeleteClick: (DiscountCode) -> Unit):  ListAdapter<DiscountCode, AllDisAdapter.ItemViewHolder>( DiffCallbackdis()
)

{



    lateinit var binding: DiscountItemBinding
    class ItemViewHolder(var binding: DiscountItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater : LayoutInflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        binding= DiscountItemBinding.inflate(inflater,parent,false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val current=getItem(position)


        holder.binding.titleTextview.text = current.code


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

class  DiffCallbackdis : DiffUtil.ItemCallback<DiscountCode>(){
    override fun areItemsTheSame(oldItem: DiscountCode, newItem: DiscountCode): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DiscountCode, newItem: DiscountCode): Boolean {
        return oldItem == newItem
    }
}