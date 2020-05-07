package sa.biotic.app.model

data class AddToCartResponse(
    val CartID: Int,
    val Status: Boolean,
    val StatusDesc: String
)