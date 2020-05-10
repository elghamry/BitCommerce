package sa.biotic.app

import android.app.Application
import android.util.Log
import android.widget.Toast
import com.chibatching.kotpref.Kotpref
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import zerobranch.androidremotedebugger.AndroidRemoteDebugger

class MyApp : Application() {

    companion object {
        const val CONSTANT = 12
//        lateinit var typeface: Typeface
    }
    init {


    }

    override fun onCreate() {
        super.onCreate()
//        typeface = Typeface.createFromAsset(assets, "fonts/myFont.ttf")
        Kotpref.init(this)
//        AndroidRemoteDebugger.init(this)

//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.d("checkTok", "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//
//                // Log and toast
////                val msg = getString(R.string.msg_token_fmt, token)
//                Log.d("checkTok","welc token " +token)
//                Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
//            })
    }

}