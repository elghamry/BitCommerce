package sa.biotic.app.retrofit_service

import android.util.Log

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Category
import sa.biotic.app.model.Offer
import sa.biotic.app.model.Product

object Repository {
    private const val BASE_URL = "http://192.129.178.42/"
    private val service = makeRetrofitService()
    var offers: MutableLiveData<MutableList<Offer>> = MutableLiveData<MutableList<Offer>>()
    var cats: MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()
    var prods: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var bunds: MutableLiveData<MutableList<BundleProds>> =
        MutableLiveData<MutableList<BundleProds>>()

    fun makeRetrofitService(): HomeService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)

            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(HomeService::class.java)

    }

    fun getOffers(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getOffers(currentpage, pageSize)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        offers.value = response.body()!!
//                        Log.d("offer", offers.size.toString())


                    } else {
//                        toast("Error: ${response.code()}")
                        Log.d("Error", response.toString())
                    }
                } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                }
            }
        }


    }

    fun getCategories(): MutableLiveData<MutableList<Category>> {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCategories()
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        cats.value = response.body()!!
//                        Log.d("offer", offers.size.toString())


                    } else {
//                        toast("Error: ${response.code()}")
                        Log.d("Error", response.toString())
                    }
                } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                }
            }
        }

        return cats
    }

    fun getAllProducts(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getAllProducts(currentpage, pageSize)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        prods.value = response.body()!!
//                        Log.d("offer", offers.size.toString())


                    } else {
//                        toast("Error: ${response.code()}")
                        Log.d("Error", response.toString())
                    }
                } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                }
            }
        }


    }


    fun getBundles(currentpage: Int, pageSize: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getBundles(currentpage, pageSize)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        bunds.value = response.body()!!
//                        Log.d("offer", offers.size.toString())


                    } else {
//                        toast("Error: ${response.code()}")
                        Log.d("Error", response.toString())
                    }
                } catch (e: HttpException) {
//                    toast("Exception ${e.message}")
                } catch (e: Throwable) {
//                    toast("Ooops: Something else went wrong")
                }
            }
        }


    }


}






