package sa.biotic.app.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.LogoutModel
import sa.biotic.app.model.UserAccountData
import sa.biotic.app.model.UserLoginModel
import sa.biotic.app.model.UserLoginModelResponse
import sa.biotic.app.retrofit_service.Repository

class LoginViewModel : ViewModel() {


    //livedata
    var userLoginResponseLiveData: MutableLiveData<UserLoginModelResponse> =
        MutableLiveData<UserLoginModelResponse>()
    //livedata
    var userAccountDataResponseLiveData: MutableLiveData<UserAccountData> =
        MutableLiveData<UserAccountData>()


    init {
        Log.i("ReviewsViewModel", "ReviewsViewModel created!")

        userLoginResponseLiveData = Repository.login_response
        userAccountDataResponseLiveData = Repository.get_account_data_response


    }

    fun loginUser(user: UserLoginModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.loginUser(user)

    }


    fun getUserAccountData(user: LogoutModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.getUserAccountData(user)

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