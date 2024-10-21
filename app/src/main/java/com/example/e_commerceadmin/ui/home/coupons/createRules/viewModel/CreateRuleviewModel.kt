package com.example.e_commerceadmin.ui.home.coupons.createRules.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class CreateRuleviewModel (var repo: Repository): ViewModel() {

    private val _createRule = MutableStateFlow<UiState<PriceRuleResponsePost>>(UiState.Loading())
    val createRule: StateFlow<UiState<PriceRuleResponsePost>> = _createRule




    fun createRulesf(rulerequest: PriceRuleRequest) {
        viewModelScope.launch {
            try {
                val newRule = repo.createPriceRules(rulerequest)
                _createRule.value = UiState.Success(newRule)
            } catch (e: Exception) {
                Log.d("TAG", "createrule:${e.message} ")
                _createRule.value = UiState.Failed(e)
            }
        }
    }
}