package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cartbundle(
    val BundleStockAvaliable: Int,
    val CartQuantity: Int,
    val Description_Ar: String,
    val Description_En: String,
    val ID: Int,
    val Name_Ar: String,
    val Name_En: String,
    val Price: Double,
    val ProductsNamesAr: String,
    val ProductsNamesEn: String,
    val IsNew: Int,
    val SImage: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is Cartbundle &&
            BundleStockAvaliable == other.BundleStockAvaliable
            && CartQuantity == other.CartQuantity
            && Description_Ar == other.Description_Ar
            && Description_En == other.Description_En
            && ID == other.ID
            && Name_Ar == other.Name_Ar
            && Name_En == other.Name_En
            && Price == other.Price
            && ProductsNamesAr == other.ProductsNamesAr
            && ProductsNamesEn == other.ProductsNamesEn
            && SImage == other.SImage
            && IsNew == other.IsNew



    override fun getUniqueIdentifier(): Long {
        return ID.toLong()
    }
}