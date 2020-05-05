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
import sa.biotic.app.model.*

object Repository {
    private const val BASE_URL = "http://192.129.178.42:1406/"
    private val service = makeRetrofitService()
    var offers: MutableLiveData<MutableList<Offer>> = MutableLiveData<MutableList<Offer>>()
    var cats: MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()
    var prods: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var searchItems: MutableLiveData<MutableList<SearchItem>> =
        MutableLiveData<MutableList<SearchItem>>()
    var prodsOfBundle: MutableLiveData<MutableList<ProductBund>> =
        MutableLiveData<MutableList<ProductBund>>()
    var cats_prods: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var bunds: MutableLiveData<MutableList<BundleProds>> =
        MutableLiveData<MutableList<BundleProds>>()
    var cartItems: MutableList<CartItem> = mutableListOf<CartItem>()
    var cartLocalItems: MutableLiveData<MutableList<CartItem>> =
        MutableLiveData<MutableList<CartItem>>()

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


    fun getCategoryProducts(categoryId: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getCategoryProducts(categoryId)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        cats_prods.value = response.body()!!
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


    fun getBundleProduct(BundleId: Int) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getBundleProduct(BundleId)
            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        prodsOfBundle.value = response.body()!!.Products


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

    fun getSearchItems(word: String, pageSize: Int, currentpage: Int, lang: String) {

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.Search(word, pageSize, currentpage, lang)

            withContext(Dispatchers.Main) {
                try {
                    if (response.isSuccessful) {
                        //Do something with response e.g show to the UI.


//                        Log.d("OfferCalled", response.body()?.get(0)?.OfferDescription_Ar.toString())
                        searchItems.value?.clear()
                        searchItems.value = response.body()!!

                        Log.d("searchis", searchItems.value.toString())


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


    fun addItemCartLocal(item: CartItem) {


        cartItems.add(item)

        cartLocalItems.value = cartItems


        Log.d("nofitem", cartItems.toString())

    }


    fun deletItemCartLocal(item: CartItem) {

        cartItems.remove(item)

        cartLocalItems.value = cartItems

    }

    fun getCartLocal(): MutableLiveData<MutableList<CartItem>> {

        return cartLocalItems
    }





}






