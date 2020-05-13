package sa.biotic.app.fragments

import android.os.Bundle
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
import androidx.navigation.Navigation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.activity_main.*
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentAddressBinding
import sa.biotic.app.utils.margin

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
class AddressFragmentProfile : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    lateinit var binding: FragmentAddressBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_address, container, false


        )

//        var stepper =
//            (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
//                R.id.stepper
//            )
//
//        stepper.goToNextStep()
//        stepper.goToNextStep()
//        binding.cancelBtn.setOnClickListener {
//            activity?.finish()
//        }

        binding.cancelBtn.visibility = MaterialButton.INVISIBLE

        binding.defaultAddressBtn.visibility = MaterialButton.INVISIBLE



        binding.defaultAddressBtn.setOnClickListener {
            //            Navigation.findNavController(binding.root)
//                .navigate(R.id.action_addressFragment_to_showAddressFragment)
        }


        binding.myLocationBtn.setOnClickListener {

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_addressFragmentProfile_to_getMyLocationFragmentProfile)
        }
        // Inflate the layout for this fragment


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE

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
                activity?.finish()

                Navigation.findNavController(binding.root)
                    .popBackStack(R.id.accountMangementFragment, false)





                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


//
//    override fun onSupportNavigateUp(): Boolean {
//        return currentNavController?.value?.navigateUp() ?: false
//    }


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
         * @return A new instance of fragment AddressFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddressFragmentProfile().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
