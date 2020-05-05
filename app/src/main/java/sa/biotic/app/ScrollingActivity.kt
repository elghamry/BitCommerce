package sa.biotic.app

import SectionsPagerAdapter
import SectionsPagerAdapterNotBundle
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.NestedScrollView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayout
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import com.rowland.cartcounter.view.CartCounterActionView
import kotlinx.android.synthetic.main.activity_scrolling.*
import kotlinx.android.synthetic.main.content_scrolling.*
import sa.biotic.app.components.alerter.Alerter
import sa.biotic.app.components.alerter.OnHideAlertListener
import sa.biotic.app.components.alerter.OnShowAlertListener
import sa.biotic.app.databinding.ActivityScrollingBinding
import sa.biotic.app.model.*
import sa.biotic.app.retrofit_service.Repository


class ScrollingActivity : AppCompatActivity() {


    private lateinit var reviewsAdapter: OneAdapter
    lateinit var main_container: ConstraintLayout
    lateinit var alerter: Alerter
    lateinit var alert_view: LinearLayout
    lateinit var main_alert_background: LinearLayout
    lateinit var nested: NestedScrollView
    private lateinit var binding: ActivityScrollingBinding

    lateinit var alert_title: String
    var no_items = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_scrolling)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scrolling)
        setSupportActionBar(toolbar)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val sectionsPagerAdapterNotBundle =
            SectionsPagerAdapterNotBundle(this, supportFragmentManager)
        val viewPager: ViewPager = view_pager


        val tabs: TabLayout = findViewById<TabLayout>(R.id.tabs)
        nested = findViewById<NestedScrollView>(R.id.nested_product)


        var addToCartButton: MaterialButton = findViewById(R.id.add_to_cart)
        main_alert_background = findViewById(R.id.Alert_main_back)
        main_container = findViewById(R.id.main_container)
        addToCartButton.setOnClickListener {
            //                                enableDisableActivty(false)

            main_alert_background.visibility = ConstraintLayout.VISIBLE
            main_alert_background.background.alpha = 200
//
            alerter =
//                   alerter
                Alerter.create(this@ScrollingActivity)


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
                    //                    Toast.makeText(this@KotlinDemoActivity, "Show Alert", Toast.LENGTH_LONG).show()

//                    main_container.setEnabledRecursively(false)
                    setViewAndChildrenEnabled(main_container, false)
                    setViewAndChildrenEnabled(nested, false)
                    nested.stopNestedScroll()
                    nested.isNestedScrollingEnabled = false


                })
                .setOnHideListener(OnHideAlertListener {
                    //                    Toast.makeText(this@KotlinDemoActivity, "Hide Alert", Toast.LENGTH_LONG).show()
                    main_alert_background.visibility = ConstraintLayout.GONE
                    setViewAndChildrenEnabled(main_container, true)
                    setViewAndChildrenEnabled(nested, true)
                    nested.stopNestedScroll()
                    nested.isNestedScrollingEnabled = true
                })

//                            .setOnClickListener()
//                            .addButton("hello",R.style.AlertStyle,onClick = )


                .show()


        }

//        if(Alerter.isShowing)
//        {
//            alert_view.findViewById<MaterialButton>(R.id.checkout)
//                .setOnClickListener {
//                    Toast.makeText(this@ScrollingActivity, "checkout", Toast.LENGTH_SHORT).show()
//                    Alerter.hide()
//                }
//            checkout.setOnClickListener {
//                Toast.makeText(this@ScrollingActivity, "checkout", Toast.LENGTH_SHORT).show()
//                Alerter.hide()
//            }
//            this.findViewById<MaterialButton>(R.id.contin).setOnClickListener {
//                Toast.makeText(this@ScrollingActivity, "continue", Toast.LENGTH_SHORT).show()
//                Alerter.hide()
//            }
//
//
//        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//        revsRec.layoutManager = LinearLayoutManager(this@ScrollingActivity, RecyclerView.VERTICAL,false)
//
//        reviewsAdapter = OneAdapter(revsRec)
//        reviewsAdapter.attachItemModule(reviewItem())
//        reviewsAdapter.add(Review(1,"hello",2,"name"))

////        if(!nested_product.canScrollVertically(-1)) {
////            // we have reached the top of the list
////            toolbar.elevation = 0f
////            app_bar.elevation=0f
////            toolbar_layout.elevation=0f
////        } else {
////            // we are not at the top yet
////            toolbar.elevation = 50f
////        }
//supportActionBar?.elevation=0f

//        nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//            if (scrollY > oldScrollY) {
//                Log.d("tag", "Scroll DOWN")
//            }
//            if (scrollY < oldScrollY) {
//                Log.d("tag", "Scroll UP")
//            }
//
//            if (scrollY == 0) {
//                Log.d("tag", "TOP SCROLL")
//            }
//
//            if (scrollY == v.measuredHeight - v.getChildAt(0).measuredHeight) {
//                Log.d("tag", "BOTTOM SCROLL")
//            }
//        }
//        )

//        nested.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
//
//            Toast.makeText(this@ScrollingActivity, "hello clicked", Toast.LENGTH_SHORT).show()
//        })
//        nested_product.setOnScrollChangeListener {
//                _: NestedScrollView?, _: Int, scrollY: Int, _: Int, _:
//            Int ->
////            Log.d("hello")
//
//            if (scrollY >= 1)
//            {
//                Toast.makeText(this@ScrollingActivity, "hello clicked", Toast.LENGTH_SHORT).show()
//            }
//
////            if (nested_product.canScrollVertically(-1) ){
//
////                    // Remove elevation
////                app_bar.elevation = 0f
////                toolbar_layout.elevation = 0f
////                }
////            else {
////                // Show elevation
////                app_bar.elevation = 4f
////
//            }
//            if (scrollY >= 1) {
//
////                if (isExpanded) {
////                    isExpanded = false
////                    animator.start()
////                }
////                app_bar.elevation = 0f
////                toolbar_layout.elevation=0f
//////
//////                toolbar_layout.visibility=LinearLayout.INVISIBLE
//////
////                toolbar.elevation=0f
//////                nested_product.elevation=0f
////                app_bar.elevation=0f
////            } else {
////                if (!isExpanded) {
////                    isExpanded = true
////                    animator.cancel()
////                app_bar.elevation = 0f
////                toolbar_layout.elevation = 0f
////
//////                }
//////            }
////            }
////            else
////            {
////                app_bar.elevation = 100f
////                toolbar_layout.elevation = 0f
//            }

//            if (nested_product.canScrollVertically(-1) {
//                    // Remove elevation
//                }
//            else {
//                // Show elevation
//            }
//        }
//        toolbar.setBackgroundColor(resources.getColor(android.R.color.transparent))
//        toolbar.setBackground(resources.getDrawable(R.drawable.toolbar_background))
//        toolbar.setElevation(0.0f)

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        val product_iv = findViewById<ImageView>(R.id.product_image)
        val product_tv = findViewById<TextView>(R.id.product_name)
        val product_price = findViewById<TextView>(R.id.product_price)



        if (intent.getStringExtra("type") == "bundle") {
            val bundle: BundleProds? = intent.getParcelableExtra("BundleItem")

            alert_title =
                bundle?.BundleName_En + "\n" + bundle?.BundleDescription_En + "\n" + bundle?.BundlePrice + " " + getString(
                    R.string._sar
                )

            Glide.with(this)
//                .load(model.img)

                .load(bundle?.BundleImage)
                .centerCrop()
                .into(binding.productImage)


            product_name.text = bundle?.BundleName_En
            product_price.text = bundle?.BundlePrice + " " + getString(R.string._sar)
            smart_rating_bar.ratingNum = bundle?.BundleReviews!!.toFloat()
            calories.visibility = TextView.INVISIBLE
            cal_icon.visibility = ImageView.INVISIBLE
            viewPager.adapter = sectionsPagerAdapter


        } else {
            if (intent.getStringExtra("type") == "product") {
                viewPager.adapter = sectionsPagerAdapterNotBundle
                val product: Product? = intent.getParcelableExtra("ProductItem")
                alert_title =
                    product?.ProductName_En + "\n" + product?.ProductDescription_En + "\n" + product?.ProductPrice + " " + getString(
                        R.string._sar
                    )
                Glide.with(this)
//                .load(model.img)

                    .load(product?.ProductImage)
                    .centerCrop()
                    .into(binding.productImage)


                product_name.text = product?.ProductName_En
                product_price.text = product?.ProductPrice + " " + getString(R.string._sar)
                smart_rating_bar.ratingNum = product?.ProductReviews!!.toFloat()
                calories.text =
                    product.ProductCallories.toString() + " " + getString(R.string.calName)

                if (product.ProductOfferPrice.toFloat() > 0F) {
                    now.visibility = TextView.VISIBLE
                    was.visibility = TextView.VISIBLE
                    product_prev_price.visibility = TextView.VISIBLE
                    product_price.text = product.ProductOfferPrice + " " + getString(R.string._sar)
                    product_prev_price.paintFlags =
                        product_prev_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    product_prev_price.text = product.ProductPrice + " " + getString(R.string._sar)

                }


            } else {

                viewPager.adapter = sectionsPagerAdapterNotBundle
                val offer: Offer? = intent.getParcelableExtra("OfferItem")

                alert_title =
                    offer?.ProductName_En + "\n" + offer?.OfferDescription_En + "\n" + offer?.OfferPrice + " " + getString(
                        R.string._sar
                    )
                Glide.with(this)
//                .load(model.img)

                    .load(offer?.OfferImage)
                    .centerCrop()
                    .into(binding.productImage)


                product_name.text = offer?.ProductName_En
                product_price.text = offer?.OfferPrice + " " + getString(R.string._sar)
                product_prev_price.text = offer?.ProductPrice + " " + getString(R.string._sar)
                smart_rating_bar.ratingNum = offer?.ProductReviews!!.toFloat()
                calories.text = offer.ProductCallories
                product_prev_price.paintFlags =
                    product_prev_price.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
//                calories.setText(offer?.ProductCallories.toString()+" "+getString(R.string.calName))
                now.visibility = TextView.VISIBLE
                was.visibility = TextView.VISIBLE
                product_prev_price.visibility = TextView.VISIBLE

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
        finish()
        return super.onSupportNavigateUp()
    }

    private fun clickEventHook(): ClickEventHook<Review> = object : ClickEventHook<Review>() {
        override fun onClick(model: Review, viewBinder: ViewBinder) {
            Toast.makeText(this@ScrollingActivity, "${model.name} clicked", Toast.LENGTH_SHORT)
                .show()


        }


    }


    private fun reviewItem(): ItemModule<Review> = object : ItemModule<Review>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.review_item

//            override fun withFirstBindAnimation(): Animator {
//                // can be implemented by inflating Animator Xml
//                return AnimatorInflater.loadAnimator(this@ReviewsFragment.context, R.animator.category_anim)
//            }
        }


        override fun onBind(model: Review, viewBinder: ViewBinder) {
//            val story1 = viewBinder.findViewById<RoundedImageView>(R.id.review_image)
//            val story2 = viewBinder.findViewById<TextView>(R.id.review_name)
//            val story3 = viewBinder.findViewById<sa.biotic.app.compnents.RatingBar>(R.id.smart_rating_bar)

            Log.d("hello", model.toString())


//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

////
//            Glide.with(this@ReviewsFragment)
////                .load(model.img)
//
//                .load(model.img).centerCrop().into(story1)


//            story2.setText(model.name)
//            story3.ratingNum=model.rate.toFloat()


//            story2.setText(model.title)


        }
    }


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

        if (Repository.cartLocalItems.value?.size != null) {
            Log.d("nofitem", "heyyy")
            no_items = Repository.cartLocalItems.value?.size!!


        }
        if (intent.getStringExtra("type") == "bundle") {

            Log.d("nofitem", "bundle")
            val bundle: BundleProds? = intent.getParcelableExtra("BundleItem")
            Repository.addItemCartLocal(
                CartItem(
                    no_items + 1.toLong(),
                    bundle!!.BundleID
                    ,
                    "bundle",
                    bundle.BundleImage,
                    bundle.BundleName_En,
                    bundle.BundleDescription_En,
                    number_button2.number.toInt(),
                    bundle.BundlePrice,
                    false
                )
            )


//            product_name.text = bundle?.BundleName_En
//            product_price.text = bundle?.BundlePrice + " " + getString(R.string._sar)
//            calories.visibility = TextView.INVISIBLE
//            cal_icon.visibility = ImageView.INVISIBLE
//            viewPager.adapter = sectionsPagerAdapter


        } else {
            if (intent.getStringExtra("type") == "product") {
                Log.d("nofitem", "product")
//                viewPager.adapter = sectionsPagerAdapterNotBundle
                val product: Product? = intent.getParcelableExtra("ProductItem")

                Repository.addItemCartLocal(
                    CartItem(
                        no_items + 1.toLong(),
                        product!!.ProductID
                        ,
                        "product",
                        product.ProductImage,
                        product.ProductName_En,
                        product.ProductDescription_En,
                        number_button2.number.toInt(),
                        product.ProductPrice,
                        false
                    )
                )
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
                val offer: Offer? = intent.getParcelableExtra("OfferItem")

                Repository.addItemCartLocal(
                    CartItem(
                        no_items + 1.toLong(),
                        offer!!.OfferID
                        ,
                        "bundle",
                        offer.OfferImage,
                        offer.ProductName_En,
                        offer.OfferDescription_En,
                        number_button2.number.toInt(),
                        offer.OfferPrice,
                        false
                    )
                )
//                Glide.with(this)
////                .load(model.img)
//
//                    .load(offer?.OfferImage)
//                    .centerCrop()
//                    .into(binding.productImage)
//
//
//                product_name.text = offer?.ProductName_En
//                product_price.text = offer?.OfferPrice + " " + getString(R.string._sar)
//                product_prev_price.text = offer?.ProductPrice + " " + getString(R.string._sar)
////                calories.setText(offer?.ProductCallories.toString()+" "+getString(R.string.calName))
//                now.visibility = TextView.VISIBLE
//                was.visibility = TextView.VISIBLE
//                product_prev_price.visibility = TextView.VISIBLE

            }

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
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {


        val itemData = menu?.findItem(R.id.action_cart)
        val actionView = itemData?.actionView as CartCounterActionView
        actionView.setItemData(menu, itemData)
        Repository.getCartLocal().observe(this, Observer { count ->

            actionView.count = count.size
            //            binding.wordText.text = newWord


        })

        return super.onPrepareOptionsMenu(menu)

    }


}
