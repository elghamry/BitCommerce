package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateDeliveryAddressModel(
    val AccessToken: String,

    val UserID: String,


    val longitude: String,

    val latitude: String,

    val City: String,

    val addressL1: String,

    val addressL2: String,

    val postalcode: String,

    val IsDefault: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UpdateDeliveryAddressModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && longitude == other.longitude
            && latitude == other.latitude
            && City == other.City
            && addressL1 == other.addressL1
            && addressL2 == other.addressL2
            && postalcode == other.postalcode
            && IsDefault == other.IsDefault


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}