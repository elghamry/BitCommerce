package sa.biotic.app.temp_models


import com.idanatz.oneadapter.external.interfaces.Diffable

data class BundleProds_temp(
    val id: Long,
    val img: String,
    val title: String,
    val price: String,
    val description: String
) : Diffable {
    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            = other is BundleProds_temp &&
            img == other.img
            && title == other.title &&
            price == other.price
            && description == other.description
//   = false


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return id
    }
}