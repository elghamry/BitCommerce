package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Paint
import android.location.Address
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.interfaces.Item
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import sa.biotic.app.OnDetailsActivity
import sa.biotic.app.R
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.components.RatingBar
import sa.biotic.app.databinding.FragmentConfimationBinding
import sa.biotic.app.databinding.FragmentOrderInformationBinding
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.shared_prefrences_model.UserRoute
import sa.biotic.app.utils.TransitionHelper
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.ConfirmationViewModel
import sa.biotic.app.viewmodels.OrderInformationViewModel
import sa.biotic.app.viewmodels.PurchaseViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfimationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfimationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderInformationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentOrderInformationBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var viewModel: OrderInformationViewModel
    private lateinit var ordersAdapter: OneAdapter

    //for rating

    lateinit var  materialDialog: MaterialDialog
    private lateinit var rateAdapter: OneAdapter
    lateinit var rateItemsLocal: MutableList<OrderItemForRating>



    //for rating

    lateinit  var ItemIDs : MutableList<String>
    lateinit   var isbundles :  MutableList<String>
    lateinit  var Rates : MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(requireActivity()).get(OrderInformationViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_order_information, container, false


        )

        materialDialog = MaterialDialog(requireContext())

        ItemIDs = mutableListOf()
        isbundles = mutableListOf()
        Rates = mutableListOf()



        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE


        binding.yourOrderContainer.layoutManager = LinearLayoutManagerWrapper(
            requireContext(),
            RecyclerView.VERTICAL, false
        )

        binding.confirmBtn.setOnClickListener {
//            UserRoute.next_step = "home"
//            activity?.finish()
//            activity?.onBackPressed()

            Repository.getOrderPostboDetailsForRating(OrderRatingPostboModel(UserInfo.access_token,UserInfo.uid,UserInfo.device_token,requireArguments().getInt("order_no")))

        }

        binding.errorBtn.setOnClickListener {
//            UserRoute.next_step = "home"
//            activity?.finish()
            activity?.onBackPressed()
        }

        binding.trackBtn.setOnClickListener {

//            UserRoute.next_step = "home"
//            activity?.finish()

            var bundle = bundleOf("order_no" to requireArguments().getInt("order_no"))
            bundle.putString("type","upcoming")


            Navigation.findNavController(binding.root).navigate(R.id.getDriverLocationFragment,bundle)



//            activity?.onBackPressed()
        }




        if(!(arguments?.getString("type")=="past"))
        {
            binding.trackBtn.visibility=View.VISIBLE
            binding.confirmBtn.visibility=View.INVISIBLE
        }
        else{
            binding.trackBtn.visibility=View.INVISIBLE
            binding.confirmBtn.visibility=View.VISIBLE
        }

        viewModel.getOrderModel(GetOrderModel(UserInfo.access_token,requireArguments().getInt("order_no"),UserInfo.uid,UserInfo.device_token))

        adapterCreation()


//        if(UserInfo.signed){
//            Log.d("rating_is", "iam signed")
//
//            Repository.getOrderDetailsForRating(OrderRatingModel(UserInfo.access_token,UserInfo.uid,UserInfo.device_token))
//
//        }





        Repository.orderPostboRatingResponse.observe(viewLifecycleOwner, Observer { newit ->





            //


            if(newit.OrderItems.size>0 && !newit.OrderItems.isNullOrEmpty() &&
                ! materialDialog.isShowing

            ){

                if(!isbundles.isEmpty())
                    isbundles.clear()

                if(!ItemIDs.isEmpty())
                    ItemIDs.clear()

                if(!Rates.isEmpty())
                    Rates.clear()



                newit.OrderItems.forEach {
                    Log.d("rating_is","hello boy")
                    ItemIDs.add(it.ID.toString())
                    if(it.ISBundle){
                        isbundles.add("True")

                    }
                    else{
                        isbundles.add("False")
                    }
                    Rates.add(0.toString())
                }


//                isbundles :  MutableList<String>
//                 Rates : MutableList<String>

                rateItemsLocal = newit.OrderItems

                showCustomViewDialog(items = newit.OrderItems,order_no = newit.OrderNumber)



            }




         //   binding.swipeRefresh.isRefreshing=false
        })




        Repository.setItemRatingResponse.observe(viewLifecycleOwner, Observer { newit ->





            //


            if(newit.Status == 1){
                materialDialog.dismiss()
                Repository.orderPostboRatingResponse.value?.OrderItems?.clear()
                newit.Status = 0
                activity?.onBackPressed()


            }
            else
            {
                if(newit.Status == 3){
                    materialDialog.dismiss()
                    Repository.orderPostboRatingResponse.value?.OrderItems?.clear()
                    newit.Status = 0
                    activity?.onBackPressed()

                }
            }


         //   binding.swipeRefresh.isRefreshing=false
        })





        return binding.root
    }

    private fun adapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        ordersAdapter = OneAdapter(binding.yourOrderContainer)
            .attachItemModule(
                orderItem()
//                    .addEventHook(clickEventHook())
            )

        viewModel.orderInformationResponse.observe(viewLifecycleOwner, Observer<OrderInfoResponse> {

            Log.d("checkedOrd", it.toString())

            if(!it.OrderItems.isNullOrEmpty() && it.OrderItems.size>0 ){

                ordersAdapter.setItems(it.OrderItems)

                binding.orderNoValue.text=it.OrderNumber.toString()
                binding.orderDateValue.text=it.OrderDate
//            binding.shippingDateValue.text=it.ShippingDate
                binding.addressDetails.text = it.CustomerName + "\n" +
                        it.Country + "\n" +

                        it.AddressLine1.removeSuffix(", Saudi Arabia") + "\n" +
                        it.CustomerPhone

                binding.paymentTotalValue.text = it.TotalPrice.toString()+" "+getString(R.string._sar)

                UserInfo.promo = ""
                binding.confirmContainer.visibility = View.VISIBLE
                binding.errorContainer.visibility = View.GONE

                if(it.RateStatus == 1){
                    binding.confirmBtn.visibility=View.INVISIBLE

                    it.RateStatus = 0
                }
                else{
                    if(it.RateStatus != 3){
                        binding.confirmBtn.visibility=View.INVISIBLE
                        it.RateStatus = 0
                    }
                    else{
                        binding.confirmBtn.visibility=View.VISIBLE
                    }
                }

//                it.Status = false
            }
            else{
                binding.confirmContainer.visibility = View.GONE
                binding.errorContainer.visibility = View.VISIBLE

                val lottieDone = binding.lottieDone


                lottieDone.setAnimation("error.json")
//        binding.lottieConnection.setColorFilter(R.color.purple)

                lottieDone.playAnimation()
                lottieDone.loop(true)
//                it.Status = false
            }










        })





//        viewModel.checkoutResponse.observe(viewLifecycleOwner, Observer { newProds ->
//
////            if (newProds.size == 0) {
////                Repository.getHomeProducts(1, 6)
////            }
////            prodsAdapter = OneAdapter(binding.productsRecycler)
////                .attachItemModule(
////                    productItem()
////                        .addEventHook(clickProductEventHook())
////                )
////            prodsAdapter.setItems(newProds)
////
////            swipeRefresh.setRefreshing(false)
//
//
//        })


    }


    private fun orderItem(): ItemModule<OrderItem> = object : ItemModule<OrderItem>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.confirmation_order_black

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(requireContext(), R.animator.category_anim)
            }
        }


        override fun onBind(item: Item<OrderItem>, viewBinder: ViewBinder) {
            val quantity = viewBinder.findViewById<TextView>(R.id.product_no)
            val title = viewBinder.findViewById<TextView>(R.id.product_title)
            val price = viewBinder.findViewById<TextView>(R.id.price)
            val descr = viewBinder.findViewById<TextView>(R.id.product_description)

            var model = item.model

            quantity.text = "X"+model.Quantity.toString()
            title.text = model.Name_En
            price.text = model.Price.toString()+" "+getString(R.string._sar)
            if(!model.ProductsNamesEn.isNullOrBlank()){
                descr.text = model.ProductsNamesEn
            }
            else
            {
                descr.text= model.Description_En
            }









        }
    }




    private fun rateItem(): ItemModule<OrderItemForRating> = object : ItemModule<OrderItemForRating>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.rate_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@OrderInformationFragment.context,
                    R.animator.category_anim
                )
            }
        }


        @SuppressLint("SetTextI18n")
        override fun onBind(item: Item<OrderItemForRating>, viewBinder: ViewBinder) {
            val image = viewBinder.findViewById<ImageView>(R.id.product_image)
            val title = viewBinder.findViewById<TextView>(R.id.product_title)

//            val status = viewBinder.findViewById<TextView>(R.id.product_status)
            val line = viewBinder.findViewById<View>(R.id.line1)

            val rate = viewBinder.findViewById<RatingBar>(R.id.smart_rating_bar)

            var model = item.model

            rate.setOnRatingBarChangeListener { ratingBar, rating ->

                Rates[rateItemsLocal.indexOf(model)] = rating.toInt().toString()


                var btn =  materialDialog.findViewById<MaterialButton>(R.id.submit_btn)



                if(Rates.contains("0")) {


                    btn.setBackgroundColor(resources.getColor(R.color.oldPrice))
//                  btn.text = getString(R.string.not_available)
                    btn.isEnabled = false
                }
                else
                {

                    btn.setBackgroundColor(resources.getColor(R.color.colorPrimary))
//                  btn.text = getString(R.string.not_available)
                    btn.isEnabled = true
                }

            }


//            rate.setOnRatingBarChangeListener{ ratingBar, rating ->
//
//                Rates[rateItemsLocal.indexOf(model)] = rating.toInt().toString()
//
//            }
//            rate.setOnRatingBarChangeListener(this@HomeFragment)

            rate
//            rate.setOnRatingBarChangeListener { ratingBar, fl ->
//
//
////                Rates[rateItemsLocal.indexOf(model)] = ratingBar..toInt().toString()
//
//            }

            if(model.ISBundle){
//                status.text = getString(R.string.sold_out)
//                status.setTextColor(resources.getColor(R.color.stockColor))
            }

            else
            {
//                status.text =
//                    getString(R.string.only) + " " + model.ItemStockQuantity.toString() + " " + getString(
//                        R.string.left
//                    )
//                status.setTextColor(resources.getColor(R.color.colorPrimary))

            }

//            rateItemsLocal.find {  }


            Glide.with(this@OrderInformationFragment)
//                .load(model.img)
//
                .load(model.SImage).centerCrop().into(image)



            title.text = model.Name_En



            if(  rateItemsLocal.last() == model){
                line.visibility = View.INVISIBLE

            }
            else
            {
                line.visibility = View.VISIBLE
            }















        }
    }

    private fun showCustomViewDialog(dialogBehavior: DialogBehavior = ModalDialog, items : MutableList<OrderItemForRating>, order_no : Int) {
        val dialog = MaterialDialog(requireContext(), dialogBehavior).show {
            //            title(R.string.googleWifi)
            cornerRadius(16f)
                .noAutoDismiss()
//            cancelable(false)  // calls setCancelable on the underlying dialog
            cancelOnTouchOutside(false)  // calls setCanceledOnTouchOutside on the underlying dialog

            customView(
                R.layout.dialog_rate,
                scrollable = false,
                horizontalPadding = true
            )

        }

        materialDialog = dialog


        // Setup custom view content
        val customView = dialog.getCustomView()

        val updatedRecyclerView  = customView.findViewById<RecyclerView>(R.id.updated_rec)
        val order_tv  = customView.findViewById<TextView>(R.id.order_no_value)
        val submit_btn  = customView.findViewById<MaterialButton>(R.id.submit_btn)
        val not_btn  = customView.findViewById<MaterialButton>(R.id.not_now_btn)

        submit_btn.setBackgroundColor(resources.getColor(R.color.oldPrice))
        submit_btn.isEnabled = false

        order_tv.text = order_no.toString()



        updatedRecyclerView.layoutManager = LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        rateAdapter = OneAdapter(updatedRecyclerView).attachItemModule(
            rateItem()
//                .addEventHook(clickBundEventHook())
        )

        submit_btn.setOnClickListener {
            //            Log.d("rates_is",ItemIDs.toString())
//            Log.d("rates_is",isbundles.toString())
//            Log.d("rates_is",Rates.toString())


            if(!Rates.contains("0")) {
                var ids : String = ItemIDs[0]
                var rates : String  = Rates[0]
                var isbund : String = isbundles[0]

                if(ItemIDs.size>1)
                    for (i in 1..ItemIDs.size-1) {

                        ids = ids +"," + ItemIDs[i]
                        rates = rates +","+ Rates[i]
                        isbund = isbund+"," +  isbundles[i]


                    }



                Repository.setItemRating(
                    ItemRatingToSendModel(UserInfo.uid,UserInfo.access_token,UserInfo.device_token,
                        ids,isbund,rates,order_no,true))
            }

        }


        not_btn.setOnClickListener {

//            if(!Rates.contains("0")) {
                var ids : String = ItemIDs[0]
                var rates : String  = Rates[0]
                var isbund : String = isbundles[0]


                if(ItemIDs.size>1)
                    for (i in 1..ItemIDs.size-1) {

                        ids = ids +"," + ItemIDs[i]
                        rates = rates +","+ Rates[i]
                        isbund = isbund+"," +  isbundles[i]


                    }

                Repository.setItemRating(ItemRatingToSendModel(UserInfo.uid,UserInfo.access_token,UserInfo.device_token,
                    ids,isbund,rates,order_no,false))
//            }

        }

        rateAdapter.setItems(items)





//        val lottieDone = customView.findViewById<LottieAnimationView>(R.id.lottie_done)
//
//        lottieDone.setAnimation("done.json")
////        binding.lottieConnection.setColorFilter(R.color.purple)
//
//        lottieDone.playAnimation()
//        lottieDone.loop(true)



//        val okbtn: MaterialButton = customView.findViewById(R.id.ok_btn)
//        okbtn.setOnClickListener {
//            dialog.dismiss()
//
//            Navigation.findNavController(binding.root).popBackStack(R.id.profileFragment, false)
//        }
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
         * @return A new instance of fragment ConfimationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfimationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
