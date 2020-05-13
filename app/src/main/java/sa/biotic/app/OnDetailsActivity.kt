package sa.biotic.app


import android.animation.Animator
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.internal.holders.ViewBinder
import com.mikhaellopez.rxanimation.RxAnimation
import com.mikhaellopez.rxanimation.translation
import com.rowland.cartcounter.view.CartCounterActionView
import com.stfalcon.imageviewer.StfalconImageViewer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import sa.biotic.app.adapters.SectionPagerAdapter
import sa.biotic.app.adapters.SectionPagerAdapterNotBundle
import sa.biotic.app.components.ImagesOverLayView
import sa.biotic.app.components.alerter.Alerter
import sa.biotic.app.databinding.ActivityScrollingBinding
import sa.biotic.app.image_transfer.CircleAnimationUtil
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.ErrorDialog
import sa.biotic.app.viewmodels.OnDetailsViewModel


//ErrorDialog.showCustomViewDialog(
//context = requireContext(),
//custView = R.layout.dialog_error,
//msg = "please fill search field !"
//)

class OnDetailsActivity : AppCompatActivity() {


    private lateinit var reviewsAdapter: OneAdapter
    lateinit var main_container: ConstraintLayout
    lateinit var alerter: Alerter
    lateinit var alert_view: LinearLayout
    lateinit var main_alert_background: LinearLayout
    lateinit var nested: NestedScrollView
    private lateinit var binding: ActivityScrollingBinding
    lateinit var alert_title: String
    private val composite = CompositeDisposable()
    lateinit var viewModel: OnDetailsViewModel
    lateinit var btn: Button
    lateinit var viewPager: ViewPager
    lateinit var product_price: TextView
    lateinit var images: MutableList<String>
    lateinit var sectionPagerAdapter: SectionPagerAdapter
    lateinit var sectionPagerAdapterNotBundle: SectionPagerAdapterNotBundle
    private var overlayView: ImagesOverLayView? = null
    var no_items = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(OnDetailsViewModel::class.java).apply {
            //
        }



        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling)

        btn = number_button2.findViewById<Button>(R.id.subtract_btn)
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



        btn.setTextColor(resources.getColor(R.color.oldPrice))




        setSupportActionBar(binding.toolbar)


        sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager)
        sectionPagerAdapterNotBundle =
            SectionPagerAdapterNotBundle(this, supportFragmentManager)
        viewPager = view_pager


        val tabs: TabLayout = findViewById<TabLayout>(R.id.tabs)
        nested = this.findViewById(R.id.nested_product)



        binding.appBar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var titleIsShowing = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (verticalOffset == 0) {
//                    binding.toolbar.title = "12.3MM TRTL"
//                    titleIsShowing = true

                    binding.swipeRefresh.isEnabled = true
//                    binding.swipeRefresh.isRefreshing = true


                } else
//                    if (titleIsShowing)
                {

                    binding.swipeRefresh.isEnabled = false
                    binding.swipeRefresh.isRefreshing = false

//                    binding.toolbar.title = " "//careful there should a space between double quote otherwise it wont work
//                    titleIsShowing = false
                }
            }
        })


        var addToCartButton: MaterialButton = findViewById(R.id.add_to_cart)
//        main_alert_background = findViewById(R.id.Alert_main_back)
        main_container = findViewById(R.id.main_container)
        addToCartButton.setOnClickListener {
            //                                enableDisableActivty(false)


            addItemToCartAnimation(binding.productImage2, number_button2.number.toInt())


        }


//        nested.setOnScrollChangeListener { v: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
//
//
////            if(scrollY==0){
////                binding.swipeRefresh.isEnabled = true
////
////            }
////            else{
////                binding.swipeRefresh.isEnabled = false
////
////            }
////
//
////            if (scrollY > oldScrollY) {
//////                Log.i(TAG, "Scroll DOWN");
////                binding.swipeRefresh.isEnabled = false
////                binding.swipeRefresh.isClickable = false
////                binding.swipeRefresh.isActivated = false
////            }
////            if (scrollY < oldScrollY) {
//////                Log.i(TAG, "Scroll UP");
////                binding.swipeRefresh.isEnabled = false
////                binding.swipeRefresh.isClickable = false
////                binding.swipeRefresh.isActivated = false
////
////            }
////
////            if (scrollY <= 0) {
//////                Log.i(TAG, "TOP SCROLL");
////                binding.swipeRefresh.isEnabled = true
////                binding.swipeRefresh.isClickable = true
////                binding.swipeRefresh.isActivated = true
////
////            }
////
////            if (scrollY == (v?.getMeasuredHeight()?.minus(v?.getChildAt(0).getMeasuredHeight()))) {
//////                Log.i(TAG, "BOTTOM SCROLL");
////                binding.swipeRefresh.isEnabled = false
////                binding.swipeRefresh.isClickable = false
////                binding.swipeRefresh.isActivated = false
////            }
//
////      Log.d("nested_pos", " $scrollY")
////           var t = Toast.makeText(this@OnDetailsActivity, scrollY.toString(), Toast.LENGTH_LONG)
////            t. show()
//
//        }

////        binding.swipeRefresh.isEnabled = false
////        binding.swipeRefresh.isClickable = false
////        binding.swipeRefresh.isActivated = false
//
//
////        nested.setOnScrollChangeListener { v: View?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
////
////
////            var t = Toast.makeText(this@OnDetailsActivity, scrollY.toString(), Toast.LENGTH_LONG)
////            t. show()
////        }
//
//
//
//        binding.swipeRefresh.setOnScrollChangeListener { view, i, i2, i3, i4 ->
//
//            if(nested.scrollY<=0){
//                binding.swipeRefresh.isEnabled = true
//
//            }
//            else{
//                binding.swipeRefresh.isEnabled = false
//            }
//
//            var t = Toast.makeText(this@OnDetailsActivity, i.toString(), Toast.LENGTH_LONG)
//            t. show()
//        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val product_iv = findViewById<ImageView>(R.id.product_image)
        val product_tv = findViewById<TextView>(R.id.product_name)
        product_price = findViewById<TextView>(R.id.product_price)
        images = mutableListOf<String>()



        if (intent.getStringExtra("type") == "bundle") {
            val bundle: BundleProduct? = intent.getParcelableExtra("BundleItem")


            viewModel.bundleLive.observe(this, Observer { bund ->

                if (bund.BundleID != -1) {

                    var parsingBundle: BundleProduct = bund.copy()



                    setUiOfBundle(parsingBundle)
                    binding.swipeRefresh.isRefreshing = false

                    bund.BundleID = -1


                }


            })


            binding.swipeRefresh.setOnRefreshListener {


                viewModel.getThisBundle(bundle!!.BundleID)


            }


            setUiOfBundle(bundle)


        } else {
            if (intent.getStringExtra("type") == "product") {


                val product: Product? = intent.getParcelableExtra("ProductItem")


                viewModel.prodLive.observe(this, Observer { prod ->

                    if (prod.ProductID != -1) {

                        var productParsing: Product = prod.copy()




                        setUiOfProduct(productParsing)
                        binding.swipeRefresh.isRefreshing = false

                        prod.ProductID = -1


                    }


                })


                binding.swipeRefresh.setOnRefreshListener {


                    viewModel.getThisProd(product!!.ProductID)


                }


                setUiOfProduct(product)



                Repository.getImages(product!!.ProductID)

                Repository.images.observe(this, Observer { image ->

                    if (image.ProductID != -1) {

                        images = image.ProductGalleryImages


                        image.ProductID = -1


                    }


                })


                binding.productImage.setOnClickListener {

                    overlayView = ImagesOverLayView(this).apply {
                        update(images.size)


                    }


                    StfalconImageViewer.Builder<String>(
                        this@OnDetailsActivity,
                        images
                    ) { view, image ->
                        Glide.with(this)
//                .load(model.img)

                            .load(image)
                            .into(view)

//                }.show()

//                    Picasso.get().load(image).into(view)
//
                    }.withTransitionFrom(binding.productImage).withBackgroundColor(
                        resources.getColor(
                            android.R.color.background_dark
                        )
                    )
                        .withOverlayView(overlayView)
                        .withImageChangeListener {
                            overlayView?.updatePosition(it)
                        }
                        .withDismissListener {

                            overlayView?.removeAllViews()
                            overlayView = null
                        }
                        .show()

                }


            } else {


                viewPager.adapter = sectionPagerAdapterNotBundle
                val offer: OfferProduct? = intent.getParcelableExtra("OfferItem")

                viewModel.prodLive.observe(this, Observer { prod ->

                    if (prod.ProductID != -1) {

                        var productParsing: Product = prod.copy()




                        setUiOfProduct(productParsing)
                        binding.swipeRefresh.isRefreshing = false

                        prod.ProductID = -1


                    }


                })


                binding.swipeRefresh.setOnRefreshListener {


                    viewModel.getThisProd(offer!!.ProductID)


                }




                Glide.with(this)
//                .load(model.img)

                    .load(offer?.ProductImage)
                    .centerCrop()
                    .into(binding.productImage)
                Glide.with(this)
//                .load(model.img)

                    .load(offer?.ProductImage)
                    .centerCrop()
                    .into(binding.productImage2)
//
//
                product_name.text = offer?.ProductName_En
                product_price.text =
                    "%.2f".format(offer?.OfferPrice) + " " + getString(R.string._sar)
                product_prev_price.text =
                    "%.2f".format(offer?.ProductPrice) + " " + getString(R.string._sar)
                smart_rating_bar.ratingNum = offer?.ProductReviews!!.toFloat()
//                calories.text = offer.ProductCallories.toString()
                product_prev_price.paintFlags =
                    product_prev_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                calories.text =
                    offer.ProductCallories.toString() + " " + getString(R.string.calName)
                now.visibility = TextView.VISIBLE
                was.visibility = TextView.VISIBLE
                product_prev_price.visibility = TextView.VISIBLE

                if (offer.ProductStockQuantity <= 5) {
                    if (offer.ProductStockQuantity <= 0) {
                        stock_tv.text = getString(R.string.sold_out)
                        binding.addToCart.setBackgroundColor(resources.getColor(R.color.oldPrice))
                        binding.addToCart.text = getString(R.string.not_available)
                        binding.addToCart.isEnabled = false
                        product_price.visibility = TextView.INVISIBLE
                        counter_box.visibility = View.INVISIBLE
                        now.visibility = View.INVISIBLE
                        was.visibility = View.INVISIBLE
                        product_prev_price.visibility = View.INVISIBLE

                    } else
                        stock_tv.text =
                            getString(R.string.only) + " " + offer.ProductStockQuantity.toString() + " " + getString(
                                R.string.left
                            )
                } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
                    stock_tv.visibility = TextView.INVISIBLE


                }


//                images.add(offer.ProductImage)


                Repository.getImages(offer.ProductID)

                Repository.images.observe(this, Observer { image ->

                    if (image.ProductID != -1) {

                        images = image.ProductGalleryImages

//                        overlayView = ImagesOverLayView(this).apply {
//                            update(image.ProductGalleryImages.size)
//
//
//                        }

                        image.ProductID = -1


                    }


                })


                binding.productImage.setOnClickListener {

                    overlayView = ImagesOverLayView(this).apply {
                        update(images.size)


                    }

                    StfalconImageViewer.Builder<String>(
                        this@OnDetailsActivity,
                        images
                    ) { view, image ->
                        Glide.with(this)
//                .load(model.img)

                            .load(image)
                            .into(view)
//                }.show()

//                    Picasso.get().load(image).into(view)
//
                    }.withTransitionFrom(binding.productImage).withBackgroundColor(
                        resources.getColor(
                            android.R.color.background_dark
                        )
                    )
                        .withOverlayView(overlayView)
                        .withImageChangeListener {
                            overlayView?.updatePosition(it)
                        }

                        .withDismissListener {
                            overlayView?.removeAllViews()
                            overlayView = null
                        }
                        .show()

                }

                number_button2.setRange(1, offer.ProductStockQuantity + 1)

                number_button2.setOnValueChangeListener { view, oldValue, newValue ->

                    Log.d("number_button_clicked", newValue.toString())
                    if (newValue > 1) {
//                    var btn = view.findViewById<Button>(R.id.subtract_btn)
                        btn.setTextColor(resources.getColor(R.color.text_header))

                    } else {
                        btn.setTextColor(resources.getColor(R.color.oldPrice))
                    }


                    if (newValue == offer.ProductStockQuantity + 1) {
                        ErrorDialog.showCustomViewDialog(
                            context = this@OnDetailsActivity,
                            custView = R.layout.dialog_error,
                            msg = "You Can't Add More Quantity"
                        )

                        number_button2.number = offer.ProductStockQuantity.toString()

                    }

                }

            }

        }

        tabs.setupWithViewPager(viewPager)


//
//        val intent = intent
//        val product_title = intent.getStringExtra("product_name")
//        val product_image = intent.getStringExtra("product_image")
//        val price = intent.getStringExtra("product_price")
//
//        if (intent.getStringExtra("type") == "offer") {
//            val prev_price = intent.getStringExtra("product_prev_price")
//            val product_prev_price = findViewById<TextView>(R.id.product_prev_price)
//            val was = findViewById<TextView>(R.id.was)
//            val now = findViewById<TextView>(R.id.now)
//
//            product_prev_price.paintFlags =
//                product_prev_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//            product_prev_price.visibility = TextView.VISIBLE
//            product_prev_price.text = prev_price
//            was.visibility = TextView.VISIBLE
//            now.visibility = TextView.VISIBLE
//
//        }
//
////        commented
//        Glide.with(this)
////                .load(model.img)
//
//            .load(product_image)
//            .centerCrop()
//            .into(product_iv)
//
//        product_tv.text = product_title
//        product_price.text = price

    }

    private fun setUiOfProduct(product: Product?) {


        viewPager.adapter = sectionPagerAdapterNotBundle
        alert_title =
            product?.ProductName_En + "\n" + product?.ProductDescription_En + "\n" + product?.ProductPrice + " " + getString(
                R.string._sar
            )
        Glide.with(this)
//                .load(model.img)

            .load(product?.ProductImage)
            .centerCrop()
            .into(binding.productImage)
        Glide.with(this)
//                .load(model.img)

            .load(product?.ProductImage)
            .centerCrop()
            .into(binding.productImage2)


        product_name.text = product?.ProductName_En
        product_price.text =
            "%.2f".format(product?.ProductPrice).toString() + " " + getString(R.string._sar)
        Repository.getReviews(product!!.ProductID, 0)
        if (product.ProductOfferPrice.toFloat() > 0F) {
            now.visibility = TextView.VISIBLE
            was.visibility = TextView.VISIBLE
            product_prev_price.visibility = TextView.VISIBLE
            product_price.text =
                "%.2f".format(product.ProductOfferPrice).toString() + " " + getString(R.string._sar)
            product_prev_price.paintFlags =
                product_prev_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            product_prev_price.text =
                "%.2f".format(product.ProductPrice).toString() + " " + getString(R.string._sar)

        }


//                stock_tv.text= product?.ProductStockQuantity

        smart_rating_bar.ratingNum = product.ProductReviews.toFloat()
        calories.text =
            product.ProductCallories.toString() + " " + getString(R.string.calName)


        if (product.ProductStockQuantity <= 5) {
            if (product.ProductStockQuantity <= 0) {
                stock_tv.text = getString(R.string.sold_out)
                binding.addToCart.setBackgroundColor(resources.getColor(R.color.oldPrice))
                binding.addToCart.text = getString(R.string.not_available)
                binding.addToCart.isEnabled = false
                product_price.visibility = TextView.INVISIBLE
                counter_box.visibility = View.INVISIBLE
                now.visibility = View.GONE
                was.visibility = View.GONE
                product_prev_price.visibility = View.INVISIBLE


            } else
                stock_tv.text =
                    getString(R.string.only) + " " + product.ProductStockQuantity.toString() + " " + getString(
                        R.string.left
                    )
        } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
            stock_tv.visibility = TextView.INVISIBLE
        }


//        images.add(product.ProductImage)


        number_button2.setRange(1, product.ProductStockQuantity + 1)

        number_button2.setOnValueChangeListener { view, oldValue, newValue ->

            Log.d("number_button_clicked", newValue.toString())

            if (newValue > 1) {
//                    var btn = view.findViewById<Button>(R.id.subtract_btn)
                btn.setTextColor(resources.getColor(R.color.text_header))

            } else {
                btn.setTextColor(resources.getColor(R.color.oldPrice))
            }


            if (newValue == product.ProductStockQuantity + 1) {
                ErrorDialog.showCustomViewDialog(
                    context = this@OnDetailsActivity,
                    custView = R.layout.dialog_error,
                    msg = "You Can't Add More Quantity"
                )

                number_button2.number = product.ProductStockQuantity.toString()

            }

        }


    }

    private fun setUiOfBundle(bundle: BundleProduct?) {

        Repository.getReviews(bundle!!.BundleID, 1)

        alert_title =
            bundle.BundleName_En + "\n" + bundle.BundleDescription_En + "\n" + bundle.BundlePrice + " " + getString(
                R.string._sar
            )


        var image = Glide.with(this)
//                .load(model.img)

            .load(bundle.BundleImage)
            .centerCrop()
            .into(binding.productImage)

        Glide.with(this)
            .load(bundle.BundleImage)
            .centerCrop()
            .into(binding.productImage2)

        product_name.text = bundle.BundleName_En
        product_price.text =
            "%.2f".format(bundle.BundlePrice).toString() + " " + getString(R.string._sar)
        smart_rating_bar.ratingNum = bundle.BundleReview.toFloat()
        calories.visibility = TextView.INVISIBLE
        cal_icon.visibility = ImageView.INVISIBLE

        if (bundle.BundleStockAvaliable <= 0) {
            stock_tv.text = getString(R.string.sold_out)
            binding.addToCart.setBackgroundColor(resources.getColor(R.color.oldPrice))
            binding.addToCart.text = getString(R.string.not_available)
            binding.addToCart.isEnabled = false
            product_price.visibility = TextView.INVISIBLE
            counter_box.visibility = View.INVISIBLE
            now.visibility = View.INVISIBLE
            was.visibility = View.INVISIBLE
            product_prev_price.visibility = View.INVISIBLE
        } else {
            if (bundle.BundleStockAvaliable <= 5) {
                stock_tv.text =
                    getString(R.string.only) + " " + bundle.BundleStockAvaliable.toString() + " " + getString(
                        R.string.left
                    )
            } else
                stock_tv.visibility = TextView.INVISIBLE
        }
        viewPager.adapter = sectionPagerAdapter



        images.add(bundle.BundleImage)



        binding.productImage.setOnClickListener {

            StfalconImageViewer.Builder<String>(this@OnDetailsActivity, images) { view, image ->
                Glide.with(this)


                    .load(image)
                    .into(view)


            }.withTransitionFrom(binding.productImage).withBackgroundColor(
                resources.getColor(
                    android.R.color.background_dark
                )
            )
                .show()

        }


        number_button2.setRange(1, bundle.BundleStockAvaliable + 1)


        number_button2.setOnValueChangeListener { view, oldValue, newValue ->

            Log.d("number_button_clicked", newValue.toString())

            if (newValue > 1) {
//                    var btn = view.findViewById<Button>(R.id.subtract_btn)
                btn.setTextColor(resources.getColor(R.color.text_header))

            } else {
                btn.setTextColor(resources.getColor(R.color.oldPrice))
            }

            if (newValue == bundle.BundleStockAvaliable + 1) {
                ErrorDialog.showCustomViewDialog(
                    context = this@OnDetailsActivity,
                    custView = R.layout.dialog_error,
                    msg = "You Can't Add More Quantity"
                )

                number_button2.number = bundle.BundleStockAvaliable.toString()

            }

        }

    }


    private fun enableDisableActivty(isEnable: Boolean) {
        if (!isEnable) window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
        else window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        when (item.itemId) {
//            R.id.action_cart -> true
//            R.id.action_search -> true
//            android.R.id.home->finish()
//        }
//
//        return when (item.itemId) {
//            R.id.action_cart -> true
//            R.id.action_search -> true
//            android.R.id.home->true
//            else -> super.onOptionsItemSelected(item)
//
//        }
//    }

    override fun onSupportNavigateUp(): Boolean {
//        supportFinishAfterTransition()
        finish()
        return super.onSupportNavigateUp()
    }

    private fun clickEventHook(): ClickEventHook<Review> = object : ClickEventHook<Review>() {
        override fun onClick(model: Review, viewBinder: ViewBinder) {
            Toast.makeText(this@OnDetailsActivity, "${model.name} clicked", Toast.LENGTH_SHORT)
                .show()


        }


    }


//    private fun reviewItem(): ItemModule<Review> = object : ItemModule<Review>() {
//        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
//            override fun withLayoutResource(): Int = R.layout.review_item
//
////            override fun withFirstBindAnimation(): Animator {
////                // can be implemented by inflating Animator Xml
////                return AnimatorInflater.loadAnimator(this@ReviewsFragment.context, R.animator.category_anim)
////            }
//        }
//
//
//        override fun onBind(model: Review, viewBinder: ViewBinder) {
////            val story1 = viewBinder.findViewById<RoundedImageView>(R.id.review_image)
////            val story2 = viewBinder.findViewById<TextView>(R.id.review_name)
////            val story3 = viewBinder.findViewById<sa.biotic.app.compnents.RatingBar>(R.id.smart_rating_bar)
//
//            Log.d("hello", model.toString())
//
//
////            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)
//
//////
////            Glide.with(this@ReviewsFragment)
//////                .load(model.img)
////
////                .load(model.img).centerCrop().into(story1)
//
//
////            story2.setText(model.name)
////            story3.ratingNum=model.rate.toFloat()
//
//
////            story2.setText(model.title)
//
//
//        }
//    }


    fun View.setEnabledRecursively(enabled: Boolean) {
        isEnabled = enabled
        if (this is ViewGroup)
            (0 until childCount).map(::getChildAt).forEach { it.setEnabledRecursively(enabled) }
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

    fun clickCheckout(view: View) {
        addToCart()
//        Toast.makeText(this@ScrollingActivity, "checkout", Toast.LENGTH_SHORT).show()
        alerter.alert?.hide()

        launchActivity<MainActivity> {
            putExtra("root", "cart")
        }

        finish()






        //        main_alert_background.visibility=ConstraintLayout.GONE
//        setViewAndChildrenEnabled(main_container,true)
//        setViewAndChildrenEnabled(nested,true)
//        nested.stopNestedScroll()
//        nested.isNestedScrollingEnabled=true

    }

    fun continueShoppingClick(view: View) {
        addToCart()
//        Toast.makeText(this@ScrollingActivity, "continue", Toast.LENGTH_SHORT).show()

        alerter.alert?.hide()
//        main_alert_background.visibility=ConstraintLayout.GONE
//        setViewAndChildrenEnabled(main_container,true)
//        setViewAndChildrenEnabled(nested,true)
//        nested.stopNestedScroll()
//        nested.isNestedScrollingEnabled=true
    }


    fun addToCart() {


        Log.d("nofitem", Repository.cartLocalItems.value?.size.toString())

        var item: AddToCartModel = AddToCartModel(
            UserInfo.uid, UserInfo.access_token,
            -1, false, number_button2.number.toInt(), UserInfo.device_token
        )
//
//         var myAnim : Animation = AnimationUtils.loadAnimation(this@OnDetailsActivity, R.anim.scale)
//        binding.productImage.startAnimation(myAnim)



        if (Repository.cartLocalItems.value?.size != null) {
            Log.d("nofitem", "heyyy")
            no_items = Repository.cartLocalItems.value?.size!!


        }
        if (intent.getStringExtra("type") == "bundle") {


            item.Isbundle = true


            Log.d("nofitem", "bundle")
            val bundle: BundleProduct? = intent.getParcelableExtra("BundleItem")
            item.ProductID = bundle!!.BundleID

            Repository.addItemCartLocal(
                CartItem(
                    no_items + 1.toLong(),
                    bundle.BundleID
                    ,
                    -1,
                    "bundle",
                    bundle.BundleImage,
                    bundle.BundleName_En,
                    bundle.BundleDescription_En,
                    number_button2.number.toInt(),
                    bundle.BundleStockAvaliable,
                    0F.toString(),
                    bundle.BundlePrice.toDouble(),
                    false
                )
            )


//            product_name.text = bundle?.BundleName_En
//            product_price.text = bundle?.BundlePrice + " " + getString(R.string._sar)
//            calories.visibility = TextView.INVISIBLE
//            cal_icon.visibility = ImageView.INVISIBLE
//            viewPager.adapter = sectionsPagerAdapter


        } else {
            item.Isbundle = false
            if (intent.getStringExtra("type") == "product") {


                Log.d("nofitem", "product")
//                viewPager.adapter = sectionsPagerAdapterNotBundle
                val product: Product? = intent.getParcelableExtra("ProductItem")


                item.ProductID = product!!.ProductID

                if (product.ProductOfferDicountValue.toFloat() > 0F) {

                    Repository.addItemCartLocal(
                        CartItem(

                            no_items + 1.toLong(),
                            product.ProductID
                            ,
                            product.ProductOfferID.toInt(),
                            "productOff",
                            product.ProductImage,
                            product.ProductName_En,
                            product.ProductDescription_En,
                            number_button2.number.toInt(),
                            product.ProductStockQuantity,
                            product.ProductOfferDicountValue,
                            product.ProductOfferPrice.toDouble(),
                            false
                        )
                    )


                } else {
                    Repository.addItemCartLocal(
                        CartItem(
                            no_items + 1.toLong(),
                            product.ProductID
                            ,
                            -1,
                            "product",
                            product.ProductImage,
                            product.ProductName_En,
                            product.ProductDescription_En,
                            number_button2.number.toInt(),
                            product.ProductStockQuantity,
                            0F.toString(),
                            product.ProductPrice.toDouble(),
                            false
                        )
                    )
                }



//                Glide.with(this)
////                .load(model.img)
//
//                    .load(product?.ProductImage)
//                    .centerCrop()
//                    .into(binding.productImage)
//
//
//                product_name.text = product?.ProductName_En
//                product_price.text = product?.ProductPrice + " " + getString(R.string._sar)
//                calories.text =
//                    product?.ProductCallories.toString() + " " + getString(R.string.calName)


            } else {
//                viewPager.adapter = sectionsPagerAdapterNotBundle
                val offer: OfferProduct? = intent.getParcelableExtra("OfferItem")
                item.ProductID = offer!!.ProductID
                Repository.addItemCartLocal(
                    CartItem(
                        no_items + 1.toLong(),
                        offer.ProductID
                        ,
                        offer.OfferID,
                        "productOff",
                        offer.ProductImage,
                        offer.ProductName_En,
                        offer.ProductDescreption_En,
                        number_button2.number.toInt(),
                        offer.ProductStockQuantity,
                        offer.OfferDicountValuePercentage,
                        offer.OfferPrice.toDouble(),
                        false
                    )
                )
//                Log.d("checker",offer.OfferDicountValuePercentage)

            }

        }

        item.DeviceToken = UserInfo.device_token

        if (!UserInfo.signed) {

//old work for No login Backend on Mine
//           Repository.getCartDetailsNoLogin(Repository.prodsLocal,Repository.priceLocal,Repository.isbundLocal,Repository.offeridsLocal,
//               Repository.discountLocal,Repository.stockquanLocal,Repository.quantityLocal)


            item.AccessToken = "rr"
            item.UserID = 0


            Repository.addToCart(item)


        } else {
            viewModel.addCartItemOn(item)

        }


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
//                Toast.makeText(applicationContext, "click search", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MainActivity::class.java)

                intent.putExtra("root", "search")


//            intent.putExtra(EXTRA_MESSAGE, message)
                launchActivity<MainActivity> {
                    putExtra("root", "search")
                }
//                startActivityForResult(intent, 1)
//                finish()
                true
            }
            R.id.action_cart -> {
//                Toast.makeText(applicationContext, "click cart", Toast.LENGTH_LONG).show()

                launchActivity<MainActivity> {
                    putExtra("root", "cart")
                }

                finish()
//
                return true

            }
            android.R.id.home -> {
                finish()


//                RxAnimation.together(
//                    RxAnimation.together(
//                                toolbar.translation(0F, -60F,duration = 100L),
//                                nested_product.translation(0F, 90F,duration = 100L))
//                    ,
//                               Completable.fromRunnable { supportFinishAfterTransition()
//                               }
//                                )  .subscribe().addTo(composite)


                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {


        val itemData = menu?.findItem(R.id.action_cart)
        val actionView = itemData?.actionView as CartCounterActionView
        actionView.setItemData(menu, itemData)
//        Repository.getCartLocal().observe(this, Observer { count ->
//
//            actionView.count = count.size
//            //            binding.wordText.text = newWord
//
//
//        })


        Repository.getCartCount.observe(this, Observer { count ->

            actionView.count = count
//            badgeDrawable.isVisible = true
//
//            badgeDrawable.number = count
//            if (count == 0)
//                badgeDrawable.isVisible = false
//            //            binding.wordText.text = newWord


        })

        return super.onPrepareOptionsMenu(menu)

    }


    fun setupWindowAnimations() { // We are not interested in defining a new Enter Transition. Instead we change default transition duration
        window.enterTransition.duration =
            resources.getInteger(R.integer.anim_duration_long).toLong()
    }

    override fun onResume() {
        super.onResume()
        RxAnimation.together(
            toolbar.translation(0F, .60F, duration = 1000L),
            nested_product.translation(0F, -.90F, duration = 1000L)
        )
            .subscribe().addTo(composite)


    }


    override fun onPause() {
        super.onPause()
        composite.clear()
    }


    fun addItemToCartAnimation(
        targetView: ImageView,
//        item: ClipData.Item,
        quantity: Int

    ) {
        val destView = binding.toolbar.menu.getItem(1).actionView

        CircleAnimationUtil().attachActivity(this).setTargetView(targetView).setMoveDuration(300)
            .setDestView(destView).setAnimationListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {}
                override fun onAnimationEnd(animation: Animator?) {
//                    addItemToCart(item, quantity)

                    addToCart()
                }

                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            }).startAnimation()
    }


}

