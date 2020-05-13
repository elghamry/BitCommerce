package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoginModel(
    val DeviceToken: String,

    val Email: String,
    val Password: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UserLoginModel

            && Email == other.Email

            && Password == other.Password
            && DeviceToken == other.DeviceToken


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}