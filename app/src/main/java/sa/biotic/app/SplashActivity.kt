package sa.biotic.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.jai.rxsplashscreen2.RxSplashScreen
import dev.jai.rxsplashscreen2.RxSplashScreenInteraction
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RxSplashScreen.Builder(this)

            .setSplashScreen(R.layout.activity_splash_modified, 2300, TimeUnit.MILLISECONDS)
//            .setFirstScreen(R.layout.activity_login)
            .navigate(object : RxSplashScreenInteraction {
                override fun navigateToLoginScreen() {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun navigateToHomeScreen(context: Context) {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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