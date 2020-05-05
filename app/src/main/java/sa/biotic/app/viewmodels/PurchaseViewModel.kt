package sa.biotic.app.viewmodels


import android.location.Address
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PurchaseViewModel : ViewModel() {


    private val _address = MutableLiveData<Address>()


    fun setAddress(address: Address) {
        _address.value = address
    }

    fun getAddress(): MutableLiveData<Address> {

        return _address
    }
}