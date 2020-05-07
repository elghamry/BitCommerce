package sa.biotic.app.model

data class UserRegisterationResponse(
    var Status: Boolean,
    val StatusDesc: String,
    var UserID: Int,
    val ValidationStatusNumber: Int
)