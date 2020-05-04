package sa.biotic.app.temp_models

import com.idanatz.oneadapter.external.interfaces.Diffable


data class Offer_temp(
    var title: String,
    var desc: String,
    var img: String,
    var price: String,
    var prev_price: String

) : Diffable {
    override fun areContentTheSame(other: Any): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getUniqueIdentifier(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}