package sa.biotic.app.model

import com.idanatz.oneadapter.external.interfaces.Diffable

data class OrderItemForRating(
    val Description_Ar: String,
    val Description_En: String,
    val ID: Int,
    val ISBundle: Boolean,
    val Name_Ar: String,
    val Name_En: String,
    val Price: Double,
    val ProductsNamesAr: String,
    val ProductsNamesEn: String,
    val Quantity: Int,
    val SImage: String
): Diffable {

    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            = other is OrderItemForRating &&
            Description_Ar == other.Description_Ar
            && Description_En == other.Description_En
            && ID == other.ID
            && Name_Ar == other.Name_Ar
            && Name_En == other.Name_En
            &&Price == other.Price
            && ProductsNamesAr == other.ProductsNamesAr
            && ProductsNamesEn == other.ProductsNamesEn
            && Quantity == other.Quantity
            && SImage == other.SImage
            && ISBundle == other.ISBundle
//   = false


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ID.toLong()
    }
}