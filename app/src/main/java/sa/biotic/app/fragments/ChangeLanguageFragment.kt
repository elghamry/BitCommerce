package sa.biotic.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentChangeLanguageBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChangeLanguageFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChangeLanguageFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangeLanguageFragment : Fragment() {

    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null
    lateinit var binding: FragmentChangeLanguageBinding
    private var temp_tv: TextView? = null
    private var temp_card: CardView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_change_language, container, false)
        cardsController()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun cardsController() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        binding.arabicCard.setOnClickListener {
            viewTempController()


            binding.arabicBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.arabicTv.setTextColor(resources.getColor(R.color.white))

            temp_card = binding.arabicBtn
            temp_tv = binding.arabicTv


        }
        binding.englishCard.setOnClickListener {
            viewTempController()
            binding.englishBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.englishTv.setTextColor(resources.getColor(R.color.white))
            temp_card = binding.englishBtn
            temp_tv = binding.englishTv

        }
    }

    private fun viewTempController() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if (temp_card != null) {
            temp_card?.setCardBackgroundColor(resources.getColor(android.R.color.transparent))
            temp_tv?.setTextColor(resources.getColor(R.color.text_header))
        }
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
         * @return A new instance of fragment ChangeLanguageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChangeLanguageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
