package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class OrderAfterConfResponse(
    val OrderDate: String,
    val OrderNumber: Int,
    val TotalPrice: Double
) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is OrderAfterConfResponse &&
            OrderDate == other.OrderDate
            && OrderNumber == other.OrderNumber
            && TotalPrice == other.TotalPrice

    override fun getUniqueIdentifier(): Long {
        return OrderNumber.toLong()
    }
}
