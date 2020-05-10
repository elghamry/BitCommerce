package sa.biotic.app.model



data class CheckoutResponse(
    val AddressLine1: String,
    val AddressLine2: String,
    var City: String,
    val Country: String,
    val CustomerName: String,
    val CustomerPhone: String,
    val OrderDate: String,
    var OrderItems: MutableList<OrderItem>,
    val OrderNumber: Int,
    val PostalCode: String,
//    val ShippingDate: String,
    var Status: Boolean,
    val StatusDecription: String,
    val TotalPrice: Double,
    val latitude: String,
    val longitude: String
)