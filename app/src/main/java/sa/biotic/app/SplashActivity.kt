package sa.biotic.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.jai.rxsplashscreen2.RxSplashScreen
import dev.jai.rxsplashscreen2.RxSplashScreenInteraction
import sa.biotic.app.model.User
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.viewmodels.SplashViewModel
import java.util.concurrent.TimeUnit


class SplashActivity : AppCompatActivity() {
    private lateinit var viewModel: SplashViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        viewModel = ViewModelProviders.of(this).get(SplashViewModel::class.java)
        var user = User(
            FirstName = "testFirst",
            LastName = "teslast",
            Email = "abdelrahman@flexible.sa",
            Mobile = "512345678"
            ,
            confirmedPW = "Abc@1234",
            Password = "Abc@1234"
        )

//

//        UserInfo.clear()
//        UserRoute.clear()
//


        Repository.getOffers(1, 200)
        Repository.getBundles(1, 200)
        Repository.getHomeBundles(1, 6)
        Repository.getAllProducts(1, 200)
        Repository.getCategories()



        UserInfo.promo = ""

        //This method will use for fetching Token
//        Thread(Runnable {
//            try {
//                Log.d("FCMToken", FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM"))
//                UserInfo.device_token=  FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID), "FCM").toString()
//
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//        }).start()

//        Repository.registerUser(user)
//        Repository.loginUser(UserLoginModel("APA91bFoi3lMMre9G3XzR1LrF4ZT82_15MsMdEICogXSLB8-MrdkRuRQFwNI5u8Dh0cI90ABD3BOKnxkEla8cGdisbDHl5cVIkZah5QUhSAxzx4Roa7b4xy9tvx9iNSYw-eXBYYd8k1XKf8Q_Qq1X9-x-U-Y79vdPqr",
//            "abdelrahman@flexible.sa","Abc@1234"))
//        CheckNetworkConnectionHelper
//            .getInstance()
//            .registerNetworkChangeListener(object : StopReceiveDisconnectedListener() {
//                override fun onDisconnected() { //Do your task on Network Disconnected!
////                    Log.e(FragmentActivity.TAG, "onDisconnected")
//                    Toast.makeText(this@SplashActivity,"Check Your Connection !",Toast.LENGTH_LONG).show()
//                }
//
//                override fun onNetworkConnected() { //Do your task on Network Connected!
////                    Log.e(FragmentActivity.TAG, "onConnected")
//                    Repository.getCategories()
////                    Repository.callBackResponse.observe(this@SplashActivity, Observer { callBack ->
////                            //            binding.wordText.text = newWord
////                        Log.d("callBackRes",callBack.toString())
////                        if(callBack)
////                        {
////
////                        }
////
////
////
////                        })
//
//                }
//
//                override fun getContext(): Context {
//                    return this@SplashActivity
//                }
//            })
        RxSplashScreen.Builder(this)
//2300
            .setSplashScreen(R.layout.activity_splash_modified, 2300, TimeUnit.MILLISECONDS)
//            .setFirstScreen(R.layout.activity_login)
            .navigate(object : RxSplashScreenInteraction {
                override fun navigateToLoginScreen() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun navigateToHomeScreen(context: Context) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    UserRoute.next_step="home"
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }


//                override fun navigateToSecondScreen(context: Context) {
//                    val navigateToCitizen = Intent(context, MainActivity::class.java)
//                    startActivity(navigateToCitizen)
//                    finish()
//                }
            })

            .setAuthenticationCheckValue(true)
            .splash()


    }
}