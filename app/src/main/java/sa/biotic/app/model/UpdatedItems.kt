package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize

data class UpdatedItems(
    val ItemID: Int,
    val ItemImage: String,
    val ItemNameAr: String,
    val ItemNameEn: String,
    val ItemStatus: Int,
    val ItemStockQuantity: Int,
    val isbundle: Boolean
) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UpdatedItems &&
            ItemID == other.ItemID
            && ItemImage == other.ItemImage
            && ItemNameAr == other.ItemNameAr
            && ItemNameEn == other.ItemNameEn
            && ItemStatus == other.ItemStatus
            && ItemStockQuantity == other.ItemStockQuantity
            && isbundle == other.isbundle




    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ItemID*ItemStockQuantity.toLong()
    }
}