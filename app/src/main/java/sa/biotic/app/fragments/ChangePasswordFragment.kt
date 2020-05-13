package sa.biotic.app.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
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
import kotlinx.android.synthetic.main.activity_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import sa.biotic.app.R
import sa.biotic.app.components.FillRequiredRule
import sa.biotic.app.components.MissMatchRule
import sa.biotic.app.databinding.FragmentChangePasswordBinding
import sa.biotic.app.model.ChangePasswordModel
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.viewmodels.ChangePasswordViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ChangePasswordFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ChangePasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChangePasswordFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentChangePasswordBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var viewModel: ChangePasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ChangePasswordViewModel::class.java).apply {
            //
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_change_password, container, false
        )
        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE

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


        binding.saveBtn.setOnClickListener {


            var pass = true

            binding.etOldPassword.validator()
                .addRule(FillRequiredRule())
                .atleastOneNumber()
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()
                .minLength(8)
                .addErrorCallback {
                    binding.etOldPasswordLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etOldPasswordLayout.isErrorEnabled = false
                }
                .check()

            binding.etNewPassword.validator()
                .addRule(FillRequiredRule())
                .atleastOneNumber()
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()
                .minLength(8)
                .addErrorCallback {
                    binding.etNewPasswordlLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etNewPasswordlLayout.isErrorEnabled = false
                }
                .check()
            binding.etConfirmPassword.validator()
                .addRule(FillRequiredRule())
                .atleastOneNumber()
                .minLength(8)
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()

                .addRule(MissMatchRule(binding.etNewPassword.text.toString()))
                .addErrorCallback {
                    binding.etConfirmPasswordLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etConfirmPasswordLayout.isErrorEnabled = false
                }

                .check()

            if (pass) {
                viewModel.changePassword(
                    ChangePasswordModel(
                        UserInfo.access_token,
                        UserInfo.uid.toString(),
                        binding.etConfirmPassword.text.toString()
                        ,
                        binding.etNewPassword.text.toString(),
                        binding.etOldPassword.text.toString(),
                        UserInfo.device_token
                    )
                )
            }


        }


        viewModel.changePasswordResponse.observe(viewLifecycleOwner, Observer { newit ->
            //            binding.wordText.text = newWord


            newit.Status
            if (newit.Status == 1) {


                showCustomViewDialog()

                newit.Status = 99


            } else {

                Log.d("changePas", newit.Status.toString())
                if (newit.Status == -8) {

                    binding.etOldPasswordLayout.error = "Old Password in incorrect !"

                    binding.etOldPasswordLayout.isErrorEnabled = true

                    newit.Status = 99
                }
            }


        })




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
         * @return A new instance of fragment ChangePasswordFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ChangePasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
