package com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel.AllRulesViewModel

class CreateRuleFactory (var repo: Repository):
    ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CreateRuleviewModel::class.java)) {
            return CreateRuleviewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}