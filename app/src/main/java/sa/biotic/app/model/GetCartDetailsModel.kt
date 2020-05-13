package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GetCartDetailsModel(
    val UserID: Int,
    val AccessToken: String,
    val DeviceToken: String,
    var Promocode: String

//    var ProductID: Int=-1,
//    val Isbundle: Boolean,
//    val Quantity: Int

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is GetCartDetailsModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            DeviceToken == other.DeviceToken &&
            Promocode == other.Promocode

//            &&
//            ProductID == other.ProductID &&
//            Isbundle == other.Isbundle &&
//            Quantity == other.Quantity


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return UserID.toLong()
    }
}