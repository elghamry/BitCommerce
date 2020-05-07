package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VerifyModel(
    val Email: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is VerifyModel

            && Email == other.Email


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}