package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BundleProds(
    val BundleDescription_Ar: String,
    val BundleDescription_En: String,
    val BundleID: Int,
    val BundleImage: String,
    val BundleName_Ar: String,
    val BundleName_En: String,
    val BundlePrice: String,
    val BundleReviews: String
) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is BundleProds &&
            BundleID == other.BundleID
            && BundleName_Ar == other.BundleName_Ar
            && BundleDescription_Ar == other.BundleDescription_Ar
            && BundleDescription_En == other.BundleDescription_En
            && BundleImage == other.BundleImage
            && BundleName_En == other.BundleName_En
            && BundlePrice == other.BundlePrice
            && BundleReviews == other.BundleReviews



    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return BundleID.toLong()
    }
}