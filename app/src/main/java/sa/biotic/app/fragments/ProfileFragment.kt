package sa.biotic.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentProfileBinding
import sa.biotic.app.utils.margin

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var temp_tv: TextView? = null
    private var temp_image: ImageView? = null
    private var temp_card: CardView? = null


    lateinit var binding: FragmentProfileBinding
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_profile, container, false)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)


        cardsController()

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

    private fun cardsController() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        binding.manageAccountBtn.setOnClickListener {

            viewTempController()

            (it as CardView).setCardBackgroundColor(resources.getColor(R.color.purple))

            binding.manageAccount.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            binding.manageAccountTv.setTextColor(resources.getColor(R.color.white))


            temp_card = it
            temp_image = binding.manageAccount
            temp_tv = binding.manageAccountTv

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_profileFragment_to_accountMangementFragment)

        }






        binding.myOrdersBtn.setOnClickListener {

            viewTempController()

            (it as CardView).setCardBackgroundColor(resources.getColor(R.color.purple))

            binding.myOrders.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            binding.myOrdersTv.setTextColor(resources.getColor(R.color.white))


            temp_card = it
            temp_image = binding.myOrders
            temp_tv = binding.myOrdersTv

        }



        binding.helpBtn.setOnClickListener {

            viewTempController()

            (it as CardView).setCardBackgroundColor(resources.getColor(R.color.purple))

            binding.helpImage.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            binding.helpTv.setTextColor(resources.getColor(R.color.white))


            temp_card = it
            temp_image = binding.helpImage
            temp_tv = binding.helpTv

        }




        binding.contactUsBtn.setOnClickListener {

            viewTempController()

            (it as CardView).setCardBackgroundColor(resources.getColor(R.color.purple))

            binding.contactUsImage.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN
            )

            binding.contactUsTv.setTextColor(resources.getColor(R.color.white))


            temp_card = it
            temp_image = binding.contactUsImage
            temp_tv = binding.contactUsTv

        }


    }

    private fun viewTempController() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if (temp_card != null) {
            temp_card?.setCardBackgroundColor(resources.getColor(R.color.white))
            temp_image?.setColorFilter(
                ContextCompat.getColor(requireContext(), R.color.colorPrimary),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            temp_tv?.setTextColor(resources.getColor(R.color.line_color))


        }

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
////        listener = null
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
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
