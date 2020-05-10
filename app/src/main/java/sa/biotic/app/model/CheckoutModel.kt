package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class CheckoutModel(
    var UserID: Int,
    var AccessToken: String,
    var longitude: String,
    var latitude:String,
    var City:String,
    var addressL1:String,
    var addressL2:String,
    var postalcode:String,
    var IsDefault:Int,
    var CheckOutTotal:Double,
    var PromoCodeText : String,
    var DeviceToken: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is CheckoutModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            longitude == other.longitude &&
            latitude == other.latitude &&
            City == other.City &&
            addressL1 == other.addressL1 &&
            addressL2 == other.addressL2 &&
            postalcode == other.postalcode &&
            IsDefault == other.IsDefault &&
            CheckOutTotal == other.CheckOutTotal &&
            PromoCodeText == other.PromoCodeText

    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return UserID.toLong()
    }
}