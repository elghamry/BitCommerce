package sa.biotic.app.model

data class OrderRatingPostboResponse(
    val OrderItems: MutableList<OrderItemForRating>,
    val OrderNumber: Int
)