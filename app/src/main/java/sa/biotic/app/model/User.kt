package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val FirstName: String,
    val LastName: String,
    val Email: String,
    val Mobile: String,
    val confirmedPW: String,
    val Password: String,
    val Device: String = "android"

) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is User &&
            FirstName == other.FirstName && LastName == other.LastName
            && Email == other.Email
            && Mobile == other.Mobile
            && confirmedPW == other.confirmedPW
            && Password == other.Password


    override fun getUniqueIdentifier(): Long {
        return 0.toLong()
    }
}
//
//FirstName:testFirst
//LastName:testlast
//Email:abdelrahman@flexible.sa
//Mobile:512345678
//confirmedPW:Abc@1234
//Password:Abc@1234