package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LogoutModel(
    val AccessToken: String,

    val UserID: String,
    val DeviceToken: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is LogoutModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && DeviceToken == other.DeviceToken


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}