package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AddToCartModel(
    var UserID: Int,
    var AccessToken: String,
    var ProductID: Int = -1,
    var Isbundle: Boolean,
    var Quantity: Int,
    var DeviceToken: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is AddToCartModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            ProductID == other.ProductID &&
            Isbundle == other.Isbundle &&
            Quantity == other.Quantity &&
            DeviceToken == other.DeviceToken


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ProductID.toLong()
    }
}