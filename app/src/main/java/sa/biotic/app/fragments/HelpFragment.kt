package sa.biotic.app.fragments

import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import sa.biotic.app.R
import sa.biotic.app.components.FillRequiredRule
import sa.biotic.app.components.NoSpecialCharacterRuleExcept
import sa.biotic.app.databinding.FragmentHelpBinding
import sa.biotic.app.model.ContactModel
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.ErrorDialog


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HelpFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HelpFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HelpFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    lateinit var binding: FragmentHelpBinding
    lateinit var bottomNavigationView: BottomNavigationView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, sa.biotic.app.R.layout.fragment_help, container, false
        )

        bottomNavigationView =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)
        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(sa.biotic.app.R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE
        //keyboard
        KeyboardVisibilityEvent.setEventListener(
            activity,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    Log.d(ContentValues.TAG, "onVisibilityChanged: Keyboard visibility changed")
                    if (isOpen) {
                        Log.d(ContentValues.TAG, "onVisibilityChanged: Keyboard is open")
                        bottomNavigationView.visibility = View.GONE
                        Log.d(ContentValues.TAG, "onVisibilityChanged: NavBar got Invisible")
                    } else {
                        Log.d(ContentValues.TAG, "onVisibilityChanged: Keyboard is closed")
                        bottomNavigationView.visibility = View.VISIBLE
                        Log.d(ContentValues.TAG, "onVisibilityChanged: NavBar got Visible")
                    }
                }
            })


        binding.etEmail.setText(UserInfo.NotificationEmail)

        binding.etPhone.setText(UserInfo.mobile)

        binding.etName.setText(UserInfo.name)

        binding.etEmail.isEnabled = false

        binding.etPhone.isEnabled = false


        binding.etName.isEnabled = false



        binding.saveBtn.setOnClickListener {
            var pass = true

            binding.etMessage.validator()
                .addRule(FillRequiredRule())
//                .atleastOneNumber()
//                .atleastOneSpecialCharacters()
                .addRule(NoSpecialCharacterRuleExcept())
//                .atleastOneUpperCase()
                .minLength(10)
                .addSuccessCallback {
                    binding.etMessageLayout.isErrorEnabled = false

                    pass = true
                }
                .addErrorCallback {
                    if (it.toString() == "Length should be greater than 10")
//                        binding.etMessageLayout.error = "Please fill the required field !"
                        ErrorDialog.showCustomViewDialog(
                            context = requireContext(),
                            custView = R.layout.dialog_error,
                            msg = "Please fill the required field !"
                        )
                    else
//                        binding.etMessageLayout.error = it
                        ErrorDialog.showCustomViewDialog(
                            context = requireContext(),
                            custView = R.layout.dialog_error,
                            msg = it
                        )





                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }
                .check()

            if (pass) {


                binding.saveBtn.isEnabled = false


                Repository.help(
                    ContactModel(
                        UserInfo.name,
                        UserInfo.email,
                        UserInfo.mobile,
                        binding.etMessage.text.toString()
                    )
                )
            }


        }


        Repository.helpResponse.observe(viewLifecycleOwner, Observer { newit ->
            //            binding.wordText.text = newWord


            newit.Status
            if (newit.Status == 1) {


                showCustomViewDialog()

                newit.Status = 99

                binding.saveBtn.isEnabled = true


            } else {
                if (newit.Status == -9) {
                    newit.Status = 99
                    binding.saveBtn.isEnabled = true
                }
            }


        })


        binding.etMessage.post(Runnable {
            binding.etMessage.requestFocusFromTouch()
            val lManager: InputMethodManager =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            lManager.showSoftInput(binding.etMessage, 0)
            binding.etMessage.setSelection(0)
        })


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun showCustomViewDialog(dialogBehavior: DialogBehavior = ModalDialog) {
        val dialog = MaterialDialog(requireContext(), dialogBehavior).show {
            //            title(R.string.googleWifi)
            cornerRadius(16f)
                .noAutoDismiss()
//            cancelable(false)  // calls setCancelable on the underlying dialog
            cancelOnTouchOutside(false)  // calls setCanceledOnTouchOutside on the underlying dialog

            customView(
                R.layout.dialog_change_password_done,
                scrollable = false,
                horizontalPadding = true
            )

        }


        // Setup custom view content
        val customView = dialog.getCustomView()
        val lottieDone = customView.findViewById<LottieAnimationView>(R.id.lottie_done)


        var tv = customView.findViewById<TextView>(R.id.tvPleaseVerify)

        tv.text = "Thanks for your message !"

        lottieDone.setAnimation("done.json")
//        binding.lottieConnection.setColorFilter(R.color.purple)

        lottieDone.playAnimation()
        lottieDone.loop(true)

        val okbtn: MaterialButton = customView.findViewById(R.id.ok_btn)
        okbtn.setOnClickListener {
            dialog.dismiss()

            Navigation.findNavController(binding.root).popBackStack(R.id.profileFragment, false)
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
         * @return A new instance of fragment HelpFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HelpFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
