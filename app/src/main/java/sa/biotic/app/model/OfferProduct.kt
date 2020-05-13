package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OfferProduct(
    val OfferDescription_Ar: String,
    val OfferDescription_En: String,
    val OfferDicountValuePercentage: String,
    val OfferEndDate: String,
    val OfferID: Int,
    val OfferImage: String,
    val OfferName_Ar: String,
    val OfferName_En: String,
    val OfferPrice: Double,
    val OfferStartDate: String,
    val ProductCallories: Int,
    val ProductDescreption_Ar: String,
    val ProductDescreption_En: String,
    val ProductID: Int,
    val ProductImage: String,
    val ProductName_Ar: String,
    val ProductName_En: String,
    val ProductPrice: Double,
    val ProductReviews: String,
    val IsNew: Int,
    val ProductStockQuantity: Int

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean =
        other is OfferProduct
                && OfferEndDate == other.OfferEndDate
                && OfferDescription_Ar == other.OfferDescription_Ar
                && OfferDescription_En == other.OfferDescription_En
                && OfferDicountValuePercentage == other.OfferDicountValuePercentage
                && OfferID == other.OfferID
                && OfferImage == other.OfferImage
                && OfferName_Ar == other.OfferName_Ar
                && OfferName_En == other.OfferName_En
                && OfferPrice == other.OfferPrice
                && OfferStartDate == other.OfferStartDate
                && ProductCallories == other.ProductCallories
                && ProductDescreption_Ar == other.ProductDescreption_Ar
                && ProductDescreption_En == other.ProductDescreption_En
                && ProductID == other.ProductID
                && ProductImage == other.ProductImage
                && ProductName_Ar == other.ProductName_Ar
                && ProductName_En == other.ProductName_En
                && ProductPrice == other.ProductPrice
                && ProductReviews == other.ProductReviews
                && ProductStockQuantity == other.ProductStockQuantity

    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ProductID.toLong()
    }
}