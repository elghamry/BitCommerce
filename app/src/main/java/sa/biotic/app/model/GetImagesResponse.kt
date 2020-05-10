package sa.biotic.app.model

data class GetImagesResponse(
    val ProductGalleryImages: MutableList<String>,
    var ProductID: Int
)