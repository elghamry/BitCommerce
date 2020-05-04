package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val ProductCallories: Int,
    val ProductCategoryID: String,
    val ProductDescription_Ar: String,
    val ProductDescription_En: String,
    val ProductID: Int,
    val ProductImage: String,
    val ProductName_Ar: String,
    val ProductName_En: String,
    val ProductPrice: String
) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is Product &&
            ProductCallories == other.ProductCallories && ProductCategoryID == other.ProductCategoryID
            && ProductDescription_Ar == other.ProductDescription_Ar
            && ProductDescription_En == other.ProductDescription_En
            && ProductID == other.ProductID
            && ProductImage == other.ProductImage
            && ProductName_Ar == other.ProductName_Ar
            && ProductName_En == other.ProductName_En
            && ProductPrice == other.ProductPrice


    override fun getUniqueIdentifier(): Long {
        return ProductID.toLong()
    }
}