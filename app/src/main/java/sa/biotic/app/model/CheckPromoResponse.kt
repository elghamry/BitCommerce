package sa.biotic.app.model

data class CheckPromoResponse(
    val PromoCodeID: Int,
    val PromoDiscount: Double,
    var Status: Boolean = false
)