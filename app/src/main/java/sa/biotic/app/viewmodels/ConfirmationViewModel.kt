package sa.biotic.app.viewmodels



import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.CheckoutModel
import sa.biotic.app.model.CheckoutResponse
import sa.biotic.app.retrofit_service.Repository

class ConfirmationViewModel : ViewModel() {


     var checkoutResponse = MutableLiveData<CheckoutResponse>()
    private var _address = MutableLiveData<Address>()


    init {
        checkoutResponse= Repository.checkoutResponse
    }





    fun setAddress(address: Address) {
        _address.value = address
    }

    fun getAddress(): MutableLiveData<Address> {

        return _address
    }

    fun checkout(item : CheckoutModel)
    {
        Repository.checkout(item)
    }
}