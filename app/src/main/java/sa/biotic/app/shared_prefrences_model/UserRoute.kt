package sa.biotic.app.shared_prefrences_model

import com.chibatching.kotpref.KotprefModel

object UserRoute : KotprefModel() {
    var next_step by stringPref()
    var previous_step by stringPref()
//    var lname by stringPref()
//    var image by stringPref()
//    var email by stringPref()
//    var access_token by stringPref()
//    var lang by intPref(default = 0)
//    var uid by intPref(default = 0)
//    var signed by booleanPref(default = false)
//    var rememberme by booleanPref(default = true)
//    var validation_status_number   by intPref(default = 0)
//    var device_token by stringPref()
//    val prizes by stringSetPref {
//        val set = TreeSet<String>()
//        set.add("Beginner")
//        return@stringSetPref set
//    }
}
