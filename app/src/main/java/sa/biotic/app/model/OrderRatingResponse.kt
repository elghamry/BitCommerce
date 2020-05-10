package sa.biotic.app.model

data class OrderRatingResponse(
    val OrderItems: MutableList<OrderItemForRating>,
    val OrderNumber: Int
)