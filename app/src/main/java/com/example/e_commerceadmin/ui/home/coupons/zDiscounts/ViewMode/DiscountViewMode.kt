package com.example.e_commerceadmin.ui.home.coupons.zDiscounts.ViewMode

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.CoponsModel.DiscountCode
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeRequest
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class DiscountViewMode (var repo: Repository): ViewModel() {

    private val _createcopon = MutableStateFlow<UiState<DiscountCodeResponse>>(UiState.Loading())
    val createcopon: StateFlow<UiState<DiscountCodeResponse>> = _createcopon


    private var _deleteDiscount: MutableStateFlow<UiState<String>> = MutableStateFlow(
        UiState.Loading()
    )
    var deleteDiscount: StateFlow<UiState<String>> = _deleteDiscount

    private val _allDiscounts = MutableStateFlow<UiState<List<DiscountCode>>>(UiState.Loading())
    val allDiscounts: StateFlow<UiState<List<DiscountCode>>> = _allDiscounts




     fun getAllDiscounts(ruleID:Long) {
        viewModelScope.launch {
            repo.getDiscounts(ruleID)
                .catch { e ->
                    _allDiscounts.value = UiState.Failed(e)
                    Log.e("HomeViewModel", "Error fetching priceRule list: ${e.message}")
                }
                .collect { productList ->
                    _allDiscounts.value = UiState.Success(productList)
                }
        }
    }

    fun deleteDiscount(ruleID:Long,disId:Long) {
        viewModelScope.launch {

            try{
                repo.deleteDiscount(ruleID,disId)
                _deleteDiscount.value= UiState.Success("deleted successfully" )

            }

            catch (e: Exception) {
                Log.d("TAG", "createrule:${e.message} ")
                _deleteDiscount.value = UiState.Failed(e)
            }


        }
    }


    fun createCoupones(ruleID:Long,discReqyest: DiscountCodeRequest) {
        viewModelScope.launch {
            try {
                val newRule = repo.createDiscount(ruleID,discReqyest)
                _createcopon.value = UiState.Success(newRule)
            } catch (e: Exception) {
                Log.d("TAG", "createrule:${e.message} ")
                _createcopon.value = UiState.Failed(e)
            }
        }
    }

}