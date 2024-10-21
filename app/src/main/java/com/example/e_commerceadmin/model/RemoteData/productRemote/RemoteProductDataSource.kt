package com.example.e_commerceadmin.model.RemoteData.productRemote

import com.example.e_commerceadmin.NetworkApi.ApiService.ProductsApiServices
import com.example.e_commerceadmin.NetworkApi.NetworkRetrofit
import com.example.e_commerceadmin.model.CoponsModel.DiscountCode
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeRequest
import com.example.e_commerceadmin.model.CoponsModel.DiscountCodeResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRule
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleRequest
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponse
import com.example.e_commerceadmin.model.CoponsModel.PriceRuleResponsePost
import com.example.e_commerceadmin.model.CustomersByEmailResponse
import com.example.e_commerceadmin.model.ProductModel.OneProductsResponse
import com.example.e_commerceadmin.model.ProductModel.ProductBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import retrofit2.http.Path


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

    override   fun getCustomerByEmail(email:String)= flow{
        emit(productService.getCustomerByEmail(email))

    }

//    override fun getUsers()= flow {
//        emit(productService.getUsers())





    override suspend fun postProduct(body: ProductBody): OneProductsResponse {
        return productService.createProduct(body)
    }


    override suspend fun updateProduct(productId: Long, product: OneProductsResponse): OneProductsResponse {
        return productService.updateProduct(productId, product)
    }

    override suspend fun deleteProduct(productId: Long?) :Response<Unit>{
      return  productService.deleteProduct(productId)
    }

    /////////////////
  //  rules
    override suspend fun getPriceRules(): List<PriceRule>{
        return productService.getPriceRules().priceRules
    }

    override suspend fun createPriceRules(rulerequest:PriceRuleRequest):PriceRuleResponsePost{
        return productService.createPriceRule(rulerequest)
    }

    override suspend fun updatePriceRule(ruleID: Long, body: PriceRuleResponsePost): PriceRuleResponsePost {
        return productService.updatePriceRule(ruleID,body)
    }

    override suspend fun drleteRule(ruleID : Long):Response<Unit>{
        return productService.deletePriceRule(ruleID)
    }
    /////

    ////////
    //dis
    override suspend fun getDiscounts(ruleID : Long): List<DiscountCode>{
        return productService.getDiscounts(ruleID).discount_codes
    }

    override suspend fun createDiscount(ruleID : Long,rulerequest:DiscountCodeRequest):DiscountCodeResponse{
        return productService.createDiscountCode(ruleID,rulerequest)
    }


    override  suspend fun deleteDiscount(ruleID : Long, discountId : Long):Response<Unit>{
        return productService.deleteDiscount(ruleID,discountId)
    }
}