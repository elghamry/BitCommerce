package sa.biotic.app.model

import com.idanatz.oneadapter.external.interfaces.Diffable

data class Review(val id: Long, val img: String, val rate: Int, val name: String) : Diffable {

    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            = other is Review &&
            img == other.img
            && rate == other.rate
            && name == other.name
//   = false


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return id
    }
}