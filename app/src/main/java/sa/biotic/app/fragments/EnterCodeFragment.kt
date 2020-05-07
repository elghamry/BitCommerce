package sa.biotic.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentEnterCodeBinding
import sa.biotic.app.model.EnterCodeModel
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.viewmodels.EnterCodeViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [EnterCodeFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [EnterCodeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnterCodeFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null


    lateinit var binding: FragmentEnterCodeBinding
    lateinit var viewModel: EnterCodeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

        viewModel = ViewModelProviders.of(this).get(EnterCodeViewModel::class.java).apply {
            //
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_enter_code, container, false)
        binding = DataBindingUtil.inflate(
            inflater, sa.biotic.app.R.layout.fragment_enter_code, container, false
        )




        binding.submitBtn.setOnClickListener {

            viewModel.enterCode(
                EnterCodeModel(
                    binding.etCode.text.toString(),
                    UserInfo.forgetPasswordEmail
                )
            )


        }

        viewModel.enterCodeResponse.observe(viewLifecycleOwner, Observer { newit ->
            //            binding.wordText.text = newWord


            newit.Status
            if (newit.Status == 1) {

                Navigation.findNavController(binding.root)
                    .navigate(R.id.action_enterCodeFragment_to_changeResetPassword)

                newit.Status = 99


            } else {
                if (newit.Status == -9) {
                    newit.Status = 99
                }
            }


        })











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
         * @return A new instance of fragment EnterCodeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnterCodeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
