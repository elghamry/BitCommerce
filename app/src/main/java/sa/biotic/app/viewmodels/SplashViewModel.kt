package sa.biotic.app.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.SearchItem
import sa.biotic.app.retrofit_service.Repository

class SplashViewModel : ViewModel() {


    //livedata
//    var offersLive: MutableLiveData<MutableList<Offer>> = MutableLiveData<MutableList<Offer>>()
//    var catsLive: MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()
    var searchItemsLive: MutableLiveData<MutableList<SearchItem>> =
        MutableLiveData<MutableList<SearchItem>>()
//    var bundlesLive: MutableLiveData<MutableList<BundleProds>> =
//        MutableLiveData<MutableList<BundleProds>>()


    init {
        Log.i("HomeViewModel", "HomeViewModel created!")
//        val offer = Offer(
//            "Hello Offer ",
//            "This is the descreption of the called offer ... !!!",
//            "https://previews.123rf.com/images/vectorgift/vectorgift1601/vectorgift160100019/50937688-super-sale-shining-banner-on-colorful-background-geometric-design-super-sale-and-special-offer-50-of.jpg",
//        )
//        val offer2 = Offer(
//            "Hello Offer 2",
//            "This is the descreption of the called offer 2 ... !!!",
//            "https://fitsmallbusiness.com/wp-content/uploads/2018/05/23-Best-Sales-Promotion-Ideas.png",
//        )
//        offers.add(offer)
//        offers.add(offer2)
//        offers.add(offer)
//        offers.add(offer2)
//        getCats()
//        getProds()
//        getBundles()
////        Log.d("initViw",offers.toString());
//
//        offersLive.value
        searchItemsLive = Repository.searchItems


    }

    fun getSearchItems(word: String, pageSize: Int, currentpage: Int, lang: String) {

        Repository.getSearchItems(word, pageSize, currentpage, lang)

    }


}