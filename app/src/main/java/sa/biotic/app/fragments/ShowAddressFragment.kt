
package sa.biotic.app.fragments


import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentShowAddressBinding
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.viewmodels.PurchaseViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ShowAddressFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ShowAddressFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShowAddressFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var purchaseViewModel: PurchaseViewModel

    lateinit var binding: FragmentShowAddressBinding


    lateinit var address: String
    lateinit var city: String
    lateinit var state: String
    lateinit var country: String
    lateinit var postalCode: String
    lateinit var streetName: String
    lateinit var apartmentNumber: String
    lateinit var subThoroughfare: String
    lateinit var adminArea: String

    lateinit var lon: String
    lateinit var lat: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        purchaseViewModel =
            ViewModelProviders.of(requireActivity()).get(PurchaseViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, sa.biotic.app.R.layout.fragment_show_address, container, false


        )


        var remmber = true

        binding.etAddress.setText("Theodore Lowe, Ap #867-859 Sit Rd. Azusa New York")
        binding.etCity.setText("Riyadh")


        binding.nextBtn.setOnClickListener {
            var stepper =
                (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
                    sa.biotic.app.R.id.stepper
                )

//            stepper.goToNextStep()
//            stepper.goToNextStep()

            Repository.orderModel.City = city
            Repository.orderModel.longitude = lon
            Repository.orderModel.latitude = lat
            Repository.orderModel.addressL1 = address
            Repository.orderModel.addressL2 = ""
            Repository.orderModel.postalcode = postalCode

            if (remmber) {
                Repository.orderModel.IsDefault = 1
            } else {
                Repository.orderModel.IsDefault = 0
            }





            Navigation.findNavController(binding.root)
                .navigate(sa.biotic.app.R.id.action_showAddressFragment_to_paymentFragment)

        }



        binding.backBtn.setOnClickListener {
            var stepper =
                (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
                    sa.biotic.app.R.id.stepper
                )

            stepper.goToPreviousStep()
            stepper.goToPreviousStep()
            stepper.goToPreviousStep()

            Navigation.findNavController(binding.root).popBackStack()

        }


        Repository.getAddressCheckout().observe(viewLifecycleOwner, Observer<Address> {


            address =
                it.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            city = ""
            state = ""
            country = ""
            postalCode = ""
            streetName = ""
            apartmentNumber = ""
            subThoroughfare = ""
            adminArea = ""


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








            binding.etAddress.setText(address)
            binding.etCity.setText(city)
            binding.etCountry.setText(country)
            binding.etApartmentNumber.setText(apartmentNumber)
            binding.etPostalCode.setText(postalCode)
            binding.etStreetName.setText(streetName)





        })


        binding.rememberComp.setOnClickListener {

            if (remmber) {
                binding.defaultRadio.setImageDrawable(resources.getDrawable(R.drawable.radio))
                remmber = false
            } else {
                binding.defaultRadio.setImageDrawable(resources.getDrawable(R.drawable.radio_filled))
                remmber = true
            }
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    // TODO: Rename method, update argument and hook method into UI event
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
         * @return A new instance of fragment ShowAddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowAddressFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
