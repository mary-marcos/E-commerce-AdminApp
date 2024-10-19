package com.example.e_commerceadmin.ui.home.myHome.viewModel_home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(var repo:Repository) :ViewModel(){

    private val _productCount = MutableStateFlow<UiState<ProdCountResponse>>(UiState.Loading())
    val productCount: StateFlow<UiState<ProdCountResponse>> = _productCount


    init {
        getProductCount()
    }

    private fun getProductCount() {
        viewModelScope.launch {
            repo.getCountOfProducts()
                .catch { e ->
                    _productCount.value = UiState.Failed(e)
                    Log.e("HomeViewModel", "Error fetching product count: ${e.message}")
                }
                .collect { cou ->
                    _productCount.value = UiState.Success(cou)
                }
        }
    }

}