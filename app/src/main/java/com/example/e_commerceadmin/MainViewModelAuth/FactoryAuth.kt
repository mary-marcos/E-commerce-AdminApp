package com.example.e_commerceadmin.MainViewModelAuth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.ui.home.myHome.viewModel_home.HomeViewModel



 class FactoryAuth (var repo: Repository):
        ViewModelProvider.Factory{


        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ViewModelAuth::class.java)) {
                return ViewModelAuth(repo) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }

}