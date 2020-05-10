package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderAfterConfModel(
    val AccessToken: String,

    val UserID: Int,
   val status : Int,
    val DeviceToken : String



) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is OrderAfterConfModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && status == other.status


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}