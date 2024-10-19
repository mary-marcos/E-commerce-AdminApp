package com.example.e_commerceadmin.model.RemoteData.productRemote

import com.example.e_commerceadmin.model.ProductModel.AllProductResponse
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse
import com.example.e_commerceadmin.model.ProductModel.ProdCountResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import kotlinx.coroutines.flow.Flow

interface IRemoteProductDataSource {
    fun getProductsCount(): Flow<ProdCountResponse>
    fun getAllProducts(): Flow<AllProductResponse>
   suspend fun postProduct(productBody:ProductBody):OneProductsResponse
    suspend fun updateProduct(productId: Long, product: OneProductsResponse): OneProductsResponse
    suspend fun deleteProduct(productId: Long?)
}