package sa.biotic.app.model

data class ProductsOfBundle(
    val BundleDescription_Ar: String,
    val BundleDescription_En: String,
    val BundleID: Int,
    val BundleImage: String,
    val BundleName_Ar: String,
    val BundleName_En: String,
    val BundlePrice: Double,
    val BundleReview: Int,
    val BundletStockQuantity: Int,
    val Products: MutableList<ProductBund>

)