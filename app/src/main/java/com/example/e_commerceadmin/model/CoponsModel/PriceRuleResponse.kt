package com.example.e_commerceadmin.model.CoponsModel

import com.google.gson.annotations.SerializedName


data class PriceRuleResponse(
    @SerializedName("price_rules")
    var priceRules : ArrayList<PriceRule>
)
