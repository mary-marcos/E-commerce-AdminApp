package com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostProdViewModel(var repo: Repository): ViewModel() {


    private val _createProductState = MutableStateFlow<UiState<OneProductsResponse>>(UiState.Loading())
    val createProductState: StateFlow<UiState<OneProductsResponse>> = _createProductState

    fun createProduct(body: ProductBody) {
        _createProductState.value = UiState.Loading()
        viewModelScope.launch {
            try {
                val newProduct = repo.createProduct(body)
                _createProductState.value = UiState.Success(newProduct)
            } catch (e: Exception) {
                Log.d("TAG", "createProduct:${e.message} ")
                _createProductState.value = UiState.Failed(e)
            }
        }
    }
}