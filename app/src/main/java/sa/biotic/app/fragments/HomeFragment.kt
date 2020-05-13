package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.eftimoff.viewpagertransformers.ZoomOutTranformer
import com.google.android.material.button.MaterialButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.interfaces.Item
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.external.modules.ItemSelectionModule
import com.idanatz.oneadapter.external.modules.ItemSelectionModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import com.skydoves.androidribbon.ShimmerRibbonView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import sa.biotic.app.AllProductsActivity
import sa.biotic.app.OnDetailsActivity
import sa.biotic.app.R
import sa.biotic.app.adapters.OfferPagerAdapter
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.components.RatingBar
import sa.biotic.app.databinding.FragmentHomeBinding
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.TransitionHelper
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.HomeViewModel
import kotlin.properties.Delegates


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    private lateinit var viewModel: HomeViewModel
    lateinit var binding: FragmentHomeBinding
    private lateinit var oneAdapter: OneAdapter
    private lateinit var prodsAdapter: OneAdapter
    private lateinit var bundlesAdapter: OneAdapter
    private var prevRecyclerBinderView: ViewBinder? = null
    lateinit var materialDialog: MaterialDialog
    private lateinit var rateAdapter: OneAdapter
    lateinit var rateItemsLocal: MutableList<OrderItemForRating>


    //for rating

    lateinit var ItemIDs: MutableList<String>
    lateinit var isbundles: MutableList<String>
    lateinit var Rates: MutableList<String>
    var RateStatus by Delegates.notNull<Boolean>()


    private var clickedCatID: Int = -1
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java).apply {
            //
        }


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )
        materialDialog = MaterialDialog(requireContext())

        ItemIDs = mutableListOf()
        isbundles = mutableListOf()
        Rates = mutableListOf()


//        val r = Integer.valueOf(resources.getColor(R.color.colorPrimary).toString().substring(1, 3), 16)
//        val g = Integer.valueOf(resources.getColor(R.color.colorPrimary).toString().substring(3, 5), 16)
//        val b = Integer.valueOf(resources.getColor(R.color.colorPrimary).toString().substring(5, 7), 16)
//        binding.swipeRefresh.setWaveRGBColor(r,g,b)
        val color = resources.getColor(R.color.colorPrimary)
        val red = (color shr 16 and 0xFF.toFloat().toInt()).toFloat()
        val green = (color shr 8 and 0xFF.toFloat().toInt()).toFloat()
        val blue = (color and 0xFF.toFloat().toInt()).toFloat()
        val alpha = (color shr 24 and 0xFF.toFloat().toInt()).toFloat()
        binding.swipeRefresh.setWaveARGBColor(
            alpha.toInt(),
            red.toInt(),
            green.toInt(),
            blue.toInt()
        )

//        binding.swipeRefresh.setTopOffsetOfWave(-50)
//        binding.swipeRefresh.


//        Log.d("UserInfAT",UserInfo.access_token)
//        Log.d("UserInfDT",UserInfo.device_token)
//        Log.d("UserInfUI",UserInfo.uid.toString())






        binding.viewPager.setPageTransformer(true, ZoomOutTranformer())


        val view_pager: ViewPager = binding.viewPager
        binding.seeProds.setOnClickListener {

            val intent = Intent(activity, AllProductsActivity::class.java)

            intent.putExtra("type", "Product")


//            intent.putExtra(EXTRA_MESSAGE, message)
            startActivityForResult(intent, 1)
        }
        binding.seeBunds.setOnClickListener {

            val intent = Intent(activity, AllProductsActivity::class.java)

            intent.putExtra("type", "Bundle")


//            intent.putExtra(EXTRA_MESSAGE, message)
            startActivityForResult(intent, 1)
        }




        binding.categoryRecycler.layoutManager = LinearLayoutManagerWrapper(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        binding.productsRecycler.layoutManager = LinearLayoutManagerWrapper(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )
        binding.bundlesRecycler.layoutManager = LinearLayoutManagerWrapper(
            requireContext(),
            RecyclerView.HORIZONTAL, false
        )


        /** Setting up LiveData observation relationship **/
        viewModel.offersLive.observe(viewLifecycleOwner, Observer { newOffers ->
            //            binding.wordText.text = newWord

            view_pager.adapter = OfferPagerAdapter(requireContext(), newOffers)
            binding.pageIndicatorView.setViewPager(binding.viewPager)


        })
        adapterCreation()
        productsAdapterCreation()


        binding.viewPager.addOnPageChangeListener(
            object : ViewPager.OnPageChangeListener {
                override fun onPageSelected(p0: Int) {
                    // no-op

                }

                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
                    // no-op

                }

                override fun onPageScrollStateChanged(p0: Int) {
                    enableDisableSwipeRefresh(p0 == ViewPager.SCROLL_STATE_IDLE)
                    when (p0) {
                        ViewPager.SCROLL_STATE_SETTLING -> {


//                            Log.d("offer","offer"+binding.viewPager.currentItem.toString())
//                            if (binding.viewPager.currentItem == images.size - 1) {
//                                binding.buttonNext.text = "end"
//                            } else {
//                                binding.buttonNext.text = "next"
//                            }
                        }
                        ViewPager.SCROLL_STATE_IDLE -> {
//                            isLastPage = binding.viewPager.currentItem == images.size - 1
                        }
                        else -> {
                            // no-op
                        }
                    }


                }
            }
        )


//      binding.viewPager.setOnTouchListener { view, motionEvent ->
//
//          when (motionEvent.getAction()) {
//              MotionEvent.ACTION_UP -> {binding.swipeRefresh.setEnabled(true)
//               false}
//
//              else ->{binding.swipeRefresh.setEnabled(false)
//              true}
//
//
//          }
//
//
//      }



        binding.viewPager.setOnClickListener {

            //            Toast.makeText(
//                requireContext(),
//                binding.viewPager.currentItem.toString() + " clicked",
//                Toast.LENGTH_SHORT
//            ).show()

//           Log.d("offer no ",binding.viewPager.currentItem.toString())


        }


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.GONE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 0F)



        binding.swipeRefresh.setOnRefreshListener {


            Repository.getOffers(1, 200)
            Repository.getBundles(1, 200)
            Repository.getHomeBundles(1, 6)
            Repository.getAllProducts(1, 200)
            Repository.getCategories()
            if (UserInfo.signed) {
                Repository.getCartDetails(
                    GetCartDetailsModel(
                        UserInfo.uid,
                        UserInfo.access_token,
                        UserInfo.device_token,
                        UserInfo.promo
                    )
                )
            } else {
                Repository.getCartDetails(
                    GetCartDetailsModel(
                        0,
                        "rr",
                        UserInfo.device_token,
                        UserInfo.promo
                    )
                )

            }


        }


        if (UserInfo.signed) {
            Repository.getCartDetails(
                GetCartDetailsModel(
                    UserInfo.uid,
                    UserInfo.access_token,
                    UserInfo.device_token,
                    UserInfo.promo
                )
            )
        } else {
            Repository.getCartDetails(
                GetCartDetailsModel(
                    0,
                    "rr",
                    UserInfo.device_token,
                    UserInfo.promo
                )
            )

        }

        if (UserInfo.signed) {
            Log.d("rating_is", "iam signed")

            Repository.getOrderDetailsForRating(
                OrderRatingModel(
                    UserInfo.access_token,
                    UserInfo.uid,
                    UserInfo.device_token
                )
            )

        }





        Repository.orderRatingResponse.observe(viewLifecycleOwner, Observer { newit ->


//


            if (newit.OrderItems.size > 0 && !newit.OrderItems.isNullOrEmpty() &&
                !materialDialog.isShowing

            ) {

                if (!isbundles.isEmpty())
                    isbundles.clear()

                if (!ItemIDs.isEmpty())
                    ItemIDs.clear()

                if (!Rates.isEmpty())
                    Rates.clear()



                newit.OrderItems.forEach {
                    Log.d("rating_is", "hello boy")
                    ItemIDs.add(it.ID.toString())
                    if (it.ISBundle) {
                        isbundles.add("True")


                    } else {
                        isbundles.add("False")
                    }
                    Rates.add(0.toString())
                }


//                isbundles :  MutableList<String>
//                 Rates : MutableList<String>

                rateItemsLocal = newit.OrderItems

                showCustomViewDialog(items = newit.OrderItems, order_no = newit.OrderNumber)


            }




            binding.swipeRefresh.isRefreshing = false
        })




        Repository.setItemRatingResponse.observe(viewLifecycleOwner, Observer { newit ->


            //


            if (newit.Status == 1) {
                materialDialog.dismiss()
                Repository.orderRatingResponse.value?.OrderItems?.clear()
                newit.Status = 0

            } else {
                if (newit.Status == 3) {
                    materialDialog.dismiss()
                    Repository.orderRatingResponse.value?.OrderItems?.clear()
                    newit.Status = 0
                }
            }


            binding.swipeRefresh.isRefreshing = false
        })





        return binding.root
    }


    private fun productsAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


//            .attachItemModule(headerItem())
//            .attachItemModule(messageItem()

//                .addState(selectionState())

//                .addEventHook(swipeEventHook())
//            )
//            .attachEmptinessModule(emptinessModule())
//            .attachPagingModule(pagingModule())
//            .attachItemSelectionModule(itemSelectionModule())
        bundlesAdapter = OneAdapter(binding.bundlesRecycler)
            .attachItemModule(
                bundleItem()
                    .addEventHook(clickBundleEventHook())
            )








        viewModel.bundlesLive.observe(viewLifecycleOwner, Observer { newBunds ->

            bundlesAdapter.setItems(newBunds)
        })

        Repository.getHomeProducts(1, 6)

        viewModel.prodsLive.observe(viewLifecycleOwner, Observer { newProds ->

            if (newProds.size == 0) {
                Repository.getHomeProducts(1, 6)
            }
            prodsAdapter = OneAdapter(binding.productsRecycler)
                .attachItemModule(
                    productItem()
                        .addEventHook(clickProductEventHook())
                )
            prodsAdapter.setItems(newProds)

            swipeRefresh.isRefreshing = false


        })
    }

    private fun adapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        oneAdapter = OneAdapter(binding.categoryRecycler)
            .attachItemModule(
                categoryItem()
                    .addEventHook(clickEventHook())
            )
//            .attachItemModule(headerItem())
//            .attachItemModule(messageItem()

//                .addState(selectionState())

//                .addEventHook(swipeEventHook())
//            )
//            .attachEmptinessModule(emptinessModule())
//            .attachPagingModule(pagingModule())
//            .attachItemSelectionModule(itemSelectionModule())


        viewModel.catsLive.observe(viewLifecycleOwner, Observer { newCats ->

            if (newCats.size > 0) {
//                if(clickedCatID == -1)
//                {
//                    Repository.getCategoryProducts(newCats[0].CategoryID)
//                }
//
//
//                else
//                {
//                    Repository.getCategoryProducts(clickedCatID)
//                }
                oneAdapter.setItems(newCats)


//                categoryRecycler.post {
//                    categoryRecycler.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
//
//
//                }


                binding.swipeRefresh.isRefreshing = false

            }



        })
    }

    private fun categoryItem(): ItemModule<Category> = object : ItemModule<Category>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.category

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(requireContext(), R.animator.category_anim)
            }
        }

        override fun onBind(item: Item<Category>, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.category_image)
//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(requireContext())
                .load(item.model.CategoryIconPurble)
//                .centerCrop()
//                .load(model.icon).
                .into(story1)


//            story2.setText(model.title)


        }
    }

    private fun productItem(): ItemModule<Product> = object : ItemModule<Product>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(requireContext(), R.animator.category_anim)
            }
        }

        override fun onBind(item: Item<Product>, viewBinder: ViewBinder) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.calories)
            val story6 = viewBinder.findViewById<TextView>(R.id.oldprice)
            val story8 = viewBinder.findViewById<TextView>(R.id.stock_tv)
            val story7 = viewBinder.findViewById<TextView>(R.id.discount_value)
            val story9 = viewBinder.findViewById<CardView>(R.id.discount_card)
            val story10 = viewBinder.findViewById<ImageView>(R.id.cal_icon)

            val ribbon: ShimmerRibbonView = viewBinder.findViewById<ShimmerRibbonView>(R.id.ribbon)
            val ribbon2: ShimmerRibbonView =
                viewBinder.findViewById<ShimmerRibbonView>(R.id.ribbon2)


            if (item.model.IsNew == 1) {
                ribbon.visibility = View.VISIBLE
                ribbon2.visibility = View.VISIBLE
            } else {
                ribbon.visibility = View.INVISIBLE
                ribbon2.visibility = View.INVISIBLE
            }

//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

            viewBinder.rootView.isNestedScrollingEnabled = false



            Log.d(
                "isoHere",
                item.model.ProductName_En + " " + item.model.ProductStockQuantity.toString()
            )

//
            Glide.with(this@HomeFragment)

//                .load(model.img)

                .load(item.model.ProductImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false).into(story1)

            story2.text = item.model.ProductName_En
            story3.text = "%.2f".format(item.model.ProductPrice) + " " + getString(R.string._sar)
            story4.text = item.model.ProductDescription_En
            story5.text = item.model.ProductCallories.toString()


            story6.paintFlags =
                story6.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG




            if (item.model.ProductOfferPrice.toFloat() > 0) {
                story3.text =
                    "%.2f".format(item.model.ProductOfferPrice) + " " + getString(R.string._sar)
                story6.text =
                    "%.2f".format(item.model.ProductPrice) + " " + getString(R.string._sar)
                story6.visibility = TextView.VISIBLE
                story3.visibility = TextView.VISIBLE


            } else {

                story6.visibility = TextView.GONE


            }
            if (item.model.ProductOfferDicountValue.toFloat() <= 0F) {
                story9.visibility = CardView.GONE

            } else {
                story9.visibility = CardView.VISIBLE
            }

            story7.text =
                (item.model.ProductOfferDicountValue.toFloat() * 100).toInt().toString() + "%"



            if (item.model.ProductStockQuantity <= 5) {
                if (item.model.ProductStockQuantity <= 0) {
//                    if(model.ProductID==79)

                    story3.text = getString(R.string.sold_out)


//                    story3.visibility=TextView.INVISIBLE
                    story6.visibility = TextView.INVISIBLE
                    story8.visibility = TextView.INVISIBLE

                } else {
                    story8.text =
                        getString(R.string.only) + " " + item.model.ProductStockQuantity.toString() + " " + getString(
                            R.string.left
                        )
                    story8.visibility = TextView.VISIBLE
                }

            } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
                story8.visibility = TextView.INVISIBLE
            }



            if (story3.text == getString(R.string.sold_out) && item.model.ProductStockQuantity == 0 && !story6.isVisible)
                story3.setTextColor(resources.getColor(R.color.stockColor))
            else
                story3.setTextColor(resources.getColor(R.color.colorPrimary))



            if (item.model.ProductCallories == 0) {

                story5.visibility = View.GONE
                story10.visibility = View.GONE


            } else {
                story5.visibility = View.VISIBLE
                story10.visibility = View.VISIBLE
            }
        }


    }

    private fun bundleItem(): ItemModule<BundleProduct> = object : ItemModule<BundleProduct>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.bundle_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(requireContext(), R.animator.category_anim)
            }
        }

        override fun onBind(item: Item<BundleProduct>, viewBinder: ViewBinder) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)

            val story8 = viewBinder.findViewById<TextView>(R.id.oldprice)
            val story9 = viewBinder.findViewById<TextView>(R.id.stock_tv)
            val ribbon: ShimmerRibbonView = viewBinder.findViewById<ShimmerRibbonView>(R.id.ribbon)
            val ribbon2: ShimmerRibbonView =
                viewBinder.findViewById<ShimmerRibbonView>(R.id.ribbon2)


            if (item.model.IsNew == 1) {
                ribbon.visibility = View.VISIBLE
                ribbon2.visibility = View.VISIBLE
            } else {
                ribbon.visibility = View.INVISIBLE
                ribbon2.visibility = View.INVISIBLE
            }


//
            Glide.with(this@HomeFragment)
//                .load(model.img)

                .load(item.model.BundleImage).centerCrop().into(story1)

            story2.text = item.model.BundleName_En
            story3.text = "%.2f".format(item.model.BundlePrice) + " " + getString(R.string._sar)
            story4.text = item.model.BundleDescription_En

            story8.visibility = TextView.INVISIBLE

            if (item.model.BundleStockAvaliable == 0) {

                story9.visibility = TextView.VISIBLE
                story3.visibility = TextView.GONE
                story9.text = "Sold out"
//                story3.setTextColor(resources.getColor(R.color.stockColor))

            } else {

                story9.visibility = TextView.GONE
                story3.visibility = TextView.VISIBLE

                if (item.model.BundleStockAvaliable <= 5) {
                    Log.d("iam here bitch", "hello friend")
                    story9.text =
                        getString(R.string.only) + " " + item.model.BundleStockAvaliable.toString() + " " + getString(
                            R.string.left
                        )
                    story9.visibility = TextView.VISIBLE

                } else {
                    story9.visibility = TextView.GONE
                }
            }
        }



        }

    private fun clickProductEventHook(): ClickEventHook<Product> =
        object : ClickEventHook<Product>() {
            override fun onClick(model: Product, viewBinder: ViewBinder) {
//            Toast.makeText(requireContext(), "${model.title} clicked", Toast.LENGTH_SHORT).show()

//            if(prevRecyclerBinderView!=null){
//
//                prevRecyclerBinderView!!.findViewById<LinearLayout>(R.id.category_background)
//                    .setBackgroundResource(R.drawable.category_frame)
//                prevRecyclerBinderView!!.findViewById<ImageView>(R.id.category_image)
//                    .setColorFilter(
//                        ContextCompat.getColor(this@MainActivity, R.color.purple),
//                        android.graphics.PorterDuff.Mode.SRC_IN)
//
//            }
//
//            viewBinder.findViewById<LinearLayout>(R.id.category_background)
//                .setBackgroundResource(R.drawable.category_frame_clicked)
//            viewBinder.findViewById<ImageView>(R.id.category_image)
//                .setColorFilter(
//                    ContextCompat.getColor(this@MainActivity, R.color.white),
//                    android.graphics.PorterDuff.Mode.SRC_IN)
//
//            prevRecyclerBinderView = viewBinder
                val intent = Intent(activity, OnDetailsActivity::class.java)

                var sharedView = viewBinder.rootView.findViewById<View>(R.id.product_image)
                var transitionName = getString(R.string.hero_image)
                var sharedView2 = viewBinder.rootView.findViewById<View>(R.id.product_title)
                var transitionName2 = getString(R.string.hero_name)
                var participants: Array<android.util.Pair<View, String>> = arrayOf()


//                participants[0]=android.util.Pair(sharedView,transitionName)
//                participants[1]=Pair(sharedView2,transitionName2)


                val pairs: Array<Pair<View, String>> =
                    TransitionHelper.createSafeTransitionParticipants(
                        requireActivity(), false,
                        android.util.Pair<View, String>(
                            sharedView,
                            transitionName
                        ),
                        android.util.Pair<View, String>(
                            sharedView2,
                            transitionName2
                        )
                    )


                var transitionActivityOptions: ActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(
                        requireActivity(),
                        *pairs
                    )

//                intent.putExtra("product_name", model.title)
//                intent.putExtra("product_image", model.img)
//                intent.putExtra("product_price", model.price)
                intent.putExtra("type", "product")
                intent.putExtra("ProductItem", model)


//            intent.putExtra(EXTRA_MESSAGE, message)
                startActivity(intent, transitionActivityOptions.toBundle())

            }


        }


    private fun clickBundleEventHook(): ClickEventHook<BundleProduct> =
        object : ClickEventHook<BundleProduct>() {
            override fun onClick(model: BundleProduct, viewBinder: ViewBinder) {


                var sharedView = viewBinder.rootView.findViewById<View>(R.id.product_image)
                var transitionName = getString(R.string.hero_image)
                var sharedView2 = viewBinder.rootView.findViewById<View>(R.id.product_title)
                var transitionName2 = getString(R.string.hero_name)

                val pairs: Array<Pair<View, String>> =
                    TransitionHelper.createSafeTransitionParticipants(
                        requireActivity(), false,
                        android.util.Pair<View, String>(
                            sharedView,
                            transitionName
                        ),
                        android.util.Pair<View, String>(
                            sharedView2,
                            transitionName2
                        )
                    )


                var transitionActivityOptions: ActivityOptions =
                    ActivityOptions.makeSceneTransitionAnimation(
                        requireActivity(),
                        *pairs
                    )

                val intent = Intent(activity, OnDetailsActivity::class.java)

                intent.putExtra("BundleItem", model)
                intent.putExtra("type", "bundle")

                startActivity(intent, transitionActivityOptions.toBundle())
            }


        }


    inline fun <reified T : Any> Activity.launchActivity(
        requestCode: Int = -1,
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivityForResult(intent, requestCode, options)
        } else {
            startActivityForResult(intent, requestCode)
        }
    }

    inline fun <reified T : Any> Context.launchActivity(
        options: Bundle? = null,
        noinline init: Intent.() -> Unit = {}
    ) {
        val intent = newIntent<T>(this)
        intent.init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent, options)
        } else {
            startActivity(intent)
        }
    }

    inline fun <reified T : Any> newIntent(context: Context): Intent =
        Intent(context, T::class.java)


    private fun clickEventHook(): ClickEventHook<Category> = object : ClickEventHook<Category>() {
        override fun onClick(model: Category, viewBinder: ViewBinder) {
//            Toast.makeText(this@MainActivity, "${model.title} clicked", Toast.LENGTH_SHORT).show()

            if (prevRecyclerBinderView != null) {

                prevRecyclerBinderView!!.findViewById<LinearLayout>(R.id.category_background)
                    .setBackgroundResource(R.drawable.category_frame)
                prevRecyclerBinderView!!.findViewById<ImageView>(R.id.category_image)
                    .setColorFilter(
                        ContextCompat.getColor(requireContext(), R.color.purple),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

            }

            viewBinder.findViewById<LinearLayout>(R.id.category_background)
                .setBackgroundResource(R.drawable.category_frame_clicked)
            viewBinder.findViewById<ImageView>(R.id.category_image)
                .setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )

            prevRecyclerBinderView = viewBinder


            Repository.getCategoryProducts(model.CategoryID)

        }


    }


    private fun itemSelectionModule(): ItemSelectionModule = object : ItemSelectionModule() {
        override fun provideModuleConfig(): ItemSelectionModuleConfig =
            object : ItemSelectionModuleConfig() {
                override fun withSelectionType() = SelectionType.Multiple
            }

        override fun onSelectionUpdated(selectedCount: Int) {
            if (selectedCount == 0) {
//                setToolbarText(getString(R.string.app_name))
//                toolbarMenu?.findItem(R.id.action_delete)?.isVisible = false


            } else {
//                setToolbarText("$selectedCount selected")
//                toolbarMenu?.findItem(R.id.action_delete)?.isVisible = true
            }
        }
    }

    private fun enableDisableSwipeRefresh(enable: Boolean) {
        if (binding.swipeRefresh != null) {
            binding.swipeRefresh.isEnabled = enable
        }

    }

    private fun rateItem(): ItemModule<OrderItemForRating> =
        object : ItemModule<OrderItemForRating>() {
            override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
                override fun withLayoutResource(): Int = R.layout.rate_item

                override fun withFirstBindAnimation(): Animator {
                    // can be implemented by inflating Animator Xml
                    return AnimatorInflater.loadAnimator(
                        this@HomeFragment.context,
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

                    var btn = materialDialog.findViewById<MaterialButton>(R.id.submit_btn)


                    if (Rates.contains("0")) {


                        btn.setBackgroundColor(resources.getColor(R.color.oldPrice))
//                  btn.text = getString(R.string.not_available)
                        btn.isEnabled = false
                    } else {

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

                if (model.ISBundle) {
//                status.text = getString(R.string.sold_out)
//                status.setTextColor(resources.getColor(R.color.stockColor))
                } else {
//                status.text =
//                    getString(R.string.only) + " " + model.ItemStockQuantity.toString() + " " + getString(
//                        R.string.left
//                    )
//                status.setTextColor(resources.getColor(R.color.colorPrimary))

                }

//            rateItemsLocal.find {  }


                Glide.with(this@HomeFragment)
//                .load(model.img)
//
                    .load(model.SImage).centerCrop().into(image)



                title.text = model.Name_En



                if (rateItemsLocal.last() == model) {
                    line.visibility = View.INVISIBLE

                } else {
                    line.visibility = View.VISIBLE
                }


            }
        }

    private fun showCustomViewDialog(
        dialogBehavior: DialogBehavior = ModalDialog,
        items: MutableList<OrderItemForRating>,
        order_no: Int
    ) {
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

        val updatedRecyclerView = customView.findViewById<RecyclerView>(R.id.updated_rec)
        val order_tv = customView.findViewById<TextView>(R.id.order_no_value)
        val submit_btn = customView.findViewById<MaterialButton>(R.id.submit_btn)
        val not_btn = customView.findViewById<MaterialButton>(R.id.not_now_btn)

        submit_btn.setBackgroundColor(resources.getColor(R.color.oldPrice))
        submit_btn.isEnabled = false

        order_tv.text = order_no.toString()



        updatedRecyclerView.layoutManager =
            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        rateAdapter = OneAdapter(updatedRecyclerView).attachItemModule(
            rateItem()
//                .addEventHook(clickBundEventHook())
        )

        submit_btn.setOnClickListener {
//            Log.d("rates_is",ItemIDs.toString())
//            Log.d("rates_is",isbundles.toString())
//            Log.d("rates_is",Rates.toString())

            if (!Rates.contains("0")) {
                var ids: String = ItemIDs[0]
                var rates: String = Rates[0]
                var isbund: String = isbundles[0]

                if (ItemIDs.size > 1)
                    for (i in 1..ItemIDs.size - 1) {

                        ids = ids + "," + ItemIDs[i]
                        rates = rates + "," + Rates[i]
                        isbund = isbund + "," + isbundles[i]


                    }





                Repository.setItemRating(
                    ItemRatingToSendModel(
                        UserInfo.uid, UserInfo.access_token, UserInfo.device_token,
                        ids, isbund, rates, order_no, true
                    )
                )

            }


        }


        not_btn.setOnClickListener {

//            if(!Rates.contains("0")) {

            var ids: String = ItemIDs[0]
            var rates: String = Rates[0]
            var isbund: String = isbundles[0]


            if (ItemIDs.size > 1)
                for (i in 1..ItemIDs.size - 1) {

                    ids = ids + "," + ItemIDs[i]
                    rates = rates + "," + Rates[i]
                    isbund = isbund + "," + isbundles[i]


                }

            Repository.setItemRating(
                ItemRatingToSendModel(
                    UserInfo.uid, UserInfo.access_token, UserInfo.device_token,
                    ids, isbund, rates, order_no, false
                )
            )

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

//            Navigation.findNavController(binding.root).popBackStack(R.id.profileFragment, false)
//        }
    }


    // TODO: Rename method, update argument and hook method into UI event

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        if (context is OnFragmentInteractionListener) {
//            listener = context
//        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
//        }
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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

//    override fun onRatingChanged(ratingBar: RatingBar, rating: Float) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }


}
