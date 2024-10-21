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

    private var _deleteDiscount: MutableStateFlow<UiState<String>> = MutableStateFlow(
        UiState.Loading()
    )
    var deleteRule: StateFlow<UiState<String>> = _deleteDiscount
    init {
        getAllRules()
    }





    fun deleteRule(ruleID:Long) {
        viewModelScope.launch {

            try{
                repo.deleteRule(ruleID)
                _deleteDiscount.value= UiState.Success("deleted successfully" )

            }

            catch (e: Exception) {
                Log.d("TAG", "createrule:${e.message} ")
                _deleteDiscount.value = UiState.Failed(e)
            }


        }
    }

     fun getAllRules() {
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