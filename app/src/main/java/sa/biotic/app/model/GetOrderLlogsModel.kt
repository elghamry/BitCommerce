package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetOrderLlogsModel(
    val AccessToken: String,

    val UserID: Int,
    val DeviceToken:String,
    val OrderID: Int

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is GetOrderLlogsModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && DeviceToken == other.DeviceToken
            && OrderID == other.OrderID


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }

}