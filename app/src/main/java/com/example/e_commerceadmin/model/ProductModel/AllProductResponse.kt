package com.example.e_commerceadmin.model.ProductModel

import com.google.gson.annotations.SerializedName


data class AllProductResponse(

    @SerializedName("products")
    val getProducts: List<ProductItem>
    )