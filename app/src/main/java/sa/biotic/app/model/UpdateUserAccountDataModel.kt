package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateUserAccountDataModel(
    val AccessToken: String,

    val UserID: String,
    val Email: String,
    val Phone: String,
    val DeviceToken: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UpdateUserAccountDataModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && Email == other.Email
            && Phone == other.Phone
            && DeviceToken == other.DeviceToken


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}