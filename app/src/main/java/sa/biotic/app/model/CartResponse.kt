package sa.biotic.app.model

data class CartResponse(
    val Tax: Double,
    val TotalPrice: Double,
    val cartbundles: MutableList<Cartbundle>,
    val cartproduct: MutableList<Cartproduct>,
//    val updatedIDs : MutableList<UpdatedIDs>,
    val CartID: Int

)