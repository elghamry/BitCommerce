package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class DeleteCartItemModel(
    var UserID: Int,
    var AccessToken: String,
    var ProductID: Int = -1,
    var Isbundle: Boolean,
    var DeviceToken: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is DeleteCartItemModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            ProductID == other.ProductID &&
            Isbundle == other.Isbundle &&
            DeviceToken == other.DeviceToken


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ProductID.toLong()
    }
}