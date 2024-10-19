package com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct.AllProdViewModel

class PostProdFactory (var repo: Repository):
    ViewModelProvider.Factory{


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostProdViewModel::class.java)) {
            return PostProdViewModel(repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}