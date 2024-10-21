package com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository

class AllRulesFactory (var repo: Repository):
    ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllRulesViewModel::class.java)) {
            return AllRulesViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }}