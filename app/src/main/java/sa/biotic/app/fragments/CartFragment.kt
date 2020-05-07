package sa.biotic.app.fragments


import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chibatching.kotpref.livedata.asLiveData
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook

import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import com.wajahatkarim3.easyvalidation.core.view_ktx.validator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cart.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import sa.biotic.app.PurchaseActivity
import sa.biotic.app.R
import sa.biotic.app.components.ElegantNumberButton
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.components.alerter.Alerter
import sa.biotic.app.components.alerter.OnHideAlertListener
import sa.biotic.app.components.alerter.OnShowAlertListener
import sa.biotic.app.databinding.FragmentCartBinding
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.shared_prefrences_model.UserRoute
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.CartViewModel
import java.text.DecimalFormat

/**
 * A placeholder fragment containing a simple view.
 */
class CartFragment : Fragment() {


    private lateinit var cartAdapter: OneAdapter
    private lateinit var cartBundAdapter: OneAdapter

    private lateinit var cartViewModel: CartViewModel
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var binding: FragmentCartBinding
    lateinit var alerter: Alerter
    lateinit var alert_view: LinearLayout
    lateinit var main_alert_background: LinearLayout
    lateinit var main_container: ConstraintLayout
    lateinit var alert_title: String
    lateinit var cartbundlesCopy: MutableList<Cartbundle>
    lateinit var cartproductsCopy: MutableList<Cartproduct>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java).apply {
            //
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (UserInfo.signed) {
            Repository.getCartDetails(
                GetCartDetailsModel(
                    UserInfo.uid,
                    UserInfo.access_token,
                    UserInfo.device_token
                )
            )
        } else {
            Repository.getCartDetails(GetCartDetailsModel(0, "rr", UserInfo.device_token))

        }


        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false
        )
        binding.cartRecycler.layoutManager =

            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)
        binding.carBundRecycler.layoutManager =

            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        cartAdapterCreation()

        setRecyclerViewItemTouchListener()
        setRecyclerViewBundleItemTouchListener()

//        cartViewModel.getTotalPrice()


//        cartViewModel.totalPrice.observe(viewLifecycleOwner, Observer { newit ->
//            //            cartAdapter.setItems(newit)
//
//            checkout.text =
//                getString(R.string.checkout) + "(" + newit.toString() + " " + getString(R.string._sar) + ")"
//            subtotal_value.text = newit.toString() + " " + getString(R.string._sar)
//
//            alert_title="Total Price : \n"+newit.toString()+" "+getString(R.string._sar)
//
//        })

        main_alert_background = (activity as AppCompatActivity).findViewById(R.id.Alert_main_back)
        main_container = (activity as AppCompatActivity).findViewById(R.id.main_container)

        binding.checkout.setOnClickListener {


            main_alert_background.visibility = ConstraintLayout.VISIBLE
            main_alert_background.background.alpha = 200
//
            alerter =
//                   alerter
                Alerter.create(requireActivity())


            alerter
                .setTitle(
//                    "X3 Rocky Road Protein bar\n" +
//                            "Peanut Butter 55g               12 SR"
                    alert_title
                )
//                .setText("Alert text...")
                .setDuration(200000)
                .enableSwipeToDismiss()
                .disableOutsideTouch()
                .setDismissable(true)
                .setOnClickListener(View.OnClickListener {
                    //                    Toast.makeText(this@ScrollingActivity, "OnClick Called", Toast.LENGTH_LONG).show();


                }).hideIcon()

                .setOnShowListener(OnShowAlertListener {

                    setViewAndChildrenEnabled(main_container, false)


                })
                .setOnHideListener(OnHideAlertListener {

                    main_alert_background.visibility = ConstraintLayout.GONE
                    setViewAndChildrenEnabled(main_container, true)

                })
                .show()
            alerter.getParentAlert()?.findViewById<MaterialButton>(R.id.contin)
                ?.setOnClickListener {
                    continueShoppingClick()
                }
            alerter.getParentAlert()?.findViewById<MaterialButton>(R.id.checkout)
                ?.setOnClickListener {
                    clickCheckout()
                }



        }


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE



        binding.applyPromo.setOnClickListener {
            var pass = false
            etPromoCode.validator()
                .minLength(5)

                .addSuccessCallback {
                    binding.etPromoCodeLayout.isErrorEnabled = false
                }
                .addErrorCallback {
                    binding.etPromoCodeLayout.error = "invalid Promo Code"



                    pass = false

                }
                .check()
//            if (etPromoCode.text.toString().toLowerCase() == "biotic19") {
//                pass = true
//                promo_code_value.text = etPromoCode.text.toString().toUpperCase()
//                binding.etPromoCodeLayout.isErrorEnabled = true
//                before_apply.visibility = ConstraintLayout.GONE
//                after_apply.visibility = ConstraintLayout.VISIBLE

            Repository.checkPromo(etPromoCode.text.toString())

//            } else {
                binding.etPromoCodeLayout.error = "invalid Promo Code"
//            }
        }


        binding.cancel.setOnClickListener {
            etPromoCode.text?.clear()
            after_apply.visibility = ConstraintLayout.GONE
            binding.beforeApply.visibility = ConstraintLayout.VISIBLE
        }



        bottomNavigationView =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)

        binding.start.setOnClickListener {
            bottomNavigationView.selectedItemId = R.id.home
        }
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


        UserRoute.asLiveData(UserRoute::next_step)
            .observe(viewLifecycleOwner, Observer<String> {
                Log.d("shareddata", it.toString())

                if (it == "home") {

                    bottomNavigationView.selectedItemId = R.id.home
                    UserRoute.next_step = "unknown"


                }
            })


        cartViewModel.updateQuantityLiveData.observe(viewLifecycleOwner, Observer { newit ->

            if (newit.Status == true) {

                if (UserInfo.signed) {
                    cartViewModel.getCartItemsOn(
                        GetCartDetailsModel(
                            UserInfo.uid,
                            UserInfo.access_token,
                            UserInfo.device_token
                        )
                    )
                } else {
                    cartViewModel.getCartItemsOn(
                        GetCartDetailsModel(
                            0,
                            "rr",
                            UserInfo.device_token
                        )
                    )
                }


                newit.Status = false
            }

        })



        cartViewModel.deletCartItemLiveData.observe(viewLifecycleOwner, Observer { newit ->

            if (newit.Status == true) {

                if (UserInfo.signed) {
                    cartViewModel.getCartItemsOn(
                        GetCartDetailsModel(
                            UserInfo.uid,
                            UserInfo.access_token,
                            UserInfo.device_token
                        )
                    )
                } else {
                    cartViewModel.getCartItemsOn(
                        GetCartDetailsModel(
                            0,
                            "rr",
                            UserInfo.device_token
                        )
                    )
                }


                newit.Status = false
            }

        })

        Repository.checkPromoResponse.observe(viewLifecycleOwner, Observer { newit ->

            if (newit.Status == true) {
                binding.promoPercent.text =
                    (newit.PromoDiscount * 100.toInt()).toString() + " " + "٪"
                binding.promoCodeValue.text = binding.etPromoCode.text.toString()
                binding.etPromoCodeLayout.isErrorEnabled = true
                before_apply.visibility = ConstraintLayout.GONE
                after_apply.visibility = ConstraintLayout.VISIBLE


//                pass=true


                newit.Status = false
            } else {
                binding.etPromoCodeLayout.error = "invalid Promo Code"
            }


        })


        return binding.root


    }


    private fun setRecyclerViewScrollListener() {
        binding.cartRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
//                if (!imageRequester.isLoadingData && totalItemCount == lastVisibleItemPosition + 1) {
//                    requestPhoto()
//                }

            }
        })

        setRecyclerViewItemTouchListener()
    }

    private fun cartAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.



        cartAdapter = OneAdapter(binding.cartRecycler).attachItemModule(
            cartItem()
//            .addEventHook(swipeEventHook())
        )
        cartBundAdapter = OneAdapter(binding.carBundRecycler).attachItemModule(
            cartBundleItem()
//            .addEventHook(swipeEventHook())
        )

//        cartAdapter = OneAdapter(binding.cartRecycler).attachItemModule(
//            cartItem()
////            .addEventHook(swipeEventHook())
//        )


//


//        cartViewModel.cartItemsLiveData.observe(viewLifecycleOwner, Observer { newit ->
//
//
////            cartAdapter.setItems(newit)
////            cartViewModel.getTotalPrice()
//
//            if(cartproductsCopy &&  )
//
//if(! UserInfo.signed &&cartproductsCopy.size==1 &&  cartbundlesCopy.size==1){
//    if (newit.size <= 0 ) {
//
//        var cart_layout = binding.cartLayout
//        cart_layout.visibility = ConstraintLayout.INVISIBLE
//        binding.emptyCartLayout.visibility = ConstraintLayout.VISIBLE
//
//    } else {
//        var cart_layout = binding.cartLayout
//        cart_layout.visibility = ConstraintLayout.VISIBLE
//        binding.emptyCartLayout.visibility = ConstraintLayout.INVISIBLE
//    }
//
//}
//
//
//
//
//
//            Log.d("cartSize", newit.size.toString())
////
////
////
//        })


        cartViewModel.getCartDetailsLiveData.observe(viewLifecycleOwner, Observer { newit ->


            cartAdapter.setItems(newit.cartproduct)
            cartBundAdapter.setItems(newit.cartbundles)

            cartproductsCopy = newit.cartproduct
            cartbundlesCopy = newit.cartbundles
            checkout.text =
                getString(R.string.checkout) + "(" + roundTwoDecimals(newit.TotalPrice + newit.TotalPrice * newit.Tax).toString() + " " + getString(
                    R.string._sar
                ) + ")"
            subtotal_value.text = newit.TotalPrice.toString() + " " + getString(R.string._sar)

            taxes_value.text =
                roundTwoDecimals(newit.TotalPrice * newit.Tax).toString() + " " + getString(R.string._sar)

            alert_title =
                "Total Price : \n" + roundTwoDecimals(newit.TotalPrice).toString() + " " + getString(
                    R.string._sar
                )
//            cartViewModel.getTotalPrice()


            if (newit.cartproduct.size <= 0 && newit.cartbundles.size <= 0) {

                var cart_layout = binding.cartLayout
                cart_layout.visibility = ConstraintLayout.INVISIBLE
                binding.emptyCartLayout.visibility = ConstraintLayout.VISIBLE

            } else {
                var cart_layout = binding.cartLayout
                cart_layout.visibility = ConstraintLayout.VISIBLE
                binding.emptyCartLayout.visibility = ConstraintLayout.INVISIBLE
            }






            Log.d("cartSize", newit.cartproduct.size.toString())
//
//
//
        })


    }

    private fun clickEventHook(): ClickEventHook<CartItem> = object : ClickEventHook<CartItem>() {
        override fun onClick(model: CartItem, viewBinder: ViewBinder) {
            Toast.makeText(context, "${model.name} clicked", Toast.LENGTH_SHORT).show()


        }


    }


    private fun cartProduct(): ItemModule<Cartproduct> = object : ItemModule<Cartproduct>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.cart_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@CartFragment.context,
                    R.animator.category_anim
                )
            }
        }


        @SuppressLint("SetTextI18n")
        override fun onBind(model: Cartproduct, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 =
                viewBinder.findViewById<ElegantNumberButton>(R.id.number_button2)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.price)
            val story6 = viewBinder.findViewById<TextView>(R.id.each_price)
            val story7 = viewBinder.findViewById<View>(R.id.line1)
            val story8 = viewBinder.findViewById<TextView>(R.id.old_price)

//            Log.d("hello", model.toString())
//            if (model.id == cartViewModel.getCartDetailsLiveData.value?.size?.toLong()) {
//                story7.setBackgroundColor(resources.getColor(R.color.text_header))
//
//            }


//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

////
            Glide.with(this@CartFragment)
//                .load(model.img)
//
                .load(model.SImage).centerCrop().into(story1)


            story2.text = model.Name_En

            story3.number = model.CartQuantity.toString()

            var newVal: Int = 0



            story3.setOnValueChangeListener { view, oldValue, newValue ->

                //add quantitiy listener and get total price
//                Repository.cartLocalItems.value?.get(((model.id - 1).toInt()))?.quantity = newValue
//
//                newVal = newValue
//
//                story5.text =
//                    (model.price.toFloat() * model.quantity).toString() + " " + getString(R.string._sar)

//                cartViewModel.getTotalPrice()
            }

            story4.text = model.Description_En
//            if (newVal != model.quantity) {
//                story5.text =
//                    (model.price.toFloat() * model.quantity).toString() + " " + getString(R.string._sar)
//            }

            story5.text =
                (model.Price.toFloat() * model.CartQuantity).toString() + " " + getString(R.string._sar)

            story6.text = model.Price + " " + getString(R.string._sar_each)
            story8.text = model.ProductOfferPrice


//            story3.ratingNum = model.rate.toFloat()


//            story2.setText(model.title)


        }
    }


    private fun cartItem(): ItemModule<Cartproduct> = object : ItemModule<Cartproduct>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.cart_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@CartFragment.context,
                    R.animator.category_anim
                )
            }
        }


        @SuppressLint("SetTextI18n")
        override fun onBind(model: Cartproduct, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 =
                viewBinder.findViewById<ElegantNumberButton>(R.id.number_button2)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.price)
            val story6 = viewBinder.findViewById<TextView>(R.id.each_price)
            val story7 = viewBinder.findViewById<View>(R.id.line1)
            val story8 = viewBinder.findViewById<TextView>(R.id.old_price)
            val story9 = viewBinder.findViewById<CardView>(R.id.discount_card)
            val story10 = viewBinder.findViewById<TextView>(R.id.discount_value)
            val story11 = viewBinder.findViewById<TextView>(R.id.stock_tv)
            val story12 = viewBinder.findViewById<RelativeLayout>(R.id.counter_box)
//            val story8 = viewBinder.findViewById<LinearLayout>(R.id.backgroundContainer)

//            viewBinder.getRootView().swipeContainer.setOnLongClickListener {
//                story8.visibility= LinearLayout.VISIBLE
//                true
//
////            }
//            viewBinder.getRootView().swipeContainer.setOnClickListener {
//                story8.visibility= LinearLayout.VISIBLE
//            }
//            viewBinder.getRootView().swipeContainer.setOnSwipeListener(object : OnSwipeListener {
//                override fun onSwipe(isExpanded: Boolean) {
//                    model.isExpanded = isExpanded
//
//
//
//                    if(model.isExpanded)
//                        Repository.deletItemCartLocal(model)
//
//
//
//                }
//
//            })


//
//            viewBinder.getRootView().swipeContainer.apply(model.isExpanded)

//            if (model.id == cartViewModel.cartItemsLiveData.value?.size?.toLong()) {
//                story7.setBackgroundColor(resources.getColor(R.color.text_header))
//
//            }


//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

////
            story5.text =
                (model.Price.toFloat() * model.CartQuantity).toString() + " " + getString(R.string._sar)

            story6.text = model.Price + " " + getString(R.string._sar_each)
            if (model.ProductOfferDicountValue.toFloat() > 0F) {

                story9.visibility = CardView.VISIBLE
                story10.text =
                    (model.ProductOfferDicountValue.toFloat() * 100).toInt().toString() + "%"
                story8.text =
                    (model.Price.toFloat() * model.CartQuantity).toString() + " " + getString(R.string._sar)
                story8.visibility = View.VISIBLE
                story8.paintFlags = story8.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG

                story5.text =
                    (model.ProductOfferPrice.toFloat() * model.CartQuantity).toString() + " " + getString(
                        R.string._sar
                    )
                story6.text = model.ProductOfferPrice + " " + getString(R.string._sar_each)


            }

            if (model.CartQuantity > 1) {
                story6.visibility = View.VISIBLE
            } else {
                story6.visibility = View.INVISIBLE
            }
            Glide.with(this@CartFragment)
//                .load(model.img)
//
                .load(model.SImage).centerCrop().into(story1)


            story2.text = model.Name_En
            story3.setRange(1, model.ProductStockQuantity)

            story3.number = model.CartQuantity.toString()

            var newVal: Int = 0







            story3.setOnValueChangeListener { view, oldValue, newValue ->


                //                Repository.cartLocalItems.value?.get(((model.id - 1).toInt()))?.quantity = newValue
//
                newVal = newValue





                if (UserInfo.signed) {

                    var item: UpdateCartItemQuantityModel = UpdateCartItemQuantityModel(
                        UserInfo.uid, UserInfo.access_token,
                        model.ID, false, newVal, UserInfo.device_token
                    )
                    cartViewModel.updateCartItemQuantity(item)
                } else {
//                    updateQuantityLocal(model,null,"product",newVal)

                    var item: UpdateCartItemQuantityModel = UpdateCartItemQuantityModel(
                        0, "rr",
                        model.ID, false, newVal, UserInfo.device_token
                    )
                    cartViewModel.updateCartItemQuantity(item)
                }



            }

            story4.text = model.Description_En


            if (model.ProductStockQuantity <= 5) {
                story11.visibility = View.VISIBLE
                if (model.ProductStockQuantity <= 0) {

                    story11.text = getString(R.string.sold_out)
                    story11.setTextColor(resources.getColor(R.color.stockColor))
//                    story3.visibility=TextView.INVISIBLE
                    story6.visibility = TextView.INVISIBLE
                    story5.visibility = TextView.INVISIBLE
                    story8.visibility = TextView.INVISIBLE
                    story12.visibility = View.INVISIBLE

                } else
                    story11.text =
                        getString(R.string.only) + " " + model.ProductStockQuantity.toString() + " " + getString(
                            R.string.left
                        )
            } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
                story11.visibility = TextView.INVISIBLE
            }
//            if (newVal != model.quantity) {
//                story5.text =
//                    (model.price.toFloat() * model.quantity).toString() + " " + getString(R.string._sar)
//            }


//            story3.ratingNum = model.rate.toFloat()


//            story2.setText(model.title)


        }
    }


    private fun cartBundleItem(): ItemModule<Cartbundle> = object : ItemModule<Cartbundle>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.cart_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@CartFragment.context,
                    R.animator.category_anim
                )
            }
        }


        @SuppressLint("SetTextI18n")
        override fun onBind(model: Cartbundle, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 =
                viewBinder.findViewById<ElegantNumberButton>(R.id.number_button2)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.price)
            val story6 = viewBinder.findViewById<TextView>(R.id.each_price)
            val story7 = viewBinder.findViewById<View>(R.id.line1)
            val story8 = viewBinder.findViewById<TextView>(R.id.old_price)
            val story9 = viewBinder.findViewById<CardView>(R.id.discount_card)
            val story10 = viewBinder.findViewById<TextView>(R.id.discount_value)
            val story11 = viewBinder.findViewById<TextView>(R.id.stock_tv)
            val story12 = viewBinder.findViewById<RelativeLayout>(R.id.counter_box)
//            val story8 = viewBinder.findViewById<LinearLayout>(R.id.backgroundContainer)

//            viewBinder.getRootView().swipeContainer.setOnLongClickListener {
//                story8.visibility= LinearLayout.VISIBLE
//                true
//
////            }
//            viewBinder.getRootView().swipeContainer.setOnClickListener {
//                story8.visibility= LinearLayout.VISIBLE
//            }
//            viewBinder.getRootView().swipeContainer.setOnSwipeListener(object : OnSwipeListener {
//                override fun onSwipe(isExpanded: Boolean) {
//                    model.isExpanded = isExpanded
//
//
//
//                    if(model.isExpanded)
//                        Repository.deletItemCartLocal(model)
//
//
//
//                }
//
//            })


//
//            viewBinder.getRootView().swipeContainer.apply(model.isExpanded)

//            if (model.id == cartViewModel.cartItemsLiveData.value?.size?.toLong()) {
//                story7.setBackgroundColor(resources.getColor(R.color.text_header))
//
//            }


//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

////
            story5.text =
                (model.Price.toFloat() * model.CartQuantity).toString() + " " + getString(R.string._sar)

            story6.text = model.Price + " " + getString(R.string._sar_each)


            story3.setRange(1, model.BundleStockAvaliable)
//            if(model.ProductOfferDicountValue.toFloat()>0F){
//
//                story9.visibility = CardView.VISIBLE
//                story10.text=(model.ProductOfferDicountValue.toFloat()*100).toInt().toString()+"%"
//                story8.text=(model.Price.toFloat() * model.CartQuantity).toString() + " " + getString(R.string._sar)
//                story8.visibility = View.VISIBLE
//                story8.paintFlags = story8.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//
//                story5.text =
//                    (model.ProductOfferPrice.toFloat() * model.CartQuantity).toString() + " " + getString(R.string._sar)
//                story6.text = model.ProductOfferPrice + " " + getString(R.string._sar_each)
//
//
//
//
//            }

            if (model.CartQuantity > 1) {
                story6.visibility = View.VISIBLE
            } else {
                story6.visibility = View.INVISIBLE
            }
            Glide.with(this@CartFragment)
//                .load(model.img)
//
                .load(model.SImage).centerCrop().into(story1)


            story2.text = model.Name_En

            story3.number = model.CartQuantity.toString()

            var newVal: Int = 0



            story3.setOnValueChangeListener { view, oldValue, newValue ->

                newVal = newValue

                if (UserInfo.signed) {

                    var item: UpdateCartItemQuantityModel = UpdateCartItemQuantityModel(
                        UserInfo.uid, UserInfo.access_token,
                        model.ID, true, newVal, UserInfo.device_token
                    )
                    cartViewModel.updateCartItemQuantity(item)
                } else {
//                    updateQuantityLocal(null,model,"bundle",newVal)
                    var item: UpdateCartItemQuantityModel = UpdateCartItemQuantityModel(
                        0, "rr",
                        model.ID, true, newVal, UserInfo.device_token
                    )
                    cartViewModel.updateCartItemQuantity(item)
                }


            }

            story4.text = model.Description_En


            if (model.BundleStockAvaliable <= 5) {
                story11.visibility = View.VISIBLE
                if (model.BundleStockAvaliable <= 0) {

                    story11.text = getString(R.string.sold_out)
                    story11.setTextColor(resources.getColor(R.color.stockColor))
//                    story3.visibility=TextView.INVISIBLE
                    story6.visibility = TextView.INVISIBLE
                    story5.visibility = TextView.INVISIBLE
                    story8.visibility = TextView.INVISIBLE
                    story12.visibility = View.INVISIBLE

                } else
                    story11.text =
                        getString(R.string.only) + " " + model.BundleStockAvaliable.toString() + " " + getString(
                            R.string.left
                        )
            } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
                story11.visibility = TextView.INVISIBLE
            }
//            if (newVal != model.quantity) {
//                story5.text =
//                    (model.price.toFloat() * model.quantity).toString() + " " + getString(R.string._sar)
//            }



//            story3.ratingNum = model.rate.toFloat()


//            story2.setText(model.title)


        }
    }


    private fun setRecyclerViewItemTouchListener() {

        //1
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3

                val position = viewHolder.adapterPosition



                if (UserInfo.signed) {
                    var item: DeleteCartItemModel = DeleteCartItemModel(
                        UserInfo.uid, UserInfo.access_token,
                        cartproductsCopy[position].ID, false, UserInfo.device_token
                    )
                    cartViewModel.deletCartItem(item)
                } else {

//                    var innerItem : CartItem? =   Repository.cartItems.find {
//                        it.typeId ==cartproductsCopy[position].ID &&
//                                ( it.type == "product" || it.type == "productOff")
//                    }
//                    Repository.deletItemCartLocal(
//                        innerItem
//                    )


                    var item: DeleteCartItemModel = DeleteCartItemModel(
                        0, "rr",
                        cartproductsCopy[position].ID, false, UserInfo.device_token
                    )
                    cartViewModel.deletCartItem(item)


                }


//                Repository.deletItemCartLocal(Repository.cartItems[position])
//                photosList.removeAt(position)
//                recyclerView.adapter!!.notifyItemRemoved(position)
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.START)
                return super.getMovementFlags(recyclerView, viewHolder)
            }

//                @Override
//    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(dragFlags, swipeFlags);
//    }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                paintSwipeLeft(c, dX, viewHolder.itemView)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.cartRecycler)
    }


    private fun setRecyclerViewBundleItemTouchListener() {

        //1
        val itemTouchCallback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                viewHolder1: RecyclerView.ViewHolder
            ): Boolean {
                //2
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                //3

                val position = viewHolder.adapterPosition


                if (UserInfo.signed) {

                    var item: DeleteCartItemModel = DeleteCartItemModel(
                        UserInfo.uid, UserInfo.access_token,
                        cartbundlesCopy[position].ID, true, UserInfo.device_token
                    )
                    cartViewModel.deletCartItem(item)
                } else {

//                    var innerItem : CartItem? =   Repository.cartItems.find {
//                        it.typeId ==cartbundlesCopy[position].ID &&
//                                it.type == "bundle"
//                    }
//                    Repository.deletItemCartLocal(
//                        innerItem
//                    )

                    var item: DeleteCartItemModel = DeleteCartItemModel(
                        0, "rr",
                        cartbundlesCopy[position].ID, true, UserInfo.device_token
                    )
                    cartViewModel.deletCartItem(item)

//                    Repository.getCartDetailsNoLogin(Repository.prodsLocal,Repository.priceLocal,Repository.isbundLocal,Repository.offeridsLocal,
//                        Repository.discountLocal,Repository.stockquanLocal,Repository.quantityLocal)

                }


//                Repository.deletItemCartLocal(Repository.cartItems[position])
//                photosList.removeAt(position)
//                recyclerView.adapter!!.notifyItemRemoved(position)
            }

            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return makeMovementFlags(0, ItemTouchHelper.START)
                return super.getMovementFlags(recyclerView, viewHolder)
            }

//                @Override
//    public int getMovementFlags(RecyclerView recyclerView, ViewHolder viewHolder) {
//        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
//        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
//        return makeMovementFlags(dragFlags, swipeFlags);
//    }

            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {

                paintSwipeLeft(c, dX, viewHolder.itemView)
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
            }
        }

        //4
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.carBundRecycler)
    }


//    private fun swipeEventHook(): SwipeEventHook<CartItem> {
//        return object : SwipeEventHook<CartItem>() {
//            override fun onSwipe(canvas: Canvas, xAxisOffset: Float, viewBinder: ViewBinder) {
//                when {
//                    xAxisOffset < 0 -> paintSwipeLeft(canvas, xAxisOffset, viewBinder.getRootView())
//                    xAxisOffset > 0 -> paintSwipeRight(
//                        canvas,
//                        xAxisOffset,
//                        viewBinder.getRootView()
//                    )
//                }
//            }
//
//            override fun onSwipeComplete(
//                model: CartItem,
//                direction: SwipeDirection,
//                viewBinder: ViewBinder
//            ) {
//                when (direction) {
//                    SwipeDirection.Left -> {
//                        Repository.deletItemCartLocal(model)
////                        cartAdapter.remove(model)
//
//                    }
////                    SwipeDirection.Right -> {
////                        Repository.deletItemCartLocal(model)
//////                        Toast.makeText(this@SwipeEventHookActivity, "${model.title} snoozed", Toast.LENGTH_SHORT).show()
//////                        cartAdapter.update(model)
////
////
////                    }
//                }
//            }
//        }
//    }

    private fun paintSwipeRight(canvas: Canvas, xAxisOffset: Float, rootView: View) {
        val icon = ContextCompat.getDrawable(this@CartFragment.requireContext(), R.drawable.search)
        val colorDrawable = ColorDrawable(Color.DKGRAY)

        rootView.setOnTouchListener { view, motionEvent ->
            view.isEnabled = false
            view.isClickable = false
            view.isHapticFeedbackEnabled = false
            view.isPressed = false
            view.isActivated = false
            view.isScrollContainer = false
            view.isNestedScrollingEnabled = false


            true
        }

        rootView.setOnDragListener { view, dragEvent ->

            view.isEnabled = false
            view.isClickable = false
            view.isHapticFeedbackEnabled = false
            view.isPressed = false
            view.isActivated = false
            view.isScrollContainer = false
            view.isNestedScrollingEnabled = false
            true
        }

        rootView.setOnLongClickListener {
            it.isEnabled = false
            it.isClickable = false
            it.isHapticFeedbackEnabled = false
            it.isPressed = false
            it.isActivated = false
            it.isScrollContainer = false
            it.isNestedScrollingEnabled = false
            true

        }

        rootView.isEnabled = false
        rootView.isClickable = false
        rootView.isHapticFeedbackEnabled = false
        rootView.isPressed = false
        rootView.isActivated = false
        rootView.isScrollContainer = false
        rootView.isNestedScrollingEnabled = false
        icon?.let {
            //            val middle = rootView.bottom - rootView.top
//            var top = rootView.top
//            var bottom = rootView.bottom
//            var right = rootView.left + xAxisOffset.toInt()
//            var left = rootView.left
//            colorDrawable.setBounds(left, top, right, bottom)
//            colorDrawable.draw(canvas)
//
//            top = rootView.top + (middle / 2) - (it.intrinsicHeight / 2)
//            bottom = top + it.intrinsicHeight
//            left = rootView.left + ICON_MARGIN
//            right = left + it.intrinsicWidth
//            it.setBounds(left, top, right, bottom)
//            it.draw(canvas)
        }
    }

    private fun paintSwipeLeft(canvas: Canvas, xAxisOffset: Float, rootView: View) {
        val icon =
            ContextCompat.getDrawable(this@CartFragment.requireContext(), R.drawable.delete_icon)
        val colorDrawable = ColorDrawable(resources.getColor(R.color.red))

        icon?.let {
            val middle = rootView.bottom - rootView.top
            var top = rootView.top
            var bottom = rootView.bottom
            var right = rootView.right
            var left = rootView.right + xAxisOffset.toInt()
            colorDrawable.setBounds(left, top, right, bottom)
            colorDrawable.draw(canvas)

            top = rootView.top + (middle / 2) - (it.intrinsicHeight / 2)
            bottom = top + it.intrinsicHeight
            right = rootView.right - ICON_MARGIN
            left = right - it.intrinsicWidth
            it.setBounds(left, top, right, bottom)
            it.draw(canvas)
        }
    }


    private fun setViewAndChildrenEnabled(view: View, enabled: Boolean) {
        view.isEnabled = enabled
        if (view is ViewGroup) {
            for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                setViewAndChildrenEnabled(child, enabled)
            }
        }
    }

    fun clickCheckout() {

        alerter.alert?.hide()



        main_alert_background.visibility = ConstraintLayout.GONE
        setViewAndChildrenEnabled(main_container, true)
        if (UserInfo.signed) {
            val intent = Intent(activity, PurchaseActivity::class.java)
//                intent.putExtra("product_name", model.title)
//                intent.putExtra("product_image", model.img)
//                intent.putExtra("product_price", model.price)

//            intent.putExtra(EXTRA_MESSAGE, message)
            startActivityForResult(intent, 1)

        } else {
//                        Navigation.findNavController(binding.root)
//                            .navigate(R.id.action_loginFragment_to_profileFragment)
            UserRoute.next_step = "purchase"
            bottomNavigationView.selectedItemId = R.id.profile
        }


    }

    fun updateQuantityLocal(
        product: Cartproduct?,
        bundle: Cartbundle?,
        type: String,
        quantity: Int
    ) {
        var no_items = Repository.cartLocalItems.value?.size!!
        if (type == "bundle") {


//             item.Isbundle=true


//             Log.d("nofitem", "bundle")
//             val bundle: BundleProduct? = intent.getParcelableExtra("BundleItem")
//             item.ProductID=bundle!!.BundleID

            Repository.addItemCartLocal(
                CartItem(
                    no_items + 1.toLong(),
                    bundle!!.ID
                    ,
                    -1,
                    "bundle",
                    bundle.SImage,
                    bundle.Name_En,
                    bundle.ProductsNamesEn,
                    quantity,
                    bundle.BundleStockAvaliable,
                    0F.toString(),
                    bundle.Price,
                    false
                )
            )


//            product_name.text = bundle?.BundleName_En
//            product_price.text = bundle?.BundlePrice + " " + getString(R.string._sar)
//            calories.visibility = TextView.INVISIBLE
//            cal_icon.visibility = ImageView.INVISIBLE
//            viewPager.adapter = sectionsPagerAdapter


        } else {

//             if (type == "product") {


//                 Log.d("nofitem", "product")
////                viewPager.adapter = sectionsPagerAdapterNotBundle
//                 val product: Product? = intent.getParcelableExtra("ProductItem")
//
//                 item.ProductID=product!!.ProductID
            if (product!!.ProductOfferDicountValue.toFloat() > 0F) {


                Repository.addItemCartLocal(
                    CartItem(
                        no_items + 1.toLong(),
                        product.ID
                        ,
                        product.ProductOfferID.toInt(),
                        "productOff",
                        product.SImage,
                        product.Name_En,
                        product.Description_En,
                        quantity,
                        product.ProductStockQuantity,
                        product.ProductOfferDicountValue,
                        product.ProductOfferPrice,
                        false
                    )
                )
            } else {

                Repository.addItemCartLocal(
                    CartItem(
                        no_items + 1.toLong(),
                        product.ID
                        ,
                        -1,
                        "product",
                        product.SImage,
                        product.Name_En,
                        product.Description_En,
                        quantity,
                        product.ProductStockQuantity,
                        0F.toString(),
                        product.Price,
                        false
                    )
                )
            }


//             }

        }

        Repository.getCartDetailsNoLogin(
            Repository.prodsLocal,
            Repository.priceLocal,
            Repository.isbundLocal,
            Repository.offeridsLocal,
            Repository.discountLocal,
            Repository.stockquanLocal,
            Repository.quantityLocal
        )
    }


    fun continueShoppingClick() {


        alerter.alert?.hide()

        main_alert_background.visibility = ConstraintLayout.GONE
        setViewAndChildrenEnabled(main_container, true)

    }

    fun roundTwoDecimals(d: Double): Double {
        val twoDForm = DecimalFormat("#.##")
        return java.lang.Double.valueOf(twoDForm.format(d))
    }

    companion object {
        const val ICON_MARGIN = 50
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private const val ARG_SECTION_NUMBER = "section_number"

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(sectionNumber: Int): ReviewsFragment {
            return ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }


}

