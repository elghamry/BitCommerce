package sa.biotic.app.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.ProductBund
import sa.biotic.app.retrofit_service.Repository

class ProdsBundsRelaViewModel : ViewModel() {

    var prods: MutableList<ProductBund> = mutableListOf<ProductBund>()


    //livedata
    var prodsLive: MutableLiveData<MutableList<ProductBund>> =
        MutableLiveData<MutableList<ProductBund>>()


    init {
        Log.i("ReviewsViewModel", "ReviewsViewModel created!")

        getProdsOfBund()


    }

    private fun getProdsOfBund() {
//        revs.add(Review(revs.size+1.toLong(),"https://avatarfiles.alphacoders.com/190/190934.jpg",5,"Girly lily"))
//        revs.add(Review(revs.size+1.toLong(),"https://imagesvc.meredithcorp.io/v3/mm/image?url=https%3A%2F%2Fewedit.files.wordpress.com%2F2019%2F05%2Fbillie-eilish.jpg&w=400&c=sc&poi=face&q=85",5,"ellie billie"))
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

        prodsLive = Repository.prodsOfBundle

    }


}