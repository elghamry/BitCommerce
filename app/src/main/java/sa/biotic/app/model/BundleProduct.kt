package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BundleProduct(
    val BundleDescription_Ar: String,
    val BundleDescription_En: String,
    var BundleID: Int = -1,
    val BundleImage: String,
    val BundleName_Ar: String,
    val BundleName_En: String,
    val BundlePrice: String,
    val BundleReview: Int,
    val BundleStockAvaliable: Int

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is BundleProduct &&
            BundleID == other.BundleID
            && BundleName_Ar == other.BundleName_Ar
            && BundleDescription_Ar == other.BundleDescription_Ar
            && BundleDescription_En == other.BundleDescription_En
            && BundleImage == other.BundleImage
            && BundleName_En == other.BundleName_En
            && BundlePrice == other.BundlePrice
            && BundleReview == other.BundleReview
            && BundleStockAvaliable == other.BundleStockAvaliable




    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return BundleID.toLong()
    }
}