package sa.biotic.app.retrofit_service

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Category
import sa.biotic.app.model.Offer
import sa.biotic.app.model.Product


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

    @GET("general/getBundles")
    suspend fun getBundles(
        @Query("currentpage") currentpage: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MutableList<BundleProds>>


}