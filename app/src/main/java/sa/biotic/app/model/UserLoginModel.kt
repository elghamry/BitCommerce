package sa.biotic.app.model


import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserLoginModel(
    val DeviceTaken: String,

    val Email: String,
    val Password: String

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is UserLoginModel

            && Email == other.Email
            && DeviceTaken == other.DeviceTaken
            && Password == other.Password


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}