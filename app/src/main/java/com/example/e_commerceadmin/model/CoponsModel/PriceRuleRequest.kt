package com.example.e_commerceadmin.model.CoponsModel

import com.google.gson.annotations.SerializedName




data class PriceRuleRequest(
    @SerializedName("price_rule")
    var priceRule : OneRule
)

data class OneRule(
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("target_type")
    var targetType: String? = null,
    @SerializedName("target_selection")
    var targetSelection: String? = null,
    @SerializedName("allocation_method")
    var allocationMethod: String? = null,
    @SerializedName("value_type")
    var valueType: String? = null,
    @SerializedName("value")
    var value: String? = null,
    @SerializedName("customer_selection")
    var customerSelection: String? = null,
    @SerializedName("starts_at")
    var startsAt: String? = null,
    @SerializedName("ends_at")
    var endsAt: String? = null,
    @SerializedName("once_per_customer")
    var once_per_customer: String? = null,
    @SerializedName("usage_limit")
    var usage_limit: Int? = null
)
