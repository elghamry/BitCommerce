package sa.biotic.app.model

data class UserLoginModelResponse(
    var Status: Boolean = false,
    val StatusDesc: String,
    val UserAccessTaken: String,
    val UserID: Int,
    val UserImage: String,
    var ValidationStatusNumber: Int
)