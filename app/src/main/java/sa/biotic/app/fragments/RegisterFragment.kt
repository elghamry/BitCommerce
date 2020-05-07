package sa.biotic.app.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.chibatching.kotpref.livedata.asLiveData
import com.github.ybq.android.spinkit.style.Wave
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_register.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import sa.biotic.app.R
import sa.biotic.app.components.FillRequiredRule
import sa.biotic.app.components.MissMatchRule
import sa.biotic.app.components.PhoneLengthRule
import sa.biotic.app.databinding.FragmentRegisterBinding
import sa.biotic.app.model.User
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.RegisterationViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [RegisterFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterationViewModel

    lateinit var mWaveDrawable: Wave
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


        viewModel = ViewModelProviders.of(this).get(RegisterationViewModel::class.java).apply {
            //
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_register, container, false)


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_register, container, false
        )


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE




        binding.registerBtn.setOnClickListener {

            var pass = true

            binding.etPassword.validator()
                .addRule(FillRequiredRule())
                .atleastOneNumber()
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()
                .minLength(8)
                .addErrorCallback {
                    binding.etPasswordLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etPasswordLayout.isErrorEnabled = false
                }
                .check()
            binding.etConPassword.validator()
                .addRule(FillRequiredRule())
                .atleastOneNumber()
                .minLength(8)
                .atleastOneSpecialCharacters()
                .atleastOneUpperCase()

                .addRule(MissMatchRule(binding.etPassword.text.toString()))
                .addErrorCallback {
                    binding.etConPasswordLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etConPasswordLayout.isErrorEnabled = false
                }

                .check()

            binding.etFirst.validator()
                .addRule(FillRequiredRule())
                .maxLength(35)
                .minLength(3)

                .addErrorCallback {
                    binding.etFirstLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etFirstLayout.isErrorEnabled = false
                }
                .check()

//            if(binding.etPhone.length()==9){
//                binding.etPhonelLayout.error = it
//            }
//            else{
//
//            }

            binding.etPhone.validator()
                .addRule(FillRequiredRule())
                .onlyNumbers()
                .addRule(PhoneLengthRule())
                .startsWith("5")
                .addErrorCallback {
                    binding.etPhonelLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }
                .addSuccessCallback {

                    binding.etPhonelLayout.isErrorEnabled = false
                }
                .check()

            binding.etLast.validator()
                .addRule(FillRequiredRule())
                .maxLength(35)
                .minLength(3)
                .addErrorCallback {
                    binding.etLastLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etLastLayout.isErrorEnabled = false
                }
                .check()

            binding.etEmail.validator()
                .addRule(FillRequiredRule())
                .validEmail()
                .addErrorCallback {
                    binding.etEmailLayout.error = it

                    pass = false


                    // it will contain the right message.
                    // For example, if edit text is empty,
                    // then 'it' will show "Can't be Empty" message
                }.addSuccessCallback {

                    binding.etEmailLayout.isErrorEnabled = false
                }
                .check()
            if (pass) {
//                binding.etPasswordLayout.isErrorEnabled=false
//                binding.etEmailLayout.isErrorEnabled=false


//                binding.registerBtn.text = ""
//                mWaveDrawable = Wave()
//                var paddingVal = 1
//                var right_bounds = 130
//
//                if (resources.displayMetrics.xdpi < 500) {
//                    paddingVal = 1
//                } else {
//                    paddingVal = 2
//                    right_bounds = 170
//
//                }
//                //binding.loginBtn.rippleColor
//                mWaveDrawable.setBounds(0, 0, right_bounds, 200)
//
//                //noinspection deprecation
//                mWaveDrawable.color = resources.getColor(R.color.white)
//
//
//
//                Log.d("density", resources.displayMetrics.toString())
//
//                binding.registerBtn.setPadding(
//                    0,
//                    (DensityUtils.dpToPx(paddingVal) * resources.displayMetrics.density).toInt(),
//                    0,
//                    0
//                )
//
//
//                binding.registerBtn.setCompoundDrawables(null, mWaveDrawable, null, null)
//
//                mWaveDrawable.start()


                var user: User = User(
                    etFirst.text.toString(),
                    etLast.text.toString(),
                    etEmail.text.toString(),
                    etPhone.text.toString(),
                    etConPassword.text.toString()
                    ,
                    etPassword.text.toString()
                )
                viewModel.registerUser(user)



                viewModel.userRegisterationResponseLiveData.observe(
                    viewLifecycleOwner,
                    Observer { userResponse ->

                        Log.d(
                            "userResp",
                            "\n" + userResponse.StatusDesc + "\n" + userResponse.ValidationStatusNumber
                        )


                        if (userResponse.Status) {

//                        UserInfo.access_token=userResponse.UserAccessTaken
//                        UserInfo.signed=userResponse.Status
//                        UserInfo.image=userResponse.UserImage
                            UserInfo.uid = userResponse.UserID
                            UserInfo.email = etEmail.text.toString()
                            UserInfo.validation_status_number = userResponse.ValidationStatusNumber


                            Navigation.findNavController(binding.root)
                                .navigate(R.id.action_registerFragment_to_regsiterCompletedFragment)

//
                            Log.d("userResp", "\n" + UserInfo.email)

//                        userResponse.Status=false
//                        userResponse.UserID=1000


                            UserInfo.asLiveData(UserInfo::access_token)
                                .observe(viewLifecycleOwner, Observer<String> {
                                    //                Log.d("shared",it.toString())

//                                if(UserInfo.signed){
//
//
//                                    Navigation.findNavController(binding.root)
//                                        .navigate(R.id.action_loginFragment_to_profileFragment)
                                    Log.d(
                                        "userResp",
                                        "\n" + UserInfo.access_token + "\n" + UserInfo.signed + "\n" + UserInfo.image + "\n" + UserInfo.uid
                                    )
//
//                                }
                                })


                        } else {

                            if (userResponse.UserID == 0) {
                                Toast.makeText(
                                    requireContext(),
                                    "Error in Registeration , try it later",
                                    Toast.LENGTH_LONG
                                ).show()
                            }


                        }


//                    Log.d("userResp",UserInfo.signed.toString())


                    })


            }
        }

        bottomNavigationView = (activity as AppCompatActivity).findViewById<BottomNavigationView>(
            R.id.nav_view
        )


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





        return binding.root


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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
