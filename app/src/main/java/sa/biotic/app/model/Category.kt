package sa.biotic.app.model

import com.idanatz.oneadapter.external.interfaces.Diffable


data class Category(
    val CategoryID: Int,
    val CategoryIconPurble: String,
    val CategoryIconWhite: String,
    val CategoryName_Ar: String,
    val CategoryName_En: String,
    val CategoryDesc_En: String,
    val CategoryDesc_Ar: String

) : Diffable {
    override fun areContentTheSame(other: Any): Boolean
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            = other is Category &&
            CategoryID == other.CategoryID && CategoryIconPurble == other.CategoryIconPurble
            && CategoryIconWhite == other.CategoryIconWhite
            && CategoryName_Ar == other.CategoryName_Ar
            && CategoryName_En == other.CategoryName_En
            && CategoryDesc_Ar == other.CategoryDesc_Ar
            && CategoryDesc_En == other.CategoryDesc_En



    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return CategoryID.toLong()
    }
}