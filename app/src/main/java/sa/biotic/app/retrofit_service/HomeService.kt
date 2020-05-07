package sa.biotic.app.retrofit_service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sa.biotic.app.model.*


interface HomeService {
    @GET("general/GetAllOffers")
    suspend fun getOffers(
        @Query("currentpage") currentpage: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MutableList<Offer>>

    @GET("general/getCategories")
    suspend fun getCategories(): Response<MutableList<Category>>


    @GET("general/getallprouducts")
    suspend fun getAllProducts(
        @Query("currentpage") currentpage: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MutableList<Product>>

    @GET("general/getHomeprouducts")
    suspend fun getHomeprouducts(
        @Query("currentpage") currentpage: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MutableList<Product>>


    @GET("general/getBundles")
    suspend fun getBundles(
        @Query("currentpage") currentpage: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MutableList<BundleProduct>>

    @GET("general/getHomeBundles")
    suspend fun getHomeBundles(
        @Query("currentpage") currentpage: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MutableList<BundleProduct>>


    @GET("general/getCategoryProduct")
    suspend fun getCategoryProducts(
        @Query("categoryId") categoryId: Int
    ): Response<MutableList<Product>>

    @GET("general/Search")
    suspend fun Search(
        @Query("word") word: String,
        @Query("pageSize") pageSize: Int,
        @Query("currentpage") currentpage: Int,
        @Query("lang") lang: String
    ): Response<MutableList<SearchItem>>


    @GET("general/getBundleProduct")
    suspend fun getBundleProduct(
        @Query("BundleId") BundleId: Int
    ): Response<ProductsOfBundle>


    @GET("general/getProduct")
    suspend fun getProduct(
        @Query("productid") productid: Int
    ): Response<Product>


    @GET("general/getOffer")
    suspend fun getOfferList(
        @Query("offerId") offerId: Int
    ): Response<MutableList<OfferProduct>>


}