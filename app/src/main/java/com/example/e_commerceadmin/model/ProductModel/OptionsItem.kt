package com.example.e_commerceadmin.model.ProductModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class OptionsItem(

    @SerializedName("product_id")
    val productId: Long,

    @SerializedName("values")
    val values: Array<String?>,

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Long,

    @SerializedName("position")
    val position: Int
):Serializable