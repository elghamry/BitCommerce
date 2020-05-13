package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ChangePasswordModel(
    val AccessToken: String,

    val UserID: String,
    val ConfirmedPassword: String,
    val NewPassword: String,
    val OldPassword: String,
    val DeviceToken: String


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is ChangePasswordModel

            && AccessToken == other.AccessToken
            && UserID == other.UserID
            && ConfirmedPassword == other.ConfirmedPassword
            && NewPassword == other.NewPassword
            && OldPassword == other.OldPassword


    override fun getUniqueIdentifier(): Long {
        return UserID.toLong()
    }
}