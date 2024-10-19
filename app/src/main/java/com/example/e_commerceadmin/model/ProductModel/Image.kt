package com.example.e_commerceadmin.model.ProductModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Image(
    @SerializedName("updated_at")
    val updatedAt: String? = null,

    @SerializedName("src")
    val src: String? = null,

    @SerializedName("product_id")
    val productId: Long? = null,

    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String? = null,

    @SerializedName("alt")
    val alt: Any? = null,

    @SerializedName("width")
    val width: Int? = null,

    @SerializedName("created_at")
    val createdAt: String? = null,

    @SerializedName("variant_ids")
    val variantIds: List<Any>? = null,

    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("position")
    val position: Int? = null,

    @SerializedName("height")
    val height: Int? = null
) : Serializable

