package sa.biotic.app.model

data class GetOrderLogsResponse(
    val DriverID: Int,
    val Latitude: String,
    val Longitude: String,
    val OrderNumber: Int,
    val OrderStatus: Int
)