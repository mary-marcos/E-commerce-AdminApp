package com.example.e_commerceadmin.ui.home.MyProducts.NewProduct.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UpdateProductViewModel (var repo: Repository): ViewModel(){


    private val _updateProductState = MutableStateFlow<UiState<OneProductsResponse>>(UiState.Loading())
    val updateProductState: StateFlow<UiState<OneProductsResponse>> = _updateProductState

    fun updateProduct(productId: Long, product: OneProductsResponse) {
        _updateProductState.value = UiState.Loading()
        viewModelScope.launch {
            try {
                val updatedProduct = repo.updateProduct(productId, product)
                _updateProductState.value = UiState.Success(updatedProduct)
            } catch (e: Exception) {
                _updateProductState.value = UiState.Failed(e)
            }
        }
    }
}