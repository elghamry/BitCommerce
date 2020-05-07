package sa.biotic.app.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.aminography.choosephotohelper.ChoosePhotoHelper
import com.aminography.choosephotohelper.callback.ChoosePhotoCallback
import com.bumptech.glide.Glide
import com.chibatching.kotpref.livedata.asLiveData
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.RequestBody
import sa.biotic.app.ProgressRequestBody
import sa.biotic.app.R
import sa.biotic.app.components.ProgressBottle
import sa.biotic.app.databinding.FragmentAccountMangementBinding
import sa.biotic.app.model.LogoutModel
import sa.biotic.app.model.UploadImageResponse
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.AccountMangementViewModel
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [AccountMangementFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [AccountMangementFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AccountMangementFragment : Fragment(), ProgressRequestBody.UploadCallbacks {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel: AccountMangementViewModel
    private var param1: String? = null
    private var param2: String? = null
    private var temp_tv: TextView? = null
    private var temp_card: CardView? = null
    var requestCode: Int = 0
    var resultCode: Int = 0
//    private var listener: OnFragmentInteractionListener? = null

    private lateinit var choosePhotoHelper: ChoosePhotoHelper


    lateinit var binding: FragmentAccountMangementBinding
    lateinit var progress: ProgressBottle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AccountMangementViewModel::class.java).apply {
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
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_account_mangement, container, false)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_account_mangement, container, false)


        cardsController()

        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)

        progress = (activity as AppCompatActivity).findViewById(R.id.myProgBar)
        progress.setTextMsg("uploading")
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE

        UserInfo.asLiveData(UserInfo::image)
            .observe(viewLifecycleOwner, Observer<String> {

                Glide.with(this)
                    .load(UserInfo.image)
                    .placeholder(R.drawable.default_user_image_placeholder)
                    .into(binding.profileImg)


            })

        UserInfo.asLiveData(UserInfo::signed)
            .observe(viewLifecycleOwner, Observer<Boolean> {

                //                Glide.with(this)
//                    .load(UserInfo.image)
//                    .placeholder(R.drawable.default_user_image_placeholder)
//                    .into(binding.profileImg)

                if (it == false) {
                    Log.d("hereiam", "iam here false ")
                    UserInfo.signed = false
                    Navigation.findNavController(binding.root)
                        .popBackStack(R.id.loginFragment, false)

                } else {
                    Log.d("hereiam", "iam here true ")
                }


            })


        viewModel.userLogoutResponseLiveData.observe(viewLifecycleOwner, Observer { userResponse ->

            //                UserInfo.access_token=userResponse.UserAccessTaken
//                UserInfo.signed=userResponse.Status
//                UserInfo.image=userResponse.UserImage
//                UserInfo.uid=userResponse.UserID

            if (userResponse.Status == 1 && UserInfo.signed) {

                userResponse.Status = 0
                Log.d("hereiam", "iam here logoutlive ")
//                UserInfo.clear()
//                Navigation.findNavController(binding.root).navigate(R.id.action_accountMangementFragment_to_loginFragment)

                UserInfo.access_token = "7amoo"
                UserInfo.signed = false

                Log.d(
                    "userResp",
                    "\n" + UserInfo.access_token + "\n" + UserInfo.signed + "\n" + UserInfo.image + "\n" + UserInfo.uid
                )
//                Navigation.findNavController(binding.root).navigate(R.id.action_accountMangementFragment_to_loginFragment)


            }


        })




        choosePhotoHelper = ChoosePhotoHelper.with(this)
            .asFilePath()
            .build(ChoosePhotoCallback {
                //                Log.d("userResp",it


                if (it != null) {
//                Glide.with(this)
//                    .load(it)
//                    .into(binding.profileImg)

                    var file = File(it)


//                choosePhotoHelper.onActivityResult(requestCode, resultCode, data)

//                Log.d("userResp",file.path)
                    progress.visibility = View.VISIBLE
                    multipartImageUpload(file)
                }

//                mBitmap = BitmapFactory.decodeFile(it)
//                getByteArrayInBackground()

//                imageView.setImageBitmap(mBitmap);mBitmap
            })


//        binding.chart.startAngle(0F)


        binding.changeIcon.setOnClickListener {

            choosePhotoHelper.showChooser(1)
        }
        binding.profileImg.setOnClickListener {
            choosePhotoHelper.showChooser(1)
        }





        return binding.root
    }

    private fun cardsController() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        binding.infoCard.setOnClickListener {
            viewTempController()


            binding.accInfoBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.infoTv.setTextColor(resources.getColor(R.color.white))

            temp_card = binding.accInfoBtn
            temp_tv = binding.infoTv

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountMangementFragment_to_informationFragment)



        }
        binding.deliveryCard.setOnClickListener {
            viewTempController()


            binding.deliveryBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.deliveryTv.setTextColor(resources.getColor(R.color.white))
            temp_card = binding.deliveryBtn
            temp_tv = binding.deliveryTv

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountMangementFragment_to_deliveryAddressFragment)



        }
        binding.passwordCard.setOnClickListener {
            viewTempController()


            binding.passwordBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.passTv.setTextColor(resources.getColor(R.color.white))
            temp_card = binding.passwordBtn
            temp_tv = binding.passTv
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountMangementFragment_to_changePasswordFragment)



        }
        binding.languageCard.setOnClickListener {
            viewTempController()


            binding.languageBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.lanTv.setTextColor(resources.getColor(R.color.white))
            temp_card = binding.languageBtn
            temp_tv = binding.lanTv
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_accountMangementFragment_to_changeLanguageFragment)


        }

        binding.logoutCard.setOnClickListener {
            viewTempController()


            binding.logoutBtn.setCardBackgroundColor(resources.getColor(R.color.colorPrimary))
            binding.logoutTv.setTextColor(resources.getColor(R.color.white))
            temp_card = binding.logoutBtn
            temp_tv = binding.logoutTv

            viewModel.logoutUser(LogoutModel(UserInfo.access_token, UserInfo.uid.toString()))


        }
    }

    private fun viewTempController() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if (temp_card != null) {
            temp_card?.setCardBackgroundColor(resources.getColor(android.R.color.transparent))
            temp_tv?.setTextColor(resources.getColor(R.color.text_header))
        }
    }

    private fun multipartImageUpload(file: File) {
//        initRetrofitClient()
        try {
//            if (byteArray != null) {

//                val filesDir: File = requireActivity().getApplicationContext().getFilesDir()
//                val file = File(filesDir, "image" + ".png")
//                val fos = FileOutputStream(file)
//                fos.write(byteArray)
//                Log.d("userResp",byteArray.toString())
//                fos.flush()
//                fos.close()
//                textView.setTextColor(Color.BLUE)

            var map: HashMap<String, RequestBody> = HashMap()

            val fileBody = ProgressRequestBody(file, this)

//                val body =
//                    MultipartBody.Part.createFormData("ggg", file.getName(), fileBody)


            val uid = RequestBody.create(MediaType.parse("text/plain"), UserInfo.uid.toString())
            val access_token =
                RequestBody.create(MediaType.parse("text/plain"), UserInfo.access_token)



            map.put("UserId", uid)
            map.put("AccessToken", access_token)
            map.put("Image\"; filename=\"${file.name}\"", fileBody)

            Repository.updateUserImage(map)


            viewModel.uploadImageResponseLiveData.observe(
                viewLifecycleOwner,
                Observer<UploadImageResponse> {
                    if (it.UserProfileImage.isNotBlank() && it.UserProfileImage.isNotEmpty())
                        UserInfo.image = it.UserProfileImage


                })

//                val req: Call<ResponseBody> = apiService.postImage(body, name)
//
//                req.enqueue(object : Callback<ResponseBody?>() {
//                    fun onResponse(
//                        call: Call<ResponseBody?>?,
//                        response: Response<ResponseBody?>
//                    ) {
//                        Toast.makeText(
//                            getApplicationContext(),
//                            response.code().toString() + " ",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    fun onFailure(
//                        call: Call<ResponseBody?>?,
//                        t: Throwable
//                    ) {
//                        textView.setText("Uploaded Failed!")
//                        textView.setTextColor(Color.RED)
//                        Toast.makeText(
//                            getApplicationContext(),
//                            "Request failed",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        t.printStackTrace()
//                    }
//                })
//            }
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        this.requestCode = requestCode
        this.resultCode = resultCode
        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
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
         * @return A new instance of fragment AccountMangementFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AccountMangementFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onProgressUpdate(percentage: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        progress.setTextMsg(percentage.toString() + " %")

    }

    override fun onError() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        progress.visibility = View.GONE

    }

    override fun onFinish() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        progress.visibility = View.GONE

    }

    override fun uploadStart() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        progress.visibility = View.VISIBLE
    }


}
