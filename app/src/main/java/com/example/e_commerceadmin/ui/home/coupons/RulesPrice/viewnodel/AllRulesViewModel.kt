package com.example.e_commerceadmin.ui.home.coupons.RulesPrice.viewnodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AllRulesViewModel (var repo: Repository): ViewModel() {

    private val _allRules = MutableStateFlow<UiState<List<PriceRule>>>(UiState.Loading())
    val allRules: StateFlow<UiState<List<PriceRule>>> = _allRules


    init {
        getAllRules()
    }

    private fun getAllRules() {
        viewModelScope.launch {
            repo.getPriceRules()
                .catch { e ->
                    _allRules.value = UiState.Failed(e)
                    Log.e("HomeViewModel", "Error fetching priceRule list: ${e.message}")
                }
                .collect { productList ->
                    _allRules.value = UiState.Success(productList)
                }
        }
    }
}