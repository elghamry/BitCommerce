package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UpdateProfileAddressResponse(
    val StatusDesc: String,

    var Status: Int

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UpdateProfileAddressResponse

            && StatusDesc == other.StatusDesc
            && Status == other.Status


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}