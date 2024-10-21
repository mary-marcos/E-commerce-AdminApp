package com.example.e_commerceadmin.model.CoponsModel

import com.google.gson.annotations.SerializedName

data class DiscountCode(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("price_rule_id") var priceRuleId: Long? = null,
    @SerializedName("code") var code: String? = null,
    @SerializedName("usage_count") var usageCount: Int? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null
)

data class AllDiscountCodes( val discount_codes: List<DiscountCode>)


data class DiscountCodeRequest(
@SerializedName("discount_code")
var discountCode : OneItemCode
)
data class OneItemCode(

    @SerializedName("code")
    var code : String
)
data class DiscountCodeResponse(
    @SerializedName("discount_code")
    var discountCode: DiscountCode
)