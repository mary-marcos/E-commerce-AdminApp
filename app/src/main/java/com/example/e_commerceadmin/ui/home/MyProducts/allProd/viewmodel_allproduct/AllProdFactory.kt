package com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository

class AllProdFactory (var repo: Repository):
    ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllProdViewModel::class.java)) {
            return AllProdViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}