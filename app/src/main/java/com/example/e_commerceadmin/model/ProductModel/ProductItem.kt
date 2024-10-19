package com.example.e_commerceadmin.model.ProductModel

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductItem(
    @SerializedName("id")
    val id: Long?=null,
    @SerializedName("title")
    val title: String?=null,
    @SerializedName("body_html")
    val bodyHtml: String?=null,


    @SerializedName("vendor")
    val vendor: String?=null,
    @SerializedName("handle")
    val handle: String?=null,
    @SerializedName("product_type")
    val productType: String?=null,

    @SerializedName("created_at")
    val createdAt: String?=null,
    @SerializedName("updated_at")
    val updatedAt: String?=null,
    @SerializedName("published_at")
    val publishedAt: String?=null,

    @SerializedName("tags")
    val tags: String?=null,
    @SerializedName("published_scope")
    val publishedScope: String?=null,
    @SerializedName("template_suffix")
    val templateSuffix: Any?=null,

    @SerializedName("admin_graphql_api_id")
    val adminGraphqlApiId: String?=null,

    @SerializedName("status")
    val status: String?=null,

    @SerializedName("image")
    val image: Image? =null,
    @SerializedName("images")
    val images: List<ImagesItem>?=null,
    @SerializedName("variants")
    val variants: List<VariantsItem>?=null,
    @SerializedName("options")
    val options: List<OptionsItem>?=null,

) : Serializable