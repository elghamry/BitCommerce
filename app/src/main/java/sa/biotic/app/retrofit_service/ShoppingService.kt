package sa.biotic.app.retrofit_service

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import sa.biotic.app.model.*


interface ShoppingService {

    @POST("Shopping/AddProductCart")
    suspend fun addToCart(
        @Body item: AddToCartModel
    ): Response<AddToCartResponse>


    @POST("Shopping/GetCartDetails")
    suspend fun getCartDetails(
        @Body item: GetCartDetailsModel
    ): Response<CartResponse>


    @POST("Shopping/UpdateCartItemQuantity")
    suspend fun updateCartItemQuantity(
        @Body item: UpdateCartItemQuantityModel
    ): Response<UpdateCartItemQuantityResponse>


    @POST("Shopping/DeleteCartItem")
    suspend fun deleteCartItem(
        @Body item: DeleteCartItemModel
    ): Response<DeleteCartItemResponse>


    @GET("shopping/GetCartDetailsNoLogin")
    suspend fun getCartDetailsNoLogin(
        @Query("ProductIDs") ProductIDs: String,
        @Query("Prices") Prices: String,
        @Query("isbundles") isbundles: String,
        @Query("OfferIDs") OfferIDs: String,
        @Query("Discounts") Discounts: String,
        @Query("StocksQuantity") StocksQuantity: String,
        @Query("Quantity") Quantity: String
    ): Response<CartResponse>

    @GET("shopping/CheckPromoCode")
    suspend fun checkPromoCode(
        @Query("promocode") promocode: String
    ): Response<CheckPromoResponse>

    @POST("Shopping/AddProductCartAfterLogin")
    suspend fun addProductCartAfterLogin(
        @Body item: AddProductCartAfterLoginModel
    ): Response<MutableList<AddProductCartAfterLoginResponse>>


    @POST("Shopping/CheckOUT")
    suspend fun checkout(
        @Body item: CheckoutModel
    ): Response<CheckoutResponse>


}