package sa.biotic.app.shared_prefrences_model

import com.chibatching.kotpref.KotprefModel

object UserInfo : KotprefModel() {
    var address by stringPref()
    var fname by stringPref()
    var lname by stringPref()
    var name by stringPref()
    var image by stringPref()
    var email by stringPref()
    var NotificationEmail by stringPref()
    var forgetPasswordEmail by stringPref()
    var mobile by stringPref()
    var access_token by stringPref()
    var lang by intPref(default = 0)
    var uid by intPref(default = 0)
    var signed by booleanPref(default = false)
    var rememberme by booleanPref(default = true)
    var validation_status_number by intPref(default = 0)
    var device_token by stringPref()
    var has_default_address by booleanPref(default = false)
//    val prizes by stringSetPref {
//        val set = TreeSet<String>()
//        set.add("Beginner")
//        return@stringSetPref set
//    }
}
