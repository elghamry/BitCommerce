package sa.biotic.app.fragments

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
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
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.aminography.choosephotohelper.ChoosePhotoHelper
import com.bumptech.glide.Glide
import com.chibatching.kotpref.livedata.asLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.MediaType
import okhttp3.RequestBody
import sa.biotic.app.ProgressRequestBody
import sa.biotic.app.PurchaseActivity
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentProfileBinding
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.shared_prefrences_model.UserRoute
import sa.biotic.app.utils.margin
import java.io.ByteArrayOutputStream
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
 * [ProfileFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment(), ProgressRequestBody.UploadCallbacks {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var temp_tv: TextView? = null
    private var temp_image: ImageView? = null
    private var temp_card: CardView? = null
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var byteArray: ByteArray
    lateinit var mBitmap: Bitmap


    lateinit var binding: FragmentProfileBinding
    private lateinit var choosePhotoHelper: ChoosePhotoHelper
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

//        toolbar.navigationIcon?.setVisible(false,true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)


//        binding.textView4.setOnClickListener {
//            choosePhotoHelper.showChooser()
//
//        }
        UserInfo.asLiveData(UserInfo::image)
            .observe(viewLifecycleOwner, Observer<String> {

                Glide.with(this)
                    .load(UserInfo.image)
                    .placeholder(R.drawable.default_user_image_placeholder)
                    .into(binding.profileImg)


            })

        UserInfo.asLiveData(UserInfo::name)
            .observe(viewLifecycleOwner, Observer<String> {

                //                Glide.with(this)
////                    .load(UserInfo.image)
////                    .placeholder(R.drawable.default_user_image_placeholder)
////                    .into(binding.profileImg)
                binding.username.text = it


            })
        UserInfo.asLiveData(UserInfo::email)
            .observe(viewLifecycleOwner, Observer<String> {

                //                Glide.with(this)
////                    .load(UserInfo.image)
////                    .placeholder(R.drawable.default_user_image_placeholder)
////                    .into(binding.profileImg)
                binding.email.text = it


            })


        bottomNavigationView =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)
        UserRoute.asLiveData(UserRoute::next_step)
            .observe(viewLifecycleOwner, Observer<String> {
                Log.d("shareddata", it.toString())

                if (it == "purchase") {
//                    Navigation.findNavController(binding.root)
//                        .navigate(R.id.action_loginFragment_to_profileFragment)

                    val intent = Intent(activity, PurchaseActivity::class.java)
                    startActivityForResult(intent, 1)

                    bottomNavigationView.selectedItemId = R.id.cart


                }
            })


//        choosePhotoHelper = ChoosePhotoHelper.with(this)
//            .asFilePath()
//            .build(ChoosePhotoCallback {
////                Log.d("userResp",it)
//
//                Glide.with(this)
//                    .load(it)
//                    .into(binding.profileImg)
//
//                var file = File(it)
//
//                Log.d("userResp",file.path)
//                multipartImageUpload(file)
//
////                mBitmap = BitmapFactory.decodeFile(it)
////                getByteArrayInBackground()
//
////                imageView.setImageBitmap(mBitmap);mBitmap
//            })

        return binding.root
    }


//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        choosePhotoHelper.onActivityResult(requestCode, resultCode, data)
//    }
//
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        choosePhotoHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
//    }





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


            Navigation.findNavController(binding.root)
                .navigate(R.id.action_profileFragment_to_ordersFragment)

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

            Navigation.findNavController(binding.root)
                .navigate(R.id.action_profileFragment_to_helpFragment)

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


            Navigation.findNavController(binding.root)
                .navigate(R.id.action_profileFragment_to_contactUsFragment)

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


    private fun getByteArrayInBackground() {
        val thread: Thread = object : Thread() {
            override fun run() {
                val bos = ByteArrayOutputStream()
                mBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
                byteArray = bos.toByteArray()
                Log.d("userResp", "getByteArrayInBackground")
//                multipartImageUpload()
//                UiThreadStatement.runOnUiThread(Runnable {
////                    frameLayout.setVisibility(View.VISIBLE)
//                })
            }
        }
        thread.start()
    }


//     private fun initRetrofitClient() {
//        var client : OkHttpClient =  OkHttpClient.Builder().build()
//
//        //change the ip to yours.
//        apiService = new Retrofit.Builder().baseUrl("http://172.20.10.3:3000").client(client).build().create(ApiService.class);
//    }


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

    override fun onProgressUpdate(percentage: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFinish() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun uploadStart() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
