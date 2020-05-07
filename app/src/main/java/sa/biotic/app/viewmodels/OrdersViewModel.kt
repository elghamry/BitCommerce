package sa.biotic.app.viewmodels


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import sa.biotic.app.model.Order


class OrdersViewModel : ViewModel() {

    var ords: MutableList<Order> = mutableListOf<Order>()


    //livedata
    var ordsLive: MutableLiveData<MutableList<Order>> = MutableLiveData<MutableList<Order>>()


    init {
        ordsLive.value = ords

        getOrds()


    }

    private fun getOrds() {
//        revs.add(Review(revs.size+1.toLong(),"https://avatarfiles.alphacoders.com/190/190934.jpg",5,"Girly lily"))
//
        ords.add(
            Order(
                ords.size.toLong(),
                "123",
                "25 JUN 19",
                "1 JUl 19",
                "1234 SAR"
            )
        )
//        for (i in 1..20) {
//            ords.add(
//                Order(
//                    ords.size.toLong(),
//                    "123",
//                    "25 JUN 19",
//                    "1 JUl 19",
//                    "1234 SAR"
//                )
//            )
//            ords.add(
//                Order(
//                    ords.size.toLong(),
//                    "321",
//                    "27 JUN 19",
//                    "2 JUl 19",
//                    "1234 SAR"
//                )
//            )
//            ords.add(
//                Order(
//                    ords.size.toLong(),
//                    "8909",
//                    "29 JUN 19",
//                    "4 JUl 19",
//                    "1234 SAR"
//                )
//            )
//            ords.add(
//                Order(
//                    ords.size.toLong(),
//                    "9847",
//                    "2 JAN 20",
//                    "20 JUl 20",
//                    "1234 SAR"
//                )
//            )
//
//        }

        ordsLive.value = ords

    }


}