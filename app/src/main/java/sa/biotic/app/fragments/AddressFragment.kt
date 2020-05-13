package sa.biotic.app.fragments

import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.google.android.gms.maps.model.LatLng
import sa.biotic.app.R
import sa.biotic.app.components.ProgressBottle
import sa.biotic.app.databinding.FragmentAddressBinding
import sa.biotic.app.model.GetProfileAddress
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import java.io.IOException
import java.util.*
import kotlin.properties.Delegates

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AddressFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    lateinit var binding: FragmentAddressBinding
    var defClick by Delegates.notNull<Boolean>()


    lateinit var progress: ProgressBottle


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        defClick = false




        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_address, container, false


        )
        progress = (activity as AppCompatActivity).findViewById(R.id.myProgBar)
        progress.setTextMsg("Loading")

        progress.visibility = View.VISIBLE



        var stepper =
            (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
                R.id.stepper
            )



        stepper.goToNextStep()
        stepper.goToNextStep()
        binding.cancelBtn.setOnClickListener {
            activity?.finish()
        }

        Repository.getProfileAddress(
            GetProfileAddress(
                UserInfo.access_token, UserInfo.uid, UserInfo.device_token
            )
        )

//        Repository.getDeliverAddressResponse.observe(viewLifecycleOwner, Observer {
//
//            //            if(clickedCatID == -1)
////            {
////                Repository.getCategoryProducts(newCats[0].CategoryID)
////            }
////
////
////            else
////            {
////                Repository.getCategoryProducts(clickedCatID)
////            }
////            oneAdapter.setItems(newCats)
////
////
////            categoryRecycler.post {
////                categoryRecycler.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
////
////
////            }
//
//
//            if(it.AddressLine1.isNullOrEmpty()){
//               binding.defaultAddressBtn.visibility=View.GONE
//            }
//            else
//            {
//                if(it.AddressLine1!="rr"){
//
////                    var viewmodel : PurchaseViewModel
////                    viewmodel.setAddress()
//                    binding.defaultAddressBtn.visibility=View.VISIBLE
//                    getAddress(LatLng(it.latitude.toDouble(),it.longitude.toDouble()))
//
//                    it.AddressLine1="rr"
//                }
//            }
//
//
//        })


        Repository.getDeliverAddressResponse.observe(viewLifecycleOwner, Observer {

            //            if(clickedCatID == -1)
//            {
//                Repository.getCategoryProducts(newCats[0].CategoryID)
//            }
//
//
//            else
//            {
//                Repository.getCategoryProducts(clickedCatID)
//            }
//            oneAdapter.setItems(newCats)
//
//
//            categoryRecycler.post {
//                categoryRecycler.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
//
//
//            }


            if (it.AddressLine1.isNullOrEmpty()) {
                binding.defaultAddressBtn.visibility = View.GONE
                progress.visibility = View.GONE


            } else {
                if (it.AddressLine1 != "rr") {


//                    var viewmodel : PurchaseViewModel
//                    viewmodel.setAddress()
                    binding.defaultAddressBtn.visibility = View.VISIBLE
                    getAddress(LatLng(it.latitude.toDouble(), it.longitude.toDouble()))

                    it.AddressLine1 = "rr"
                }
            }


        })





        Repository.getAddressCheckout().observe(viewLifecycleOwner, Observer {

            //            if(clickedCatID == -1)
//            {
//                Repository.getCategoryProducts(newCats[0].CategoryID)
//            }
//
//
//            else
//            {
//                Repository.getCategoryProducts(clickedCatID)
//            }
//            oneAdapter.setItems(newCats)
//
//
//            categoryRecycler.post {
//                categoryRecycler.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
//
//
//            }


            if (it.getAddressLine(0).isNullOrEmpty()) {

                binding.defaultAddressBtn.isEnabled = false
//               if(defClick){
//                   defClick = false
//                   Navigation.findNavController(binding.root)
//                       .navigate(R.id.action_addressFragment_to_showAddressFragment)
//
//
//               }
            }


        })










        binding.defaultAddressBtn.setOnClickListener {

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_addressFragment_to_showAddressFragment)

//            defClick = true


        }


        binding.myLocationBtn.setOnClickListener {

            defClick = false

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_addressFragment_to_getMyLocationFragment)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun getAddress(latLng: LatLng): String {
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
//                Log.d("helloAdd", address.maxAddressLineIndex.toString())
//        Log.d("addresss",addresses.toString())
//        for (i in 0 until address.maxAddressLineIndex) {
//
////          addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
//
//        }
//                var address = addresses.get(0)
//                    .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                var city = addresses.get(0).locality
//                var state = addresses.get(0).adminArea
//                var country = addresses.get(0).countryName
//                var postalCode = addresses.get(0).postalCode
//                var knownName = addresses.get(0).featureName
//                var var1 = addresses.get(0).thoroughfare
//                var var2 = addresses.get(0).subThoroughfare
//                var premises = addresses.get(0).premises
//
//                addressText =
//                    address + "\n" + city + "\n" + state + "\n" + country + "\n" + postalCode + "\n" + knownName + "\n" + var1 + "\n" + var2 + "\n" + premises
//                Log.d("addresss", addressText.toString())
//
//
//                Toast.makeText(context, address.getAddressLine(0), Toast.LENGTH_LONG).show()
                Repository.setAddressCheckout(addresses.get(0))

                progress.visibility = View.GONE
            }


        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)

            progress.visibility = View.GONE
        }





        return addressText
    }

    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
