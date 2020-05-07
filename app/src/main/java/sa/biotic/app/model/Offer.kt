package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class Offer(
    val OfferDescription_Ar: String,
    val OfferDescription_En: String,
    val OfferDicountValuePercentage: String,
    val OfferEndDate: String,
    val OfferID: Int,
    val OfferImage: String,
    val OfferName_Ar: String,
    val OfferName_En: String,
    val OfferStartDate: String
) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUniqueIdentifier(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}