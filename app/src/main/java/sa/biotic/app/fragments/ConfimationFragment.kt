package sa.biotic.app.fragments

import android.location.Address
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentConfimationBinding
import sa.biotic.app.shared_prefrences_model.UserRoute
import sa.biotic.app.viewmodels.PurchaseViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfimationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfimationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfimationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentConfimationBinding
    lateinit var purchaseViewModel: PurchaseViewModel

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
            inflater, R.layout.fragment_confimation, container, false


        )

        binding.confirmBtn.setOnClickListener {
            UserRoute.next_step = "home"
            activity?.finish()
        }

        purchaseViewModel.getAddress().observe(viewLifecycleOwner, Observer<Address> {


            var address =
                it.getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            var city = it.locality
            var state = it.adminArea
            var country = it.countryName
            var postalCode = it.postalCode
            var knownName = it.featureName
            var premises = it.premises


            var addressToShow = "Firas Alateeq\n"
            if (!it.premises.isNullOrEmpty() && !it.premises.isNullOrBlank())
                addressToShow += it.premises + " \n" else ""

            if (!it.subThoroughfare.isNullOrEmpty() && !it.subThoroughfare.isNullOrBlank())
                addressToShow += it.subThoroughfare + " \n" else ""
            if (!it.thoroughfare.isNullOrEmpty() && !it.thoroughfare.isNullOrBlank())
                addressToShow += it.thoroughfare + " \n" else ""
            if (!it.thoroughfare.isNullOrEmpty() && !it.thoroughfare.isNullOrBlank())
                addressToShow += it.thoroughfare + " \n" else ""
            if (!it.adminArea.isNullOrEmpty() && !it.adminArea.isNullOrBlank())
                addressToShow += it.adminArea + " \n" else ""
            if (!it.locality.isNullOrEmpty() && !it.locality.isNullOrBlank())
                addressToShow += it.locality + " \n" else ""
            if (!it.countryName.isNullOrEmpty() && !it.countryName.isNullOrBlank())
                addressToShow += it.countryName + " \n" else ""
            if (!it.postalCode.isNullOrEmpty() && !it.postalCode.isNullOrBlank())
                addressToShow += it.postalCode + " " else ""


//            +it.featureName+ "\n" + city + "\n" + postalCode

            binding.addressDetails.text = addressToShow


        })


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
         * @return A new instance of fragment ConfimationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfimationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
