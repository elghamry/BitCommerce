package sa.biotic.app.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository

class OnDetailsViewModel : ViewModel() {

    var cartItems: MutableList<CartItem> = mutableListOf<CartItem>()


    //livedata
    var cartItemsLiveData: MutableLiveData<MutableList<CartItem>> =
        MutableLiveData<MutableList<CartItem>>()

    var addToCartLiveData: MutableLiveData<AddToCartResponse> =
        MutableLiveData<AddToCartResponse>()


    var totalPrice: MutableLiveData<String> = MutableLiveData<String>()

    var prodLive: MutableLiveData<Product> =
        MutableLiveData<Product>()

    var bundleLive: MutableLiveData<BundleProduct> =
        MutableLiveData<BundleProduct>()


    init {
        Log.i("ReviewsViewModel", "ReviewsViewModel created!")
        prodLive = Repository.prod
        bundleLive = Repository.bundleItem
        addToCartLiveData = Repository.addToCartResponse

        getCartItems()


    }

    private fun getCartItems() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        cartItemsLiveData = Repository.getCartLocal()


    }

    fun addCartItemOn(item: AddToCartModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.addToCart(item)


    }


    fun getTotalPrice() {
        totalPrice.value = "0"
        if (!cartItemsLiveData.value.isNullOrEmpty())
            for (i in 0..cartItemsLiveData.value!!.size - 1)
                totalPrice.value = (totalPrice.value?.toFloat()?.plus(
                    cartItemsLiveData.value?.get(i)?.price!!.toFloat() * cartItemsLiveData.value?.get(
                        i
                    )!!.quantity
                )).toString()

    }

    fun getThisProd(Id: Int) {
        Repository.getProduct(Id)
    }


    fun getThisBundle(Id: Int) {
        Repository.getBundleProductAsItem(Id)
    }


//    private fun getRevs() {
////        revs.add(Review(revs.size+1.toLong(),"https://avatarfiles.alphacoders.com/190/190934.jpg",5,"Girly lily"))
////        revs.add(Review(revs.size+1.toLong(),"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fewedit.files.wordpress.com%2F2019%2F05%2Fbillie-eilish.jpg&w=400&c=sc&poi=face&q=85",5,"ellie billie"))
//        revs.add(
//            Review(
//                revs.size.toLong(),
//                "https://besthqwallpapers.com/Uploads/6-1-2017/11935/thumb2-cara-delevingne-portrait-4k-blonde-british-actress-british-top-model.jpg",
//                3,
//                "delphane ocare"
//            )
//        )
//
//        for (i in 1..20) {
//            revs.add(
//                Review(
//                    revs.size.toLong(),
//                    "https://avatarfiles.alphacoders.com/190/190934.jpg",
//                    5,
//                    "Girly lily"
//                )
//            )
//            revs.add(
//                Review(
//                    revs.size.toLong(),
//                    "https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fewedit.files.wordpress.com%2F2019%2F05%2Fbillie-eilish.jpg&w=400&c=sc&poi=face&q=85",
//                    5,
//                    "ellie billie"
//                )
//            )
//            revs.add(
//                Review(
//                    revs.size.toLong(),
//                    "https://besthqwallpapers.com/Uploads/6-1-2017/11935/thumb2-cara-delevingne-portrait-4k-blonde-british-actress-british-top-model.jpg",
//                    3,
//                    "delphane ocare"
//                )
//            )
//            revs.add(
//                Review(
//                    revs.size.toLong(),
//                    "https://i.dlpng.com/static/png/444765_preview.png",
//                    4,
//                    "Scarlet soka"
//                )
//            )
//
//        }
//
//        revsLive.value = revs
//
//    }


}