package sa.biotic.app.model

data class AddProductCartAfterLoginResponse(
    val ISbundle: Boolean,
    val ItemID: Int,
    val Status: Boolean,
    val StatusDesc: String
)