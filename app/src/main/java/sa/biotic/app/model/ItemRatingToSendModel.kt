package sa.biotic.app.model

import android.os.Parcelable
import com.idanatz.oneadapter.external.interfaces.Diffable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ItemRatingToSendModel(
    var UserID: Int,
    var AccessToken: String,
    var DeviceToken: String,
   var ItemIDs : String,
    var isbundles : String,
    var Rates : String,
    var OrderNumber: Int,
    var RateStatus : Boolean


) : Diffable, Parcelable {
    override fun areContentTheSame(other: Any): Boolean = other is ItemRatingToSendModel &&
            UserID == other.UserID &&
            AccessToken == other.AccessToken &&
            ItemIDs == other.ItemIDs &&
            isbundles == other.isbundles &&
            Rates == other.Rates &&
            OrderNumber == other.OrderNumber&&
            RateStatus == other.RateStatus


    override fun getUniqueIdentifier(): Long {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return UserID.toLong()
    }
}