package com.example.e_commerceadmin.model.ProductModel

import com.google.gson.annotations.SerializedName

data class OneProductsResponse(

    @field:SerializedName("product")
    val product: Product
)

data class ProductBody(

    @field:SerializedName("product")
    val product: Product? = null
)