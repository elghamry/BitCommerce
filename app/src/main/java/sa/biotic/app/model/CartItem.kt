package sa.biotic.app.model

import com.idanatz.oneadapter.external.interfaces.Diffable


data class CartItem(
    val id: Long,
    val typeId: Int,
    val offerId: Int,
    val type: String,
    val img: String,
    val name: String,
    val description: String,
    var quantity: Int,
    var stockQuantity: Int,
    var discount: String,

    val price: String,
    var isExpanded: Boolean = false
) : Diffable {

    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            = other is CartItem &&
            img == other.img
            && id == other.id
            && typeId == other.typeId
            && type == other.type
            && img == other.img
            && name == other.name
            && description == other.description
            && quantity == other.quantity
            && price == other.price

//   = false


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return id
    }
}