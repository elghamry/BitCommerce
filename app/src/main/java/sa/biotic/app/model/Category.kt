package sa.biotic.app.model

import com.idanatz.oneadapter.external.interfaces.Diffable

data class Category(var  icon : String,var title : String) : Diffable {
    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        = other is  Category &&
                icon ==  other.icon && title == other.title;


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return 5.toLong()
    }
}