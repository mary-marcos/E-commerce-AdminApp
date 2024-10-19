package com.example.e_commerceadmin.model.RemoteData.productRemote

import com.example.e_commerceadmin.NetworkApi.ApiService.ProductsApiServices
import com.example.e_commerceadmin.NetworkApi.NetworkRetrofit
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow



class RemoteProductDataSource(private val productService: ProductsApiServices) :
    IRemoteProductDataSource {

    companion object {
        @Volatile
        private var INSTANCE: RemoteProductDataSource? = null
        fun getInstance(): RemoteProductDataSource {
            return INSTANCE ?: synchronized(this) {
                val instance = RemoteProductDataSource(NetworkRetrofit.productsApiService())
                INSTANCE = instance
                instance
            }
        }
    }


   override fun getProductsCount() = flow {
        emit(productService.getCountOfProducts())
    }

     override fun getAllProducts()= flow {
        emit(productService.getProducts())
    }



    override suspend fun postProduct(body: ProductBody): OneProductsResponse {
        return productService.createProduct(body)
    }


    override suspend fun updateProduct(productId: Long, product: OneProductsResponse): OneProductsResponse {
        return productService.updateProduct(productId, product)
    }

    override suspend fun deleteProduct(productId: Long?) {
        productService.deleteProduct(productId)
    }


}