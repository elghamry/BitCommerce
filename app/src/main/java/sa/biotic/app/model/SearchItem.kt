package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchItem(
    val Callories: Int,
    val Description_Ar: String,
    val Description_En: String,
    val ID: Int,
    val Name_Ar: String,
    val Name_En: String,
    val Price: String,
    val ProductOfferDicountValue: String,
    val ProductOfferID: String,
    val ProductOfferPrice: String,
    val SImage: String,
    val TypeIsProduct: String,
    val ProductStockQuantity: Int,
    val IsNew: Int
) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is SearchItem &&
            Callories == other.Callories
            && Description_Ar == other.Description_Ar
            && Description_En == other.Description_En
            && Name_Ar == other.Name_Ar
            && ID == other.ID
            && Name_En == other.Name_En
            && Price == other.Price
            && SImage == other.SImage
            && TypeIsProduct == other.TypeIsProduct
            && ProductOfferPrice == other.ProductOfferPrice
            && ProductOfferID == other.ProductOfferID
            && ProductOfferDicountValue == other.ProductOfferDicountValue
            && ProductStockQuantity == other.ProductStockQuantity
            && IsNew == other.IsNew

    override fun getUniqueIdentifier(): Long {
        return ID.toLong()
    }
}