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
import androidx.navigation.Navigation
import com.chibatching.kotpref.livedata.asLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import sa.biotic.app.R
import sa.biotic.app.components.FillRequiredRule
import sa.biotic.app.components.PhoneLengthRule
import sa.biotic.app.databinding.FragmentInformationBinding
import sa.biotic.app.model.LogoutModel
import sa.biotic.app.model.UpdateUserAccountDataModel
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [InformationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [InformationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class InformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    lateinit var binding: FragmentInformationBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_information, container, false
        )
        // Inflate the layout for this fragment


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE

        bottomNavigationView = (activity as AppCompatActivity).findViewById<BottomNavigationView>(
            R.id.nav_view
        )


        UserInfo.asLiveData(UserInfo::NotificationEmail)
            .observe(viewLifecycleOwner, Observer<String> {
                binding.etEmail.setText(it)

            })

        UserInfo.asLiveData(UserInfo::mobile)
            .observe(viewLifecycleOwner, Observer<String> {
                binding.etPhone.setText(it)

            })

        binding.etEmail.isEnabled = false
        binding.etPhone.isEnabled = false

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

        var isEdit: Boolean = true


        binding.saveBtn.setOnClickListener {


            if (binding.saveBtn.text == getString(R.string.edit)) {
                binding.etEmail.isEnabled = true
                binding.etPhone.isEnabled = true
                binding.etEmail.setText("")
                binding.etPhone.setText("")

                isEdit = false

            }


            binding.saveBtn.text = getString(R.string.save)

            if (isEdit) {
                var pass = true

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

                    Repository.updateUserAccountData(
                        UpdateUserAccountDataModel(
                            UserInfo.access_token,
                            UserInfo.uid.toString()
                            ,
                            binding.etEmail.text.toString(),
                            binding.etPhone.text.toString(),
                            UserInfo.device_token
                        )
                    )


                }

            }
            isEdit = true

        }






        Repository.updateUserInfo.observe(viewLifecycleOwner, Observer { newit ->
            //            binding.wordText.text = newWord


            newit.Status
            if (newit.Status == 1) {
                binding.saveBtn.text = getString(R.string.edit)
                binding.etEmail.isEnabled = false
                binding.etPhone.isEnabled = false
                Repository.getUserAccountData(
                    LogoutModel(
                        UserInfo.access_token,
                        UserInfo.uid.toString(),
                        UserInfo.device_token
                    )
                )
//                Navigation.findNavController(binding.root).navigate(R.id.action_forgetPassFragment_to_enterCodeFragment)
//                UserInfo.forgetPasswordEmail=binding.etEmail.text.toString()
                newit.Status = 99


            } else {

//                if(newit.Status==-9){
////                    newit.Status=99
//                }


            }


        })

        binding.cancelBtn.setOnClickListener {


            Navigation.findNavController(binding.root).popBackStack()
        }




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
         * @return A new instance of fragment InformationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            InformationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
