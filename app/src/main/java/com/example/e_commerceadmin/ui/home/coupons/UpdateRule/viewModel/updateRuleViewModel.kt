package com.example.e_commerceadmin.ui.home.coupons.UpdateRule.viewModel
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.Repository.Repository
import com.example.e_commerceadmin.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
class updateRuleViewModel(var repo: Repository): ViewModel() {

    private val _updateRule:MutableStateFlow<UiState<PriceRuleResponsePost>> = MutableStateFlow(UiState.Loading())
    val updaterule: StateFlow<UiState<PriceRuleResponsePost>> = _updateRule




    fun updateruleF(ruleid:Long,rulerequest: PriceRuleResponsePost) {
        viewModelScope.launch {
            try {
                repo.updatePriceRules(ruleid,rulerequest).collect {
                    _updateRule.value = UiState.Success(it!!)
                }
            } catch (e: java.lang.Exception) {
                _updateRule.value = UiState.Failed(e)
                Log.d("TAG", "updateruleF:$e ")
            }
        }


    }
}