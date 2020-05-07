package sa.biotic.app.retrofit_service

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.RequestBody
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import sa.biotic.app.model.*
import sa.biotic.app.shared_prefrences_model.UserInfo
import java.io.IOException
import java.net.ConnectException


object Repository {
    private const val BASE_URL = "http://192.129.178.42/API/"
    private val service = makeRetrofitService()
    private val serviceUser = makeRetrofitUserService()
    private val serviceShopping = makeRetrofitShoppingService()

    lateinit var callBackResponse: MutableLiveData<Boolean>


    var offers: MutableLiveData<MutableList<Offer>> = MutableLiveData<MutableList<Offer>>()
    var cats: MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()
    var prods: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var prodsHome: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var offerProds: MutableLiveData<MutableList<OfferProduct>> =
        MutableLiveData<MutableList<OfferProduct>>()
    var searchItems: MutableLiveData<MutableList<SearchItem>> =
        MutableLiveData<MutableList<SearchItem>>()
    var prodsOfBundle: MutableLiveData<MutableList<ProductBund>> =
        MutableLiveData<MutableList<ProductBund>>()
    var prod: MutableLiveData<Product> =
        MutableLiveData<Product>()

    var bundleItem: MutableLiveData<BundleProduct> =
        MutableLiveData<BundleProduct>()


    var cats_prods: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var bunds: MutableLiveData<MutableList<BundleProduct>> =
        MutableLiveData<MutableList<BundleProduct>>()
    var bundsHome: MutableLiveData<MutableList<BundleProduct>> =
        MutableLiveData<MutableList<BundleProduct>>()
    var cartItems: MutableList<CartItem> = mutableListOf<CartItem>()
    var cartLocalItems: MutableLiveData<MutableList<CartItem>> =
        MutableLiveData<MutableList<CartItem>>()


    //LoginAndRegisteration
    var login_response: MutableLiveData<UserLoginModelResponse> =
        MutableLiveData<UserLoginModelResponse>()
    var registeration_response: MutableLiveData<UserRegisterationResponse> =
        MutableLiveData<UserRegisterationResponse>()
    var logout_response: MutableLiveData<LogoutModelResponse> =
        MutableLiveData<LogoutModelResponse>()

    var get_account_data_response: MutableLiveData<UserAccountData> =
        MutableLiveData<UserAccountData>()
    var uploadImageResponse: MutableLiveData<UploadImageResponse> =
        MutableLiveData<UploadImageResponse>()
    var forgetPasswordResponse: MutableLiveData<ForgetPasswordResponseModel> =
        MutableLiveData<ForgetPasswordResponseModel>()
    var enterCodeResponse: MutableLiveData<ForgetPasswordResponseModel> =
        MutableLiveData<ForgetPasswordResponseModel>()
    var resetChanPassResponse: MutableLiveData<ForgetPasswordResponseModel> =
        MutableLiveData<ForgetPasswordResponseModel>()

    var changePasswordResponse: MutableLiveData<ForgetPasswordResponseModel> =
        MutableLiveData<ForgetPasswordResponseModel>()

    var getDeliverAddressResponse: MutableLiveData<GetDeliveryAddressModelResponse> =
        MutableLiveData<GetDeliveryAddressModelResponse>()

    var updateDeliverAddressResponse: MutableLiveData<UpdateDeiveryAddressResponseModel> =
        MutableLiveData<UpdateDeiveryAddressResponseModel>()

    var verifyUserResponse: MutableLiveData<VerifyResponseModel> =
        MutableLiveData<VerifyResponseModel>()

    var updateUserInfo: MutableLiveData<UpdateUserAccountDataResponse> =
        MutableLiveData<UpdateUserAccountDataResponse>()


    //shopping

    var addToCartResponse: MutableLiveData<AddToCartResponse> =
        MutableLiveData<AddToCartResponse>()

    var updateQuantityResponse: MutableLiveData<UpdateCartItemQuantityResponse> =
        MutableLiveData<UpdateCartItemQuantityResponse>()

    var deleteCartItemResponse: MutableLiveData<DeleteCartItemResponse> =
        MutableLiveData<DeleteCartItemResponse>()

    var getCartResponse: MutableLiveData<CartResponse> =
        MutableLiveData<CartResponse>()
    var checkPromoResponse: MutableLiveData<CheckPromoResponse> =
        MutableLiveData<CheckPromoResponse>()
    var prodsCartAfterLogin: MutableLiveData<MutableList<AddProductCartAfterLoginResponse>> =
        MutableLiveData<MutableList<AddProductCartAfterLoginResponse>>()

    var getCartCount: MutableLiveData<Int> =
        MutableLiveData<Int>()


    //local cart is here

    var prodsLocal: MutableList<String> = mutableListOf<String>()
    var isbundLocal: MutableList<String> = mutableListOf<String>()
    var offeridsLocal: MutableList<String> = mutableListOf<String>()
    var stockquanLocal: MutableList<String> = mutableListOf<String>()
    var quantityLocal: MutableList<String> = mutableListOf<String>()
    var priceLocal: MutableList<String> = mutableListOf<String>()
    var discountLocal: MutableList<String> = mutableListOf<String>()


    var ProductIDsToString: String = ""
    var PricesToString: String = ""
    var isbundlesToString: String = ""
    var OfferIDsToString: String = ""
    var DiscountsToString: String = ""
    var StocksQuantityToString: String = ""
    var QuantityToString: String = ""


//    lateinit var prodsLocal: String
//    lateinit  var isbundLocal: String
//    lateinit var offeridsLocal: String
//    lateinit var stockquanLocal: String
//    lateinit var quantityLocal: String
//    lateinit var priceLocal: String
//    lateinit var discountLocal: String







    fun makeRetrofitService(): HomeService {
        callBackResponse = MutableLiveData<Boolean>()
        callBackResponse.value = false
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(HomeService::class.java)

    }

    fun makeRetrofitUserService(): UserService {
        callBackResponse = MutableLiveData<Boolean>()
        callBackResponse.value = false
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(UserService::class.java)

    }

    fun makeRetrofitShoppingService(): ShoppingService {
        callBackResponse = MutableLiveData<Boolean>()
        callBackResponse.value = false
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(ShoppingService::class.java)

    }


    fun registerUser(user: User) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.registerUser(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            registeration_response.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun updateUserImage(
        Image: HashMap<String, RequestBody>
    ) {


        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response = serviceUser.updateUserImage(Image)

                withContext(Dispatchers.Main) {
                    try {

                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!

                            Log.d("userResp", response.body().toString())
//                            registeration_response.value =
                            uploadImageResponse.value = response.body()

                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("ErrorHello", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {
                Log.d("userResp", e.toString())
            }
        }


    }

    fun logoutUser(user: LogoutModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.logoutUser(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            logout_response.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun getDeliveryAddr(user: GetDeliveryAddressModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.getDeliveryAddress(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            getDeliverAddressResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun updateDeliveryAddr(user: UpdateDeliveryAddressModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.updateDeliveryAddress(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            updateDeliverAddressResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }

    fun forgetPassword(user: ForgetPasswordModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.forgetPassword(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            forgetPasswordResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }

    fun verifyUser(user: VerifyModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.verifyUser(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            verifyUserResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun enterCode(user: EnterCodeModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.enterCode(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            enterCodeResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }

    fun changePassword(user: ChangePasswordModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.updateUserPw(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            changePasswordResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun resetPasswordFo(user: ResetChangePassModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.resetPasswordFo(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            resetChanPassResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }

    fun getUserAccountData(user: LogoutModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.getUserAccountData(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            get_account_data_response.value = response.body()
                            UserInfo.email = response.body()!!.UserEmail
                            UserInfo.NotificationEmail = response.body()!!.NotificationEmail
                            UserInfo.mobile = response.body()!!.MobileNumber

                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun updateUserAccountData(user: UpdateUserAccountDataModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.updateUserAccountData(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            updateUserInfo.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun loginUser(user: UserLoginModel) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceUser.loginUser(user)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!
                            login_response.value = response.body()
                            callBackResponse.value = true


                            Log.d("userResp", response.body().toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false


                    } catch (e: HttpException) {
//                    Toast.makeText(this@MainAc, "Exception ${e.message}", Toast.LENGTH_LONG).show()
                        callBackResponse.value = false

                    } catch (e: IOException) {
//    callBackResponse.value=false
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                    } catch (e: ConnectException) {
                        callBackResponse.value = false
                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }


            } catch (e: Exception) {

            }


        }


    }


    fun getOffers(currentpage: Int, pageSize: Int) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getOffers(currentpage, pageSize)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            offers.value = response.body()!!
                            callBackResponse.value = true
//                        Log.d("offer", offers.size.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false
                    } catch (e: Exception) {
                        callBackResponse.value = false

                    }
                }
            } catch (e: Exception) {

            }
        }


    }

    fun getCategories(): MutableLiveData<MutableList<Category>> {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response =


                    service.getCategories()
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            cats.value = response.body()!!
//                        Log.d("offer", offers.size.toString())
                            callBackResponse.value = true

                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }


                }
            } catch (e: Exception) {

//                    callBackResponse.value=false

            }


        }





        return cats
    }

    fun getAllProducts(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = service.getAllProducts(currentpage, pageSize)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            prods.value = response.body()!!
//                        Log.d("offer", offers.size.toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
                        callBackResponse.value = false
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getBundles(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getBundles(currentpage, pageSize)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            bunds.value = response.body()!!
                            callBackResponse.value = true
                            Log.d("bundle", bunds.value!!.size.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false
                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }

    fun getHomeBundles(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getHomeBundles(currentpage, pageSize)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            bundsHome.value = response.body()!!
                            callBackResponse.value = true
                            Log.d("bundle", bunds.value!!.size.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false
                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getCategoryProducts(categoryId: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getCategoryProducts(categoryId)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            cats_prods.value = response.body()!!
                            callBackResponse.value = true
                            Log.d("category", cats_prods.value.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getHomeProducts(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getHomeprouducts(currentpage, pageSize)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            cats_prods.value = response.body()!!
                            callBackResponse.value = true
                            Log.d("category", cats_prods.value.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getBundleProduct(BundleId: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getBundleProduct(BundleId)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


                            prodsOfBundle.value = response.body()!!.Products

//                            var tempBund : BundleProduct = BundleProduct(
//                                response.body()!!.BundleDescription_Ar,
//                                response.body()!!.BundleDescription_En,
//                                response.body()!!.BundleID,
//                                response.body()!!.BundleImage,
//                                response.body()!!.BundleName_Ar,
//                                response.body()!!.BundleName_En,
//                                response.body()!!.BundlePrice,
//                                response.body()!!.BundleReview,
//                                response.body()!!.BundletStockQuantity
//                            )
//                            bundleItem.value = tempBund
//                            callBackResponse.value = true
//
//                            Log.d("bundleCalled", bundleItem.value.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getBundleProductAsItem(BundleId: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getBundleProduct(BundleId)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


                            prodsOfBundle.value = response.body()!!.Products

                            var tempBund: BundleProduct = BundleProduct(
                                response.body()!!.BundleDescription_Ar,
                                response.body()!!.BundleDescription_En,
                                response.body()!!.BundleID,
                                response.body()!!.BundleImage,
                                response.body()!!.BundleName_Ar,
                                response.body()!!.BundleName_En,
                                response.body()!!.BundlePrice,
                                response.body()!!.BundleReview,
                                response.body()!!.BundletStockQuantity
                            )
                            bundleItem.value = tempBund
                            callBackResponse.value = true

                            Log.d("bundleCalled", bundleItem.value.toString())


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getProduct(productid: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getProduct(productid)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("ProdCalled", response.body()?.ProductOfferDicountValue.toString())
                            prod.value = response.body()!!
                            Log.d("ProdCalled", response.body()?.ProductID.toString())




                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }


    fun getOfferList(offerId: Int) {

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.getOfferList(offerId)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            offerProds.value = response.body()
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }

        }


    }

    fun getSearchItems(word: String, pageSize: Int, currentpage: Int, lang: String) {

        CoroutineScope(Dispatchers.IO).launch {

            try {

                val response = service.Search(word, pageSize, currentpage, lang)

                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                            searchItems.value?.clear()
                            searchItems.value = response.body()!!

                            Log.d("searchis", searchItems.value.toString())
                            callBackResponse.value = true

                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())
                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: IOException) {
//
//                    UtilityMethods.hideView(loading_view)
//                    UtilityMethods.showView(network_error_msg)
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {
                        callBackResponse.value = false

                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun addItemCartLocal(item: CartItem) {


        var innerItem: CartItem? = cartItems.find {
            it.typeId == item.typeId &&
                    it.type == item.type
        }

        if (innerItem == null) {
            cartItems.add(item)
            prodsLocal.add(item.typeId.toString())
//            if(prodsLocal.isBlank())
//            prodsLocal.plus("")


            if (item.type == "bundle") {
                isbundLocal.add(1.toString())
            } else {
                isbundLocal.add(0.toString())
            }
            if (item.type == "productOff") {
                offeridsLocal.add(item.offerId.toString())
            } else {
                offeridsLocal.add(0.toString())
            }

            stockquanLocal.add(item.stockQuantity.toString())
            quantityLocal.add(item.quantity.toString())
            priceLocal.add(item.price)
            discountLocal.add(item.discount)

        } else {
            var position: Int = cartItems.indexOf(innerItem)






            if (item.type == "productOff") {
                offeridsLocal[position] = item.offerId.toString()
            } else {
                offeridsLocal[position] = 0.toString()
            }

            stockquanLocal[position] = item.stockQuantity.toString()
            quantityLocal[position] = item.quantity.toString()
            priceLocal[position] = item.price
            discountLocal[position] = item.discount


        }



        cartLocalItems.value = cartItems
//
        Log.d("nologinchecker", prodsLocal.toString())
        Log.d("nologinchecker", isbundLocal.toString())
        Log.d("nologinchecker", offeridsLocal.toString())
        Log.d("nologinchecker", stockquanLocal.toString())
        Log.d("nologinchecker", quantityLocal.toString())
        Log.d("nologinchecker", priceLocal.toString())
        Log.d("nologinchecker", discountLocal.toString())




        Log.d("nofitem", cartItems.toString())

    }


    fun deletItemCartLocal(item: CartItem?) {


        var innerItem: CartItem? = cartItems.find {
            it.typeId == item?.typeId &&
                    it.type == item.type
        }

        if (innerItem == null) {
//            cartItems.add(item)
//            prodsLocal.add(item.typeId.toString())
////            if(prodsLocal.isBlank())
////            prodsLocal.plus("")
//
//
//            if(item.type.equals("bundle"))
//            {
//                isbundLocal.add(1.toString())
//            }
//            else{
//                isbundLocal.add(0.toString())
//            }
//            if(item.type.equals("productOff")){
//                offeridsLocal.add(item.offerId.toString())
//            }
//            else
//            {
//                offeridsLocal.add(0.toString())
//            }
//
//            stockquanLocal.add(item.stockQuantity.toString())
//            quantityLocal.add(item.quantity.toString())
//            priceLocal.add(item.price)
//            discountLocal.add(item.discount)

        } else {
            var position: Int = cartItems.indexOf(innerItem)

            Log.d("itemPos", position.toString())


            prodsLocal.removeAt(position)
            isbundLocal.removeAt(position)




            offeridsLocal.removeAt(position)


            stockquanLocal.removeAt(position)
            quantityLocal.removeAt(position)
            priceLocal.removeAt(position)
            discountLocal.removeAt(position)


        }


        cartItems.remove(item)

        cartLocalItems.value = cartItems

        Repository.getCartDetailsNoLogin(
            Repository.prodsLocal,
            Repository.priceLocal,
            Repository.isbundLocal,
            Repository.offeridsLocal,
            Repository.discountLocal,
            Repository.stockquanLocal,
            Repository.quantityLocal
        )


    }

    fun getCartLocal(): MutableLiveData<MutableList<CartItem>> {

        return cartLocalItems
    }


    fun addToCart(item: AddToCartModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.addToCart(item)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            addToCartResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true
                            if (UserInfo.signed) {
                                Repository.getCartDetails(
                                    GetCartDetailsModel(
                                        UserInfo.uid,
                                        UserInfo.access_token,
                                        UserInfo.device_token
                                    )
                                )

                            } else {
                                Repository.getCartDetails(
                                    GetCartDetailsModel(
                                        0,
                                        "rr",
                                        UserInfo.device_token
                                    )
                                )


                            }


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun getCartDetails(item: GetCartDetailsModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.getCartDetails(item)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            getCartResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true
                            getCartCountFn()


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {
                Log.d("userResp", e.toString())
            }
        }


    }


    fun updateCartItemQuantity(item: UpdateCartItemQuantityModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.updateCartItemQuantity(item)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            updateQuantityResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }
    }


    fun deleteCartItem(item: DeleteCartItemModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.deleteCartItem(item)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            deleteCartItemResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun getCartDetailsNoLogin(
        ProductIDs: MutableList<String>,
        Prices: MutableList<String>,
        isbundles: MutableList<String>,
        OfferIDs: MutableList<String>,
        Discounts: MutableList<String>,
        StocksQuantity: MutableList<String>,
        Quantity: MutableList<String>
    ) {


        for (i in 1..ProductIDs.size) {

            if (i == 1) {
                ProductIDsToString = ProductIDs[i - 1]
                PricesToString = (Prices[i - 1])
                isbundlesToString = (isbundles[i - 1])
                OfferIDsToString = (OfferIDs[i - 1])
                DiscountsToString = (Discounts[i - 1])
                StocksQuantityToString = (StocksQuantity[i - 1])
                QuantityToString = (Quantity[i - 1])
            } else {

                ProductIDsToString = ProductIDsToString + (",") + (ProductIDs[i - 1])
                PricesToString = PricesToString + (",") + (Prices[i - 1])
                isbundlesToString = isbundlesToString + (",") + (isbundles[i - 1])
                OfferIDsToString = OfferIDsToString + (",") + (OfferIDs[i - 1])
                DiscountsToString = DiscountsToString + (",") + (Discounts[i - 1])
                StocksQuantityToString = StocksQuantityToString + (",") + (StocksQuantity[i - 1])
                QuantityToString = QuantityToString + (",") + (Quantity[i - 1])
            }

        }


//        Log.d("nologinchecker", ProductIDsToString.toString())
//        Log.d("nologinchecker", PricesToString.toString())
//        Log.d("nologinchecker", isbundlesToString.toString())
//        Log.d("nologinchecker", OfferIDsToString.toString())
//        Log.d("nologinchecker", DiscountsToString.toString())
//        Log.d("nologinchecker", StocksQuantityToString.toString())
//        Log.d("nologinchecker", QuantityToString.toString())


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.getCartDetailsNoLogin(
                    ProductIDsToString.toString(),
                    PricesToString.toString(),
                    isbundlesToString.toString(),
                    OfferIDsToString.toString(),
                    DiscountsToString.toString(),
                    StocksQuantityToString.toString(),
                    QuantityToString.toString()
                )

                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            getCartResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true

                            getCartCountFn()


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {


                Log.d("Erroris", e.toString())

            }
        }


    }


//    fun updateCartQuantityLocal(item : CartItem){
//
//
//
//
//
//
//
//    }

    fun addToCartAfterLogin(item: AddProductCartAfterLoginModel) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.addProductCartAfterLogin(item)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            prodsCartAfterLogin.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true

                            cartItems.clear()
                            cartLocalItems.value = cartItems
                            priceLocal.clear()
                            prodsLocal.clear()
                            quantityLocal.clear()
                            isbundLocal.clear()
                            offeridsLocal.clear()
                            discountLocal.clear()
                            stockquanLocal.clear()


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun checkPromo(item: String) {


        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = serviceShopping.checkPromoCode(item)
                withContext(Dispatchers.Main) {
                    try {
                        if (response.isSuccessful) {
                            //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
//                        offers.value = response.body()!!


                            checkPromoResponse.value = response.body()
                            Log.d("userResp", response.body().toString())
                            callBackResponse.value = true

                            cartItems.clear()
                            cartLocalItems.value = cartItems
                            priceLocal.clear()
                            prodsLocal.clear()
                            quantityLocal.clear()
                            isbundLocal.clear()
                            offeridsLocal.clear()
                            discountLocal.clear()
                            stockquanLocal.clear()


                        } else {
//                        toast("Error: ${response.code()}")
                            Log.d("Error", response.toString())

                        }
                    } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                        callBackResponse.value = false
                    } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                        callBackResponse.value = false
                    } catch (e: ConnectException) {
                        callBackResponse.value = false

                    } catch (e: Exception) {

                        callBackResponse.value = false
                    }
                }
            } catch (e: Exception) {

            }
        }


    }


    fun getCartCountFn() {
        var count = 0
        for (i in 1..getCartResponse.value!!.cartbundles.size) {
            count = count + getCartResponse.value!!.cartbundles[i - 1].CartQuantity
        }
        for (i in 1..getCartResponse.value!!.cartproduct.size) {
            count = count + getCartResponse.value!!.cartproduct[i - 1].CartQuantity
        }

        getCartCount.value = count
    }


}






















