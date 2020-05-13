package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Cartproduct(
    val Callories: Int,
    val CartQuantity: Int,
    val Description_Ar: String,
    val Description_En: String,
    val ID: Int,
    val Name_Ar: String,
    val Name_En: String,
    val Price: Double,
    val ProductOfferDicountValue: String,
    val ProductOfferID: String,
    val ProductOfferPrice: Double,
    val ProductStockQuantity: Int,
    val SImage: String,
    val TypeIsProduct: String,
    val IsNew: Int


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is Cartproduct &&
            Callories == other.Callories
            && CartQuantity == other.CartQuantity
            && Description_Ar == other.Description_Ar
            && Description_En == other.Description_En
            && ID == other.ID
            && Name_Ar == other.Name_Ar
            && Name_En == other.Name_En
            && Price == other.Price
            && ProductOfferDicountValue == other.ProductOfferDicountValue
            && ProductOfferID == other.ProductOfferID
            && ProductOfferPrice == other.ProductOfferPrice
            && ProductStockQuantity == other.ProductStockQuantity
            && SImage == other.SImage
            && TypeIsProduct == other.TypeIsProduct
            && IsNew == other.IsNew




    override fun getUniqueIdentifier(): Long {
        return ID.toLong()
    }
}