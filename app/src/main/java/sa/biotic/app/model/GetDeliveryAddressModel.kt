package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GetDeliveryAddressModel(
    val AccessToken: String,

    val UserID: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is GetDeliveryAddressModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}