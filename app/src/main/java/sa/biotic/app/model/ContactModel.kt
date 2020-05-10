package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactModel(
    val Name: String,

    val Email: String,
    val Phone: String,
    val Message: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is ContactModel

            && Email == other.Email
            && Name == other.Name
            && Message == other.Message
            && Phone == other.Phone


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}