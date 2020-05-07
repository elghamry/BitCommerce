package sa.biotic.app.retrofit_service

import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PartMap
import sa.biotic.app.model.*


interface UserService {

    @POST("user/Register")
    suspend fun registerUser(
        @Body user: User
    ): Response<UserRegisterationResponse>


    @POST("user/Login")
    suspend fun loginUser(
        @Body user_log: UserLoginModel
    ): Response<UserLoginModelResponse>

    @POST("user/LogOut")
    suspend fun logoutUser(
        @Body user_out: LogoutModel
    ): Response<LogoutModelResponse>

    @POST("user/GetUserAccountData")
    suspend fun getUserAccountData(
        @Body user_data: LogoutModel
    ): Response<UserAccountData>


    @POST("user/ForgetPassword")
    suspend fun forgetPassword(
        @Body user_data: ForgetPasswordModel
    ): Response<ForgetPasswordResponseModel>

    @POST("user/GetResetPwCode")
    suspend fun enterCode(
        @Body user_data: EnterCodeModel
    ): Response<ForgetPasswordResponseModel>


    @POST("user/ResetPassword")
    suspend fun resetPasswordFo(
        @Body user_data: ResetChangePassModel
    ): Response<ForgetPasswordResponseModel>


    @POST("user/UpdateUserPw")
    suspend fun updateUserPw(
        @Body user_data: ChangePasswordModel
    ): Response<ForgetPasswordResponseModel>


    @POST("user/UpdateUserAccountData")
    suspend fun updateUserAccountData(
        @Body user_data: UpdateUserAccountDataModel
    ): Response<UpdateUserAccountDataResponse>


    @POST("user/GetDeliveryAddress")
    suspend fun getDeliveryAddress(
        @Body user_data: GetDeliveryAddressModel
    ): Response<GetDeliveryAddressModelResponse>


    @POST("user/UpdateDeliveryAddress")
    suspend fun updateDeliveryAddress(
        @Body user_data: UpdateDeliveryAddressModel
    ): Response<UpdateDeiveryAddressResponseModel>

    @POST("user/VerifyAccount")
    suspend fun verifyUser(
        @Body user_data: VerifyModel
    ): Response<VerifyResponseModel>


//    @Multipart
//    @POST("user/UpdateUserImage")
//    suspend fun updateUserImage(@Part("Image") Image: MultipartBody.Part, @Part("UserID") UserID: RequestBody,@Part("AccessToken") AccessToken: RequestBody): Response<UploadImageResponse>


    @Multipart
    @POST("user/UpdateUserImage")
    suspend fun updateUserImage(@PartMap params: HashMap<String, RequestBody>): Response<UploadImageResponse>
//    @GET("general/getCategories")
//    suspend fun getCategories(): Response<MutableList<Category>>
//
//
//    @GET("general/getallprouducts")
//    suspend fun getAllProducts(
//        @Query("currentpage") currentpage: Int,
//        @Query("pageSize") pageSize: Int
//    ): Response<MutableList<Product>>
//
//    @GET("general/getBundles")
//    suspend fun getBundles(
//        @Query("currentpage") currentpage: Int,
//        @Query("pageSize") pageSize: Int
//    ): Response<MutableList<BundleProds>>
//
//
//    @GET("general/getCategoryProduct")
//    suspend fun getCategoryProducts(
//        @Query("categoryId") categoryId: Int
//    ): Response<MutableList<Product>>
//
//    @GET("general/Search")
//    suspend fun Search(
//        @Query("word") word: String,
//        @Query("pageSize") pageSize: Int,
//        @Query("currentpage") currentpage: Int,
//        @Query("lang") lang: String
//    ): Response<MutableList<SearchItem>>
//
//
//    @GET("general/getBundleProduct")
//    suspend fun getBundleProduct(
//        @Query("BundleId") BundleId: Int
//    ): Response<ProductsOfBundle>


}