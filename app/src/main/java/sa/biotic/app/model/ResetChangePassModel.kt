package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResetChangePassModel(
    val Email: String,
    val ConfirmedPassword: String,
    val Password: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is ResetChangePassModel

            && Email == other.Email
            && Password == other.Password
            && ConfirmedPassword == other.ConfirmedPassword


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}