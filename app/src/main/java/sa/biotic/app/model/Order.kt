package sa.biotic.app.model

import com.idanatz.oneadapter.external.interfaces.Diffable

data class Order(
    val id: Long,
    val no: String,
    val date: String,
    val delivery_date: String,
    val total_price: String
) : Diffable {

    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            = other is Order &&
            id == other.id
            && no == other.no
            && date == other.date
            && delivery_date == other.delivery_date
            && total_price == other.total_price
//   = false


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return id
    }
}