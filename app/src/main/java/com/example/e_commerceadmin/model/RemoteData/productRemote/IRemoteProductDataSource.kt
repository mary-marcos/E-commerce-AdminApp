package com.example.e_commerceadmin.model.RemoteData.productRemote

import com.example.e_commerceadmin.model.CoponsModel.DiscountCode
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeRequest
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
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

    //////copones
    suspend fun getPriceRules(): List<PriceRule>
    suspend fun createPriceRules(rulerequest: PriceRuleRequest): PriceRuleResponsePost
    suspend fun updatePriceRule(ruleID: Long, body: PriceRuleResponsePost): PriceRuleResponsePost
    ///
    suspend fun getDiscounts(ruleID : Long): List<DiscountCode>
    suspend fun createDiscount(ruleID : Long,rulerequest: DiscountCodeRequest): DiscountCodeResponse
}