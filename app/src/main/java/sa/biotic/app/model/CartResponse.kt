package sa.biotic.app.model

data class CartResponse(
    val TaxPrice: Double,
    val TotalPrice: Double,
    val DiscountPrice: Double,
    val SubTotalPrice: Double,
    val cartbundles: MutableList<Cartbundle>,
    val cartproduct: MutableList<Cartproduct>,
    val UpdatedCartItem: MutableList<UpdatedItems>,
    val CartID: Int,
    val Promo: Promo

)