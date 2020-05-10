package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize
import kotlin.random.Random

@Parcelize
data class GetReviewsResponse(
    val UserImage: String,
    val UserName: String,
    val UserRate: Int
): Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is GetReviewsResponse &&
            UserImage == other.UserImage
            && UserName == other.UserName
            && UserRate == other.UserRate



    override fun getUniqueIdentifier(): Long {


        return Random.nextLong()
    }
}