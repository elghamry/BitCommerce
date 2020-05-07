package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ForgetPasswordModel(
    val Email: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is ForgetPasswordModel

            && Email == other.Email


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}