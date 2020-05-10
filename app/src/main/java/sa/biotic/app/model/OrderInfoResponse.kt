package sa.biotic.app.model

data class OrderInfoResponse(
    val AddressLine1: String,
    val AddressLine2: String,
    val City: String,
    val Country: String,
    val CustomerName: String,
    val CustomerPhone: String,
    val OrderItems: MutableList<OrderItem>,
    val OrderNumber: Int,
    val PaymentMethod: String,
    val PostalCode: String,
    val TotalPrice: Double,
    val latitude: String,
    val longitude: String,
    var RateStatus : Int = 0,
    val OrderDate  : String
)