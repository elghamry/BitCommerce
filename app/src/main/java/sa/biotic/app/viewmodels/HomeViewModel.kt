package sa.biotic.app.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Category
import sa.biotic.app.model.Offer
import sa.biotic.app.model.Product

class HomeViewModel : ViewModel() {
    var offers : MutableList<Offer> = mutableListOf<Offer>()
    var cats : MutableList<Category> = mutableListOf<Category>()
    var prods : MutableList<Product> = mutableListOf<Product>()
    var bundles : MutableList<BundleProds> = mutableListOf<BundleProds>()


    //livedata
    var offersLive : MutableLiveData<MutableList<Offer>> = MutableLiveData<MutableList<Offer>>()
    var catsLive : MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()
    var prodsLive : MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var bundlesLive : MutableLiveData<MutableList<BundleProds>> = MutableLiveData<MutableList<BundleProds>>()


    init {
        Log.i("HomeViewModel", "HomeViewModel created!")
        val offer = Offer(
            "Fresh High protein salads ",
            "This is the descreption of the called offer ... !!!",
            "https://i.imgur.com/35SYrqa.png",
            "30 SR",
            "40 SR"

        )
        val offer2 = Offer(
            "Hello Offer",
            "This is the descreption of the called offer 2 ... !!!",
            "https://fitsmallbusiness.com/wp-content/uploads/2018/05/23-Best-Sales-Promotion-Ideas.png",
            "12 SR"
            ,
            "20 SR"
        )
        offers.add(offer)
        offers.add(offer2)
        offers.add(offer)
        offers.add(offer2)
        getCats()
        getProds()
        getBundles()
//        Log.d("initViw",offers.toString());
        Log.d("initViw", prodsLive.value.toString())
        offersLive.value = offers


    }

    private fun getProds() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        prods.add(
            Product(
                prods.size + 1.toLong(),
                "https://i.imgur.com/jlMo65d.png",
                "pop chips",
                "15 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        prods.add(
            Product(
                prods.size + 1.toLong(),
                "https://i.imgur.com/l1S996X.png",
                "Skinny Sauce",
                "20 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        prods.add(
            Product(
                prods.size + 1.toLong(),
                "https://i.imgur.com/x1fk5hs.png",
                "Muscle Sandwich",
                "30 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        prods.add(Product(prods.size+1.toLong(),"https://images-na.ssl-images-amazon.com/images/G/15/img16/grocery/content-grid/36251_grocery_breakfastfoods-_apr27_440x300.png","Corn Flakes Product","13 SR","good meal for bla bla oats !!i love it"))
//        prods.add(Product(prods.size+1.toLong(),"","Oat Meal2 Product","153 SR","good meal for bla bla oats !!i love it dear"))
        prods.add(Product(prods.size+1.toLong(),"https://grist.files.wordpress.com/2015/11/organic.jpg?w=1024&h=576&crop=1","organic with Rice","20 SR","good meal for bla bla oats !!i love it"))
        prods.add(Product(prods.size+1.toLong(),"https://s1.r29static.com/bin/entry/57f/277,2,1488,837/1200xbm,80/2251087/image.jpg","chips pips Vitamin","30 SR","good meal for bla bla oats !!i love it"))
        prods.add(Product(prods.size+1.toLong(),"https://www.wegmans.com/content/dam/wegmans/products/881/97881.jpg","Corn Flakes Product","13 SR","good meal for bla bla oats !!i love it"))
        prodsLive.value=prods
    }

    private fun getBundles() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://i.imgur.com/qfbErUf.png",
                "Hello best Bundle",
                "15 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://i.imgur.com/IXiUYXp.png",
                "Happy Bundles",
                "20 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://i.imgur.com/JhwqzRT.png",
                "Juicy Lucy",
                "30 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(BundleProds(bundles.size+1.toLong(),"https://everyg.com/uploads/everyg.com/products/2019/04/thumb590/irishspringbundle-15688718818166.jpg","Gargiir Flakes Bundle","13 SR","good meal for bla bla oats !!i love it"))
//        prods.add(Product(prods.size+1.toLong(),"","Oat Meal2 Product","153 SR","good meal for bla bla oats !!i love it dear"))
        bundles.add(BundleProds(bundles.size+1.toLong(),"https://www.fastandup.in/images/product-img//xcalcium-bundle-fortify-buy5-get-2-free-2-301.png.pagespeed.ic.NNX-WvN15z.png","organic Bundle","20 SR","good meal for bla bla oats !!i love it"))
        bundles.add(BundleProds(bundles.size+1.toLong(),"https://goodmorning.com.sg/wp-content/uploads/vgrains-1kg-x2-small-3-400x500.jpg","Grains pips Vitamin","30 SR","good meal for bla bla oats !!i love it"))
        bundles.add(BundleProds(bundles.size+1.toLong(),"https://www.wegmans.com/content/dam/wegmans/products/881/97881.jpg","Corn Flakes Product","13 SR","good meal for bla bla oats !!i love it"))
        bundlesLive.value=bundles
    }

    private fun getCats() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        cats.add(Category("https://i.imgur.com/FbDX9x8.png","Vega"))
        cats.add(Category("https://i.imgur.com/d2dGczv.png","Vega"))
        cats.add(Category("https://i.imgur.com/sTaaRq1.png","Vega"))
        cats.add(Category("https://i.imgur.com/K6G7rQn.png","Vega"))
        cats.add(Category("https://i.imgur.com/RPwHDqg.png","Vega"))
//        cats.add(Category("https://i.imgur.com/K6G7rQn.png","Vega"))
//        cats.add(Category("https://i.imgur.com/RPwHDqg.png","Vega"))
        catsLive.value = cats
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "HomeViewModel destroyed!")
    }
}