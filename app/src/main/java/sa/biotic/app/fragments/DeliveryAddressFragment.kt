package sa.biotic.app.fragments

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import sa.biotic.app.R
import sa.biotic.app.components.ProgressBottle
import sa.biotic.app.databinding.FragmentDeliveryAddressBinding
import sa.biotic.app.model.GetProfileAddress
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.margin
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [DeliveryAddressFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [DeliveryAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DeliveryAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentDeliveryAddressBinding
    lateinit var bottomNavigationView: BottomNavigationView
    var first: Boolean = true


    lateinit var address: String
    lateinit var city: String
    lateinit var state: String
    lateinit var country: String
    lateinit var postalCode: String
    lateinit var streetName: String
    lateinit var apartmentNumber: String
    lateinit var subThoroughfare: String
    lateinit var adminArea: String

    lateinit var progress: ProgressBottle


    lateinit var lon: String
    lateinit var lat: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_delivery_address, container, false
        )

        progress = (activity as AppCompatActivity).findViewById(R.id.myProgBar)
        progress.setTextMsg("Loading")

        progress.visibility = View.VISIBLE

        binding.rememberComp.visibility = View.INVISIBLE


        Repository.getProfileAddress(
            GetProfileAddress(
                UserInfo.access_token, UserInfo.uid, UserInfo.device_token
            )
        )



        Repository.getDeliverAddressResponse.observe(viewLifecycleOwner, Observer {


            if (it.AddressLine1.isNullOrEmpty()) {
                progress.visibility = View.GONE
                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_deliveryAddressFragment_to_addressFragmentProfile)
            } else {
                if (it.AddressLine1 != "rr") {

//                   binding.etApartmentNumber.setText(it.AddressLine2)
//                   binding.etCity.setText(it.City)
//                   binding.etCountry.setText(it.Country)
//                   binding.etStreetName.setText(it.AddressLine2)
//                   binding.etPostalCode.setText(it.PostalCode)

                    getAddress(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))

                    it.AddressLine1 = "rr"
                }
            }


        })


        Repository.getAddressProfile().observe(viewLifecycleOwner, Observer<Address> {


            address =
                it.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = it.locality
            state = it.adminArea
            country = it.countryName
            postalCode = it.postalCode
            streetName = it.featureName
            apartmentNumber = ""
            subThoroughfare = ""
            adminArea = it.adminArea


            lon = it.longitude.toString()
            lat = it.latitude.toString()
//            if(!it.subThoroughfare.isNullOrEmpty() && !it.subThoroughfare.isNullOrBlank()){
//                apartmentNumber = it.subThoroughfare
//            }


//            binding.etAddress.setText(address)
//            binding.etCity.setText(city)


//            var addressToShow = "Firas Alateeq\n"
//            if (!it.premises.isNullOrEmpty() && !it.premises.isNullOrBlank())
//                apartmentNumber = it.premises  else ""

            if (!it.subThoroughfare.isNullOrEmpty() && !it.subThoroughfare.isNullOrBlank())
                apartmentNumber = it.subThoroughfare else ""
            if (!it.thoroughfare.isNullOrEmpty() && !it.thoroughfare.isNullOrBlank())
                streetName = it.thoroughfare else ""
            if (!it.adminArea.isNullOrEmpty() && !it.adminArea.isNullOrBlank())
                adminArea = it.adminArea else ""
            if (!it.locality.isNullOrEmpty() && !it.locality.isNullOrBlank())
                city = it.locality else ""
            if (!it.countryName.isNullOrEmpty() && !it.countryName.isNullOrBlank())
                country = it.countryName else ""
            if (!it.postalCode.isNullOrEmpty() && !it.postalCode.isNullOrBlank())
                postalCode = it.postalCode else ""


            if (it.thoroughfare.isNullOrEmpty()) {
                if (!it.subLocality.isNullOrEmpty() && !it.subLocality.isNullOrBlank())
                    streetName = it.subLocality
            }

            if (it.subThoroughfare.isNullOrEmpty()) {
                if (!it.premises.isNullOrEmpty() && !it.premises.isNullOrBlank())
                    apartmentNumber = it.premises
            }


//            binding.etAddress.setText(address)
            binding.etCity.setText(city)
            binding.etCountry.setText(country)
            binding.etApartmentNumber.setText(apartmentNumber)
            binding.etPostalCode.setText(postalCode)
            binding.etStreetName.setText(streetName)


            progress.visibility = View.GONE


        })


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE






//        if(!UserInfo.has_default_address){
//            Navigation.findNavController(binding.root).navigate(R.id.action_deliveryAddressFragment_to_addressFragmentProfile)
//        }


//        UserInfo.asLiveData(UserInfo::has_default_address)
//            .observe(viewLifecycleOwner, Observer<Boolean> {
//                //                Log.d("shared",it.toString())
////
//                if(!it){
//                    Navigation.findNavController(binding.root).navigate(R.id.action_deliveryAddressFragment_to_addressFragmentProfile)
////                    if(it==false){
////                        Log.d("hereiam","iam here false from login ")
////
////                    }
////                    else
////                    {
////                        Log.d("hereiam","iam here true from login ")
////                    }
//                }
//            })

        return binding.root
    }


    private fun getAddress(latLng: LatLng): String {
        // 1
        var loc = Locale("en", "US")//en US
        var aLocale = Locale.Builder().setLanguage("en").setScript("Latn").setRegion("RS").build()
        val geocoder = Geocoder(requireContext(), loc)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                Log.d("helloAdd", address.maxAddressLineIndex.toString())
//        Log.d("addresss",addresses.toString())
//        for (i in 0 until address.maxAddressLineIndex) {
//
////          addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
//
//        }
                var address = addresses.get(0)
                    .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                var city = addresses.get(0).locality
                var state = addresses.get(0).adminArea
                var country = addresses.get(0).countryName
                var postalCode = addresses.get(0).postalCode
                var knownName = addresses.get(0).featureName
                var var1 = addresses.get(0).thoroughfare
                var var2 = addresses.get(0).subThoroughfare
                var premises = addresses.get(0).premises

                addressText =
                    address + "\n" + city + "\n" + state + "\n" + country + "\n" + postalCode + "\n" + knownName + "\n" + var1 + "\n" + var2 + "\n" + premises
                Log.d("addresss", addressText.toString())


//                Toast.makeText(context, address, Toast.LENGTH_LONG).show()

                Repository.setAddressProfile(addresses.get(0))
            }


        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)

            progress.visibility = View.GONE
        }





        return addressText
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
//            R.id.action_search -> {
////                Toast.makeText(applicationContext, "click search", Toast.LENGTH_LONG).show()
//                val intent = Intent(this, MainActivity::class.java)
//
//                intent.putExtra("root", "search")
//
//
////            intent.putExtra(EXTRA_MESSAGE, message)
//                launchActivity<MainActivity> {
//                    putExtra("root", "search")
//                }
////                startActivityForResult(intent, 1)
////                finish()
//                true
//            }
//            R.id.action_cart -> {
////                Toast.makeText(applicationContext, "click cart", Toast.LENGTH_LONG).show()
//
//                launchActivity<MainActivity> {
//                    putExtra("root", "cart")
//                }
//
//                finish()
////
//                return true
//
//            }
            android.R.id.home -> {
                Navigation.findNavController(binding.root)
                    .popBackStack(R.id.accountMangementFragment, false)


//                RxAnimation.together(
//                    RxAnimation.together(
//                                toolbar.translation(0F, -60F,duration = 100L),
//                                nested_product.translation(0F, 90F,duration = 100L))
//                    ,
//                               Completable.fromRunnable { supportFinishAfterTransition()
//                               }
//                                )  .subscribe().addTo(composite)


                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }
//
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
//    }
//
//    override fun onDetach() {
//        super.onDetach()
//        listener = null
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     *
//     *
//     * See the Android Training lesson [Communicating with Other Fragments]
//     * (http://developer.android.com/training/basics/fragments/communicating.html)
//     * for more information.
//     */
//    interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        fun onFragmentInteraction(uri: Uri)
//    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DeliveryAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DeliveryAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
