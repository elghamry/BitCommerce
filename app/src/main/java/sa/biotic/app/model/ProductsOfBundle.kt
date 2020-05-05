package sa.biotic.app.model

data class ProductsOfBundle(
    val BundleDescription_Ar: String,
    val BundleDescription_En: String,
    val BundleID: Int,
    val BundleImage: String,
    val BundleName_Ar: String,
    val BundleName_En: String,
    val BundlePrice: String,
    val BundleReview: Int,
    val Products: MutableList<ProductBund>

)