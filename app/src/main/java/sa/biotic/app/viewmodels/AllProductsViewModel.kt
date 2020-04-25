package sa.biotic.app.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Category
import sa.biotic.app.model.Offer
import sa.biotic.app.model.Product

class AllProductsViewModel : ViewModel() {
    var offers: MutableList<Offer> = mutableListOf<Offer>()
    var cats: MutableList<Category> = mutableListOf<Category>()
    var prods: MutableList<Product> = mutableListOf<Product>()
    var bundles: MutableList<BundleProds> = mutableListOf<BundleProds>()


    //livedata
    var offersLive: MutableLiveData<MutableList<Offer>> = MutableLiveData<MutableList<Offer>>()
    var catsLive: MutableLiveData<MutableList<Category>> = MutableLiveData<MutableList<Category>>()
    var prodsLive: MutableLiveData<MutableList<Product>> = MutableLiveData<MutableList<Product>>()
    var bundlesLive: MutableLiveData<MutableList<BundleProds>> =
        MutableLiveData<MutableList<BundleProds>>()


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
        getCats()
        getProds()
        getBundles()
//        Log.d("initViw",offers.toString());
        Log.d("initViw", prodsLive.value.toString())
        offersLive.value = offers


    }

    private fun getProds() {

//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        for (i in 1..20) {

            prods.add(
                Product(
                    prods.size + 1.toLong(),
                    "https://i.imgur.com/hHJMztw.png",
                    "Rooky Road",
                    "15 SR",
                    "good meal for bla bla oats !!i love it"
                )
            )
            prods.add(
                Product(
                    prods.size + 1.toLong(),
                    "https://i.imgur.com/Z2cjwok.png",
                    "Vega Chips",
                    "20 SR",
                    "good meal for bla bla oats !!i love it"
                )
            )
            prods.add(
                Product(
                    prods.size + 1.toLong(),
                    "https://www.nowfoods.com/sites/default/files/styles/rbase-1440/public/fiber-2x.jpg?itok=0wDNDFqD&timestamp=1499351656",
                    "Multi Vitamin",
                    "30 SR",
                    "good meal for bla bla oats !!i love it"
                )
            )
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
        }

        prodsLive.value = prods
    }

    private fun getBundles() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://cdn.shopify.com/s/files/1/0126/1214/1115/products/Paleo-Bundle_570x.png?v=1555949854",
                "Hello best Bundle",
                "15 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://www.duogreen.com/uploaded/201611161402165822187.jpg",
                "Happy Bundles",
                "20 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://cdn.shopify.com/s/files/1/0053/9952/products/mcp-flavors-strawberry-lemonade-bottle_FB_31594028-8061-492a-b206-22e8191d3140_1200x.jpg?v=1564771748",
                "Juicy Lucy",
                "30 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://everyg.com/uploads/everyg.com/products/2019/04/thumb590/irishspringbundle-15688718818166.jpg",
                "Gargiir Flakes Bundle",
                "13 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
//        prods.add(Product(prods.size+1.toLong(),"","Oat Meal2 Product","153 SR","good meal for bla bla oats !!i love it dear"))
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://www.fastandup.in/images/product-img//xcalcium-bundle-fortify-buy5-get-2-free-2-301.png.pagespeed.ic.NNX-WvN15z.png",
                "organic Bundle",
                "20 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://goodmorning.com.sg/wp-content/uploads/vgrains-1kg-x2-small-3-400x500.jpg",
                "Grains pips Vitamin",
                "30 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundles.add(
            BundleProds(
                bundles.size + 1.toLong(),
                "https://www.wegmans.com/content/dam/wegmans/products/881/97881.jpg",
                "Corn Flakes Product",
                "13 SR",
                "good meal for bla bla oats !!i love it"
            )
        )
        bundlesLive.value = bundles
    }

    private fun getCats() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        cats.add(Category("https://i.imgur.com/FbDX9x8.png", "Vega"))
        cats.add(Category("https://i.imgur.com/d2dGczv.png", "Vega"))
        cats.add(Category("https://i.imgur.com/sTaaRq1.png", "Vega"))
        cats.add(Category("https://i.imgur.com/K6G7rQn.png", "Vega"))
        cats.add(Category("https://i.imgur.com/RPwHDqg.png", "Vega"))
//        cats.add(Category("https://i.imgur.com/K6G7rQn.png","Vega"))
//        cats.add(Category("https://i.imgur.com/RPwHDqg.png","Vega"))
        catsLive.value = cats
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("HomeViewModel", "HomeViewModel destroyed!")
    }
}