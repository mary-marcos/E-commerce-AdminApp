package com.example.e_commerceadmin.ui.home.MyProducts.allProd.viewmodel_allproduct

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllProdViewModel(var repo:Repository):ViewModel() {

    private val _allProduct = MutableStateFlow<UiState<AllProductResponse>>(UiState.Loading())
    val allProduct: StateFlow<UiState<AllProductResponse>> = _allProduct


    init {
        getAllProduct()
    }

    private fun getAllProduct() {
        viewModelScope.launch {
            repo.getAllProducts()
                .catch { e ->
                    _allProduct.value = UiState.Failed(e)
                    Log.e("HomeViewModel", "Error fetching product list: ${e.message}")
                }
                .collect { productList ->
                    _allProduct.value = UiState.Success(productList)
                }
        }
    }
}