package com.example.e_commerceadmin.ui.home.coupons.UpdateRule.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel.CreateRuleviewModel

class ViewModelFactory (var repo: Repository):
    ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(updateRuleViewModel::class.java)) {
            return updateRuleViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}