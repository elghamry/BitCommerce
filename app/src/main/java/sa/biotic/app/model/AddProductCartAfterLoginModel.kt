package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class AddProductCartAfterLoginModel(
    var UserID: Int,
    var AccessToken: String,
    var ProductIDs: String,
    var isbundles: String,
    var Quantitys: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is AddProductCartAfterLoginModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            ProductIDs == other.ProductIDs &&
            isbundles == other.isbundles &&
            Quantitys == other.Quantitys


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return UserID.toLong()
    }
}