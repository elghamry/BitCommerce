package sa.biotic.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentDeliveryAddressBinding
import sa.biotic.app.shared_prefrences_model.UserInfo

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
    var first: Boolean = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_delivery_address, container, false
        )

        binding.rememberComp.visibility = View.INVISIBLE

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

    override fun onResume() {
        super.onResume()

        if (!UserInfo.has_default_address && first) {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_deliveryAddressFragment_to_addressFragmentProfile)
            first = false
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
