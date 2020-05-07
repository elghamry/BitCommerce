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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import sa.biotic.app.databinding.ActivityAllProductsBinding
import sa.biotic.app.model.BundleProduct
import sa.biotic.app.model.OfferProduct
import sa.biotic.app.model.Product
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

        binding.productsRecycler.layoutManager =
            GridLayoutManagerWrapper(this, 2, GridLayoutManager.VERTICAL, false)

        if (intent.getStringExtra("type") == "OfferProducts") {

            viewModel.getOfferList(intent.getIntExtra("offerId", 0))

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

            viewModel.prodsLive.observe(this, Observer { newProds ->
                prodsAdapter.setItems(newProds)

                binding.noOfPr.text = newProds.size.toString() + " Items"


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


                })
            } else {
                bundlesAdapter = OneAdapter(binding.productsRecycler)
                    .attachItemModule(
                        bundleItem()
                            .addEventHook(clickBundleEventHook())
                    )
                binding.toolbar.title = getString(R.string.bundles)

                viewModel.bundlesLive.observe(this, Observer { newProds ->
                    bundlesAdapter.setItems(newProds)

                    binding.noOfPr.text = newProds.size.toString() + " Items"


                })
            }
        }




//            .attachItemModule(headerItem())
//            .attachItemModule(messageItem()

//                .addState(selectionState())

//                .addEventHook(swipeEventHook())
//            )
//            .attachEmptinessModule(emptinessModule())
//            .attachPagingModule(pagingModule())
//            .attachItemSelectionModule(itemSelectionModule())
//        bundlesAdapter = OneAdapter(binding.bundlesRecycler)
//            .attachItemModule(bundleItem()
//                .addEventHook(clickBundleEventHook()))


//        viewModel.bundlesLive?.observe(this, Observer { newBunds -> bundlesAdapter.setItems(newBunds)
//        })

//        viewModel.prodsLive.observe(this, Observer { newProds ->
//            prodsAdapter.setItems(newProds)
//
//
//        })
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


        override fun onBind(model: Product, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.calories)
            val story6 = viewBinder.findViewById<TextView>(R.id.oldprice)
            val story8 = viewBinder.findViewById<TextView>(R.id.stock_tv)
            val story7 = viewBinder.findViewById<TextView>(R.id.discount_value)
            val story9 = viewBinder.findViewById<CardView>(R.id.discount_card)

//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(this@AllProductsActivity)

//                .load(model.img)

                .load(model.ProductImage)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .skipMemoryCache(false).into(story1)

            story2.text = model.ProductName_En
            story3.text = model.ProductPrice + " " + getString(R.string._sar)
            story4.text = model.ProductDescription_En
            story5.text = model.ProductCallories.toString()


            story6.paintFlags =
                story6.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG




            if (model.ProductOfferPrice.toFloat() > 0) {
                story3.text = model.ProductOfferPrice + " " + getString(R.string._sar)
                story6.text = model.ProductPrice + " " + getString(R.string._sar)
                story6.visibility = TextView.VISIBLE
                story3.visibility = TextView.VISIBLE



            } else {

                story6.visibility = TextView.INVISIBLE


            }
            if (model.ProductOfferDicountValue.toFloat() <= 0F) {
                story9.visibility = CardView.GONE
            }

            story7.text = (model.ProductOfferDicountValue.toFloat() * 100).toInt().toString() + "%"



            if (model.ProductStockQuantity <= 5) {
                if (model.ProductStockQuantity <= 0) {
                    story3.text = getString(R.string.sold_out)
                    story3.setTextColor(resources.getColor(R.color.stockColor))
//                    story3.visibility=TextView.INVISIBLE
                    story6.visibility = TextView.INVISIBLE
                    story8.visibility = TextView.INVISIBLE

                } else
                    story8.text =
                        getString(R.string.only) + " " + model.ProductStockQuantity.toString() + " " + getString(
                            R.string.left
                        )
            } else {

//                    story8.text=getString(R.string.only)+" "+model.ProductStockQuantity.toString()+" "+getString(R.string.left)
                story8.visibility = TextView.INVISIBLE
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


        override fun onBind(model: OfferProduct, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.calories)
            val story6 = viewBinder.findViewById<TextView>(R.id.oldprice)
            val story7 = viewBinder.findViewById<TextView>(R.id.discount_value)
            val story8 = viewBinder.findViewById<TextView>(R.id.stock_tv)


            Glide.with(this@AllProductsActivity)

                .load(model.ProductImage).centerCrop().into(story1)

            story2.text = model.ProductName_En
            story3.text = model.ProductPrice + " " + getString(R.string._sar)
            story4.text = model.ProductDescreption_En
            story5.text = model.ProductCallories.toString()
            story7.text =
                (model.OfferDicountValuePercentage.toFloat() * 100).toInt().toString() + "%"






            story6.paintFlags =
                story6.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG



            if (model.OfferPrice.toFloat() > 0) {
                story3.text = model.OfferPrice + " " + getString(R.string._sar)
                story6.text = model.ProductPrice + " " + getString(R.string._sar)
                story6.visibility = TextView.VISIBLE
                story3.visibility = TextView.VISIBLE
            }


            if (model.ProductStockQuantity <= 5) {

                if (model.ProductStockQuantity <= 0) {
                    story3.text = getString(R.string.sold_out)
                    story3.setTextColor(resources.getColor(R.color.stockColor))
//                    story3.visibility=TextView.INVISIBLE
                    story6.visibility = TextView.INVISIBLE
                    story8.visibility = TextView.INVISIBLE

                } else {
                    story8.text =
                        getString(R.string.only) + " " + model.ProductStockQuantity.toString() + " " + getString(
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

        override fun onBind(model: BundleProduct, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)

            val story8 = viewBinder.findViewById<TextView>(R.id.oldprice)
            val story9 = viewBinder.findViewById<TextView>(R.id.stock_tv)



//
            Glide.with(this@AllProductsActivity)
//                .load(model.img)

                .load(model.BundleImage).centerCrop().into(story1)

            story2.text = model.BundleName_En
            story3.text = model.BundlePrice + " " + getString(R.string._sar)
            story4.text = model.BundleDescription_En

            story8.visibility = TextView.INVISIBLE

            if (model.BundleStockAvaliable == 0) {

                story9.visibility = TextView.VISIBLE
                story3.visibility = TextView.INVISIBLE
                story9.text = "Sold out"
//                story3.setTextColor(resources.getColor(R.color.stockColor))

            } else {
                if (model.BundleStockAvaliable <= 5) {
                    Log.d("iam here bitch", "hello friend")
                    story9.text =
                        getString(R.string.only) + " " + model.BundleStockAvaliable.toString() + " " + getString(
                            R.string.left
                        )
                    story9.visibility = TextView.VISIBLE

                }
            }


        }
    }



    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}


