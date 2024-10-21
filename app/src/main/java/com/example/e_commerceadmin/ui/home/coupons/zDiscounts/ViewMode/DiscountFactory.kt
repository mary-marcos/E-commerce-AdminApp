package com.example.e_commerceadmin.ui.home.coupons.zDiscounts.ViewMode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel.CreateRuleviewModel

class DiscountFactory (var repo: Repository):
    ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscountViewMode::class.java)) {
            return DiscountViewMode(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}