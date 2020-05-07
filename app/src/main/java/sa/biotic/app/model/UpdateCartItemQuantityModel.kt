package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class UpdateCartItemQuantityModel(
    var UserID: Int,
    var AccessToken: String,
    var ProductID: Int = -1,
    var Isbundle: Boolean,
    var Quantity: Int,
    var Devicetoken: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UpdateCartItemQuantityModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            ProductID == other.ProductID &&
            Isbundle == other.Isbundle &&
            Quantity == other.Quantity &&
            Devicetoken == other.Devicetoken


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ProductID.toLong()
    }
}