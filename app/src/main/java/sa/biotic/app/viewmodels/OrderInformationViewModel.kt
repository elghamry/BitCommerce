package sa.biotic.app.viewmodels



import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.CheckoutModel
import sa.biotic.app.model.CheckoutResponse
import sa.biotic.app.model.GetOrderModel
import sa.biotic.app.model.OrderInfoResponse
import sa.biotic.app.retrofit_service.Repository

class OrderInformationViewModel : ViewModel() {


     var orderInformationResponse = MutableLiveData<OrderInfoResponse>()
    private var _address = MutableLiveData<Address>()


    init {
        orderInformationResponse= Repository.getOrderResponse
    }






    fun getOrderModel(item : GetOrderModel)
    {
        Repository.getOrderInfo(item)
    }
}