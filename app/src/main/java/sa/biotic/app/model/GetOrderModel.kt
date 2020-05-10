package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetOrderModel(
    val AccessToken: String,
    val OrderID : Int,

    val UserID: Int,
    val DeviceToken:String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is GetOrderModel
            && OrderID == other.OrderID
            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && DeviceToken == other.DeviceToken


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}