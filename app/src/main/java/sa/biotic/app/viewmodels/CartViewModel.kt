package sa.biotic.app.viewmodels


import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository

class CartViewModel : ViewModel() {

    var cartItems: MutableList<CartItem> = mutableListOf<CartItem>()


    //livedata
    var cartItemsLiveData: MutableLiveData<MutableList<CartItem>> =
        MutableLiveData<MutableList<CartItem>>()

    var addToCartLiveData: MutableLiveData<AddToCartResponse> =
        MutableLiveData<AddToCartResponse>()

    var updateQuantityLiveData: MutableLiveData<UpdateCartItemQuantityResponse> =
        MutableLiveData<UpdateCartItemQuantityResponse>()

    var deletCartItemLiveData: MutableLiveData<DeleteCartItemResponse> =
        MutableLiveData<DeleteCartItemResponse>()


    var getCartDetailsLiveData: MutableLiveData<CartResponse> =
        MutableLiveData<CartResponse>()


    var totalPrice: MutableLiveData<String> = MutableLiveData<String>()


    init {
        Log.i("ReviewsViewModel", "ReviewsViewModel created!")

        addToCartLiveData = Repository.addToCartResponse
        getCartDetailsLiveData = Repository.getCartResponse
        updateQuantityLiveData = Repository.updateQuantityResponse
        deletCartItemLiveData = Repository.deleteCartItemResponse

        getCartItems()


    }

    private fun getCartItems() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        cartItemsLiveData = Repository.getCartLocal()


    }

    private fun addCartItemOn(item: AddToCartModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.addToCart(item)


    }


    fun getCartItemsOn(item: GetCartDetailsModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.getCartDetails(item)


    }


    fun updateCartItemQuantity(item: UpdateCartItemQuantityModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.updateCartItemQuantity(item)

    }

    fun deletCartItem(item: DeleteCartItemModel) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        Repository.deleteCartItem(item)

    }



    fun getTotalPrice() {
        totalPrice.value = "0"
        if (!cartItemsLiveData.value.isNullOrEmpty())
            for (i in 0..cartItemsLiveData.value!!.size - 1)
                totalPrice.value = (totalPrice.value?.toFloat()?.plus(
                    cartItemsLiveData.value?.get(i)?.price!!.toFloat() * cartItemsLiveData.value?.get(
                        i
                    )!!.quantity
                )).toString()

    }



}