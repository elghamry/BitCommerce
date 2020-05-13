package sa.biotic.app.viewmodels


import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.retrofit_service.Repository

class PurchaseViewModel : ViewModel() {


    private var _address = MutableLiveData<Address>()

    init {
        _address = Repository.address_checkout
    }


    fun setAddress(address: Address) {
        Repository.setAddressCheckout(address)
    }

    fun getAddress(): MutableLiveData<Address> {

        return _address
    }
}