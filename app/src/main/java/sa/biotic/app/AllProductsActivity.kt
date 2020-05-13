package sa.biotic.app


import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.interfaces.Item
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import com.skydoves.androidribbon.ShimmerRibbonView
import sa.biotic.app.databinding.ActivityAllProductsBinding
import sa.biotic.app.model.BundleProduct
import sa.biotic.app.model.OfferProduct
import sa.biotic.app.model.Product
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.utils.TransitionHelper
import sa.biotic.app.viewmodels.AllProductsViewModel



class AllProductsActivity : AppCompatActivity() {
    private lateinit var viewModel: AllProductsViewModel
    private lateinit var binding: ActivityAllProductsBinding
    private lateinit var oneAdapter: OneAdapter
    private lateinit var prodsAdapter: OneAdapter
    private lateinit var bundlesAdapter: OneAdapter
    private lateinit var offerProdsAdapter: OneAdapter
    private var prevRecyclerBinderView: ViewBinder? = null

////    private lateinit var textMessage: TextView
//    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
//        when (item.itemId) {
//            R.id.navigation_home -> {
////                textMessage.setText(R.string.title_home)
////                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_dashboard -> {
////                textMessage.setText(R.string.title_dashboard)
////                return@OnNavigationItemSelectedListener true
//            }
//            R.id.navigation_notifications -> {
////                textMessage.setText(R.string.title_notifications)
////                return@OnNavigationItemSelectedListener true
//            }
//        }
//        false
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_products)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)



        viewModel = ViewModelProviders.of(this).get(AllProductsViewModel::class.java)


//        val r = Integer.valueOf(resources.getColor(R.color.colorPrimary).toString().substring(1, 3), 16)
//        val g = Integer.valueOf(resources.getColor(R.color.colorPrimary).toString().substring(3, 5), 16)
//        val b = Integer.valueOf(resources.getColor(R.color.colorPrimary).toString().substring(5, 7), 16)

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



        binding.productsRecycler.layoutManager =
            GridLayoutManagerWrapper(this, 2, GridLayoutManager.VERTICAL, false)
        binding.productsRecycler.setRecyclerListener {
            it.setIsRecyclable(false)
        }

        if (intent.getStringExtra("type") == "OfferProducts") {

            viewModel.getOfferList(intent.getIntExtra("offerId", 0))


            binding.swipeRefresh.setOnRefreshListener {

                viewModel.getOfferList(intent.getIntExtra("offerId", 0))
            }

        }

        productsAdapterCreation()

    }

    private fun productsAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        if (intent.getStringExtra("type") == "Product") {


            prodsAdapter = OneAdapter(binding.productsRecycler)
                .attachItemModule(
                    productItem()
                        .addEventHook(clickProductEventHook())
                )

            binding.toolbar.title = getString(R.string.products)

            binding.swipeRefresh.setOnRefreshListener {

                Repository.getAllProducts(1, 200)
            }

            viewModel.prodsLive.observe(this, Observer { newProds ->
                prodsAdapter.setItems(newProds)

                binding.noOfPr.text = newProds.size.toString() + " Items"

                binding.swipeRefresh.isRefreshing = false


            })
        } else {

            if (intent.getStringExtra("type") == "OfferProducts") {
                offerProdsAdapter = OneAdapter(binding.productsRecycler)
                    .attachItemModule(
                        offerProductItem()
                            .addEventHook(clickOfferProductEventHook())
                    )

                binding.toolbar.title = getString(R.string.offers)




                viewModel.offersProdsLive.observe(this, Observer { newProds ->
                    offerProdsAdapter.setItems(newProds)

                    binding.noOfPr.text = newProds.size.toString() + " Items"
                    binding.swipeRefresh.isRefreshing = false



                })
            } else {
                bundlesAdapter = OneAdapter(binding.productsRecycler)
                    .attachItemModule(
                        bundleItem()
                            .addEventHook(clickBundleEventHook())
                    )



                binding.swipeRefresh.setOnRefreshListener {

                    Repository.getBundles(1, 200)
                }
                binding.toolbar.title = getString(R.string.bundles)

                viewModel.bundlesLive.observe(this, Observer { newProds ->
                    bundlesAdapter.setItems(newProds)

                    binding.noOfPr.text = newProds.size.toString() + " Items"

                    binding.swipeRefresh.isRefreshing = false


                })
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
////
////            prevRecyclerBinderView = viewBinder
                val intent = Intent(this@AllProductsActivity, OnDetailsActivity::class.java)
////                intent.putExtra("product_name", model.title)
////                intent.putExtra("product_image", model.img)
////                intent.putExtra("product_price", model.price)
//                intent.putExtra("type", "product")
//                intent.putExtra("ProductItem", model)
//
//
////            intent.putExtra(EXTRA_MESSAGE, message)
//                startActivityForResult(intent, 1)

                // Create pair of transition participants.
                // Create pair of transition participants.
                var sharedView = viewBinder.rootView.findViewById<View>(R.id.product_image)
                var transitionName = getString(R.string.hero_image)
                var sharedView2 = viewBinder.rootView.findViewById<View>(R.id.product_title)
                var transitionName2 = getString(R.string.hero_name)
                var participants: Array<android.util.Pair<View, String>> = arrayOf()


//                participants[0]=android.util.Pair(sharedView,transitionName)
//                participants[1]=Pair(sharedView2,transitionName2)


                val pairs: Array<Pair<View, String>> =
                    TransitionHelper.createSafeTransitionParticipants(
                        this@AllProductsActivity, false,
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
                        this@AllProductsActivity,
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


    private fun clickOfferProductEventHook(): ClickEventHook<OfferProduct> =
        object : ClickEventHook<OfferProduct>() {
            override fun onClick(model: OfferProduct, viewBinder: ViewBinder) {
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
//                val intent = Intent(this@AllProductsActivity, ScrollingActivity::class.java)
////                intent.putExtra("product_name", model.title)
////                intent.putExtra("product_image", model.img)
////                intent.putExtra("product_price", model.price)
//                intent.putExtra("type", "product")
//                intent.putExtra("ProductItem", model)
//
//
////            intent.putExtra(EXTRA_MESSAGE, message)
//                startActivityForResult(intent, 1)


                val intent = Intent(this@AllProductsActivity, OnDetailsActivity::class.java)

                var sharedView = viewBinder.rootView.findViewById<View>(R.id.product_image)
                var transitionName = getString(R.string.hero_image)
                var sharedView2 = viewBinder.rootView.findViewById<View>(R.id.product_title)
                var transitionName2 = getString(R.string.hero_name)

                val pairs: Array<Pair<View, String>> =
                    TransitionHelper.createSafeTransitionParticipants(
                        this@AllProductsActivity, false,
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
                        this@AllProductsActivity,
                        *pairs
                    )


                intent.putExtra("type", "offer")
                intent.putExtra("OfferItem", model)

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
                        this@AllProductsActivity, false,
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
                        this@AllProductsActivity,
                        *pairs
                    )


                val intent = Intent(this@AllProductsActivity, OnDetailsActivity::class.java)

                intent.putExtra("BundleItem", model)
                intent.putExtra("type", "bundle")



                startActivity(intent, transitionActivityOptions.toBundle())
            }


        }




    private fun productItem(): ItemModule<Product> = object : ItemModule<Product>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item_for_all

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@AllProductsActivity,
                    R.animator.category_anim
                )
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
//
//
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
            Glide.with(this@AllProductsActivity)

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
                    story6.visibility = TextView.GONE
                    story8.visibility = TextView.GONE


                } else {
                    story8.text =
                        getString(R.string.only) + " " + item.model.ProductStockQuantity.toString() + " " + getString(
                            R.string.left
                        )
                    story8.visibility = TextView.VISIBLE
                }

            } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
                story8.visibility = TextView.GONE
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


    private fun offerProductItem(): ItemModule<OfferProduct> = object : ItemModule<OfferProduct>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item_for_all

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@AllProductsActivity,
                    R.animator.category_anim
                )
            }
        }


        override fun onBind(item: Item<OfferProduct>, viewBinder: ViewBinder) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.calories)
            val story6 = viewBinder.findViewById<TextView>(R.id.oldprice)
            val story7 = viewBinder.findViewById<TextView>(R.id.discount_value)
            val story8 = viewBinder.findViewById<TextView>(R.id.stock_tv)
            val ribbon: ShimmerRibbonView = viewBinder.findViewById<ShimmerRibbonView>(R.id.ribbon)
            val ribbon2: ShimmerRibbonView =
                viewBinder.findViewById<ShimmerRibbonView>(R.id.ribbon2)

//
            if (item.model.IsNew == 1) {
                ribbon.visibility = View.VISIBLE
                ribbon2.visibility = View.VISIBLE
            } else {
                ribbon.visibility = View.INVISIBLE
                ribbon2.visibility = View.INVISIBLE
            }


            Glide.with(this@AllProductsActivity)

                .load(item.model.ProductImage).centerCrop().into(story1)

            story2.text = item.model.ProductName_En
            story3.text = "%.2f".format(item.model.ProductPrice) + " " + getString(R.string._sar)
            story4.text = item.model.ProductDescreption_En
            story5.text = item.model.ProductCallories.toString()
            story7.text =
                (item.model.OfferDicountValuePercentage.toFloat() * 100).toInt().toString() + "%"






            story6.paintFlags =
                story6.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG



            if (item.model.OfferPrice.toFloat() > 0) {
                story3.text = item.model.OfferPrice.toString() + " " + getString(R.string._sar)
                story6.text = item.model.ProductPrice.toString() + " " + getString(R.string._sar)
                story6.visibility = TextView.VISIBLE
                story3.visibility = TextView.VISIBLE
            }


            if (item.model.ProductStockQuantity <= 5) {

                if (item.model.ProductStockQuantity <= 0) {
                    story3.text = getString(R.string.sold_out)
                    story3.setTextColor(resources.getColor(R.color.stockColor))
//                    story3.visibility=TextView.INVISIBLE
                    story6.visibility = TextView.INVISIBLE
                    story8.visibility = TextView.INVISIBLE

                } else {
                    story8.text =
                        getString(R.string.only) + " " + item.model.ProductStockQuantity.toString() + " " + getString(
                            R.string.left
                        )
                }
            } else {


                story8.visibility = TextView.INVISIBLE
            }
        }
    }

    private fun bundleItem(): ItemModule<BundleProduct> = object : ItemModule<BundleProduct>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.bundle_item_for_all

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@AllProductsActivity,
                    R.animator.category_anim
                )
            }
        }


        override fun onBind(item: Item<BundleProduct>, viewBinder: ViewBinder) {
//            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)

//            val story8 = viewBinder.findViewById<TextView>(R.id.oldprice)
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
            Glide.with(this@AllProductsActivity)
//                .load(model.img)

                .load(item.model.BundleImage).centerCrop().into(story1)

            story2.text = item.model.BundleName_En
            story3.text = "%.2f".format(item.model.BundlePrice) + " " + getString(R.string._sar)
            story4.text = item.model.BundleDescription_En

//            story8.visibility = TextView.INVISIBLE

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



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}


