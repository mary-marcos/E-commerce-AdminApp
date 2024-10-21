package com.example.e_commerceadmin.MainViewModelAuth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.CustomersByEmailResponse
import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ViewModelAuth (var repo: Repository): ViewModel() {

    private val _allUsers = MutableStateFlow<UiState<CustomersByEmailResponse>>(UiState.Loading())
    val allUsers: StateFlow<UiState<CustomersByEmailResponse>> = _allUsers




     fun getadmin(email:String) {
        viewModelScope.launch {
            repo.getCustomerByEmail(email)
                .catch { e ->
                    _allUsers.value = UiState.Failed(e)
                    Log.e("HomeViewModel", "Error fetching admin Auth: ${e.message}")
                }
                .collect { custmerList ->
                    _allUsers.value = UiState.Success(custmerList)
                }
        }
    }


}