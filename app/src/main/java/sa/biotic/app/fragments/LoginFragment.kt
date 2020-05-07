package sa.biotic.app.fragments


import android.content.ContentValues.TAG
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
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.chibatching.kotpref.livedata.asLiveData
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_login.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import sa.biotic.app.R
import sa.biotic.app.components.FillRequiredRule
import sa.biotic.app.databinding.FragmentLoginBinding
import sa.biotic.app.model.AddProductCartAfterLoginModel
import sa.biotic.app.model.LogoutModel
import sa.biotic.app.model.UserLoginModel
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.ErrorDialog
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.LoginViewModel


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoginFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var viewModel: LoginViewModel
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding: FragmentLoginBinding

    lateinit var mWaveDrawable: Wave
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java).apply {
            //
        }
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, sa.biotic.app.R.layout.fragment_login, container, false
        )

        bottomNavigationView =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)
//        UserRoute.asLiveData(UserRoute::next_step)
//            .observe(viewLifecycleOwner, Observer<String> {
//                //                Log.d("shared",it.toString())
//
//                if(UserRoute.next_step=="purchase"){
////                    Navigation.findNavController(binding.root)
////                        .navigate(R.id.action_loginFragment_to_profileFragment)
//
//                    val intent = Intent(activity, PurchaseActivity::class.java)
//                    startActivityForResult(intent, 1)
//
//                    bottomNavigationView.selectedItemId=R.id.cart
//
//
//
//                }
//            })


        UserInfo.asLiveData(UserInfo::signed)
            .observe(viewLifecycleOwner, Observer<Boolean> {
                //                Log.d("shared",it.toString())

                if (it) {

                    if (it == false) {
                        Log.d("hereiam", "iam here false from login ")

                    } else {
                        Log.d("hereiam", "iam here true from login ")
                        Navigation.findNavController(binding.root)
                            .navigate(R.id.action_loginFragment_to_profileFragment)
                    }
                }
            })

        /*
        Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
        control the toolbar
        */

        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(sa.biotic.app.R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(sa.biotic.app.R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE

        var remmber = true

        binding.etEmail.setText("")
        binding.etPassword.setText("")

        binding.forgetPass.setOnClickListener {

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_forgetPassFragment)
        }

        binding.rememberComp.setOnClickListener {

            if (remmber) {
                binding.loginRadio.setImageDrawable(resources.getDrawable(sa.biotic.app.R.drawable.radio))
                remmber = false
            } else {
                binding.loginRadio.setImageDrawable(resources.getDrawable(sa.biotic.app.R.drawable.radio_filled))
                remmber = true
            }
        }



        binding.loginBtn.setOnClickListener {

            var pass = true

            binding.etPassword.validator()
                .addRule(FillRequiredRule())
                .atleastOneNumber()
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()
                .minLength(8)
                .addSuccessCallback {
                    binding.etPasswordLayout.isErrorEnabled = false
                }
                .addErrorCallback {
                    binding.etPasswordLayout.error = it



                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }
                .check()


            binding.etEmail.validator()
                .addRule(FillRequiredRule())
                .validEmail()
                .addSuccessCallback {

                    binding.etEmailLayout.isErrorEnabled = false

                }
                .addErrorCallback {
                    binding.etEmailLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }
                .check()
            if (pass || (etEmail.text.toString() == "test" && etPassword.text.toString() == "test")) {


                var userLoginModel: UserLoginModel = UserLoginModel(
                    UserInfo.device_token
                    , etEmail.text.toString(), etPassword.text.toString()
                )


                viewModel.loginUser(userLoginModel)


            }
        }

        viewModel.userLoginResponseLiveData.observe(viewLifecycleOwner, Observer { userResponse ->

            if (userResponse.ValidationStatusNumber == -10) {
                showCustomViewDialog()
                UserInfo.signed = false
                userResponse.ValidationStatusNumber = 99


            } else {

                if (userResponse.ValidationStatusNumber == -9) {

                    ErrorDialog.showCustomViewDialog(
                        context = requireContext(),
                        custView = R.layout.dialog_error,
                        msg = "User Not Registered Before"
                    )
                    userResponse.ValidationStatusNumber = 99
                }
                if (userResponse.ValidationStatusNumber == -11) {

                    ErrorDialog.showCustomViewDialog(
                        context = requireContext(),
                        custView = R.layout.dialog_error,
                        msg = "Password is Worng"
                    )
                    userResponse.ValidationStatusNumber = 99
                }


            }


            if (userResponse.Status) {


                Log.d("hereiam", "statue fully hacked")



                UserInfo.access_token = userResponse.UserAccessTaken

                UserInfo.image = userResponse.UserImage
                UserInfo.uid = userResponse.UserID


//                        UserInfo.asLiveData(UserInfo::access_token)
//                            .observe(viewLifecycleOwner, Observer<String> {
//                                //                Log.d("shared",it.toString())
//
////                                if(UserInfo.signed){
////
////
////                                    Navigation.findNavController(binding.root)
////                                        .navigate(R.id.action_loginFragment_to_profileFragment)
//                                    Log.d("userResp","\n"+UserInfo.access_token+"\n"+UserInfo.signed+"\n"+UserInfo.image+"\n"+UserInfo.uid)
////
////                                }
//                            })

                userResponse.Status = false

                var get_user = LogoutModel(UserInfo.access_token, UserInfo.uid.toString())
                viewModel.getUserAccountData(get_user)

                Repository.addToCartAfterLogin(
                    AddProductCartAfterLoginModel(
                        UserInfo.uid,
                        UserInfo.access_token,
                        Repository.ProductIDsToString,
                        Repository.isbundlesToString,
                        Repository.QuantityToString
                    )
                )


//userResponse.Status=false

            }
//            else{
//                UserInfo.signed=false
//            }


//                    Log.d("userResp",UserInfo.signed.toString())


        })



        viewModel.userAccountDataResponseLiveData.observe(viewLifecycleOwner,
            Observer {


                if (!it.UserName.isEmpty() && !it.UserName.isBlank()) {
                    if (it.UserEmail.isNotEmpty() && it.UserEmail.isNotBlank()) {
                        UserInfo.email = it.UserEmail
                        if (it.NotificationEmail.isEmpty() && it.NotificationEmail.isBlank()) {
                            UserInfo.NotificationEmail = it.UserEmail

                        } else {
                            UserInfo.NotificationEmail = it.NotificationEmail
                        }

                        UserInfo.mobile = it.MobileNumber
                        UserInfo.name = it.UserName
                        UserInfo.signed = true


//here is the fucking problem

//                                binding.etEmail.setText("")
//                                binding.etPassword.setText("")


                    } else {
                        UserInfo.signed = false
                    }

                    it.UserName = ""
                }

//                Log.d("userResp",(it.UserEmail.isNotEmpty()&&it.UserEmail.isNotBlank()).toString())

            })

//        bottomNavigationView =
//            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)


        //keyboard
        KeyboardVisibilityEvent.setEventListener(
            activity,
            object : KeyboardVisibilityEventListener {
                override fun onVisibilityChanged(isOpen: Boolean) {
                    Log.d(TAG, "onVisibilityChanged: Keyboard visibility changed")
                    if (isOpen) {
                        Log.d(TAG, "onVisibilityChanged: Keyboard is open")
                        bottomNavigationView.visibility = View.GONE
                        Log.d(TAG, "onVisibilityChanged: NavBar got Invisible")
                    } else {
                        Log.d(TAG, "onVisibilityChanged: Keyboard is closed")
                        bottomNavigationView.visibility = View.VISIBLE
                        Log.d(TAG, "onVisibilityChanged: NavBar got Visible")
                    }
                }
            })




        binding.signup.setOnClickListener {

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }


        return binding.root
    }

    private fun showCustomViewDialog(dialogBehavior: DialogBehavior = ModalDialog) {
        val dialog = MaterialDialog(requireContext(), dialogBehavior).show {
            cornerRadius(16f)
            customView(R.layout.dialog_verify_email, scrollable = false, horizontalPadding = true)
//            positiveButton(R.string.connect) { dialog ->
//                // Pull the password out of the custom view when the positive button is pressed
////                val passwordInput: EditText = dialog.getCustomView()
////                    .findViewById(R.id.password)
////                toast("Password: $passwordInput")
//            }
//            negativeButton(android.R.string.cancel)
//            lifecycleOwner(this@MainActivity)
//            debugMode(debugMode)
        }

        // Setup custom view content
        val customView = dialog.getCustomView()

        val cancel: MaterialButton = customView.findViewById(R.id.cancel_btn)
        cancel.setOnClickListener {
            dialog.dismiss()
        }
    }


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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
