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


    @POST("user/GetProfileAddress")
    suspend fun getProfileAddress(
        @Body user_data: GetProfileAddress
    ): Response<GetProfileAddressResponse>


    @POST("user/UpdateProfileAddress")
    suspend fun updateProfileAddress(
        @Body user_data: UpdateProfileAddressModel
    ): Response<UpdateProfileAddressResponse>

    @POST("user/VerifyAccount")
    suspend fun verifyUser(
        @Body user_data: VerifyModel
    ): Response<VerifyResponseModel>


    @POST("user/GetUserOrders")
    suspend fun getUserOrders(
        @Body user_data: OrderAfterConfModel
    ): Response<MutableList<OrderAfterConfResponse>>


    @POST("user/Help")
    suspend fun help(
        @Body user_data: ContactModel
    ): Response<ContactlResponse>


    @POST("user/ContactUs")
    suspend fun contactUs(
        @Body user_data: ContactModel
    ): Response<ContactlResponse>


    @POST("user/GetOrder")
    suspend fun getOrderInfo(
        @Body user_data: GetOrderModel
    ): Response<OrderInfoResponse>

    @POST("user/GetOrderDetailsForRating")
    suspend fun getOrderDetailsForRating(
        @Body user_data: OrderRatingModel
    ): Response<OrderRatingResponse>


    @POST("user/GetPostPonedOrderDetailsForRating")
    suspend fun getOrderPostboDetailsForRating(
        @Body user_data: OrderRatingPostboModel
    ): Response<OrderRatingPostboResponse>

    @POST("user/ItemRating")
    suspend fun setItemRating(
        @Body user_data: ItemRatingToSendModel
    ): Response<ItemRatingToSendResponse>

    @POST("user/GetOrderLogs")
    suspend fun getOrderLogs(
        @Body user_data: GetOrderLlogsModel
    ): Response<GetOrderLogsResponse>















    @Multipart
    @POST("user/UpdateUserImage")
    suspend fun updateUserImage(@PartMap params: HashMap<String, RequestBody>): Response<UploadImageResponse>


}