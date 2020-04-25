package sa.biotic.app

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.external.modules.ItemSelectionModule
import com.idanatz.oneadapter.external.modules.ItemSelectionModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import sa.biotic.app.databinding.ActivityAllProductsBinding
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Category
import sa.biotic.app.model.Product
import sa.biotic.app.viewmodels.AllProductsViewModel


class AllProductsActivity : AppCompatActivity() {
    private lateinit var viewModel: AllProductsViewModel
    private lateinit var binding: ActivityAllProductsBinding
    private lateinit var oneAdapter: OneAdapter
    private lateinit var prodsAdapter: OneAdapter
    private lateinit var bundlesAdapter: OneAdapter
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
//        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_all_products)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.title = "hello"


//        val navView: BottomNavigationView = binding.navView
//            findViewById(R.id.nav_view)

        viewModel = ViewModelProviders.of(this).get(AllProductsViewModel::class.java)
//        binding.viewPager.setPageTransformer(true,  ZoomOutTranformer())
//        binding.pageIndicatorView.setViewPager(binding.viewPager)

//         val view_pager : ViewPager= binding.viewPager

        /** Setting up LiveData observation relationship **/
//        viewModel.offersLive?.observe(this, Observer { newOffers ->
////            binding.wordText.text = newWord
//
////            view_pager.adapter=OfferPagerAdapter(this,newOffers);
//
////            binding.categoryRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,true)
        binding.productsRecycler.layoutManager =
            GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
////            binding.bundlesRecycler.layoutManager = LinearLayoutManager(this,RecyclerView.HORIZONTAL,true)
//
//
//
////            adapterCreation()
//
//
//        })
        productsAdapterCreation()
        binding.toolbar.title = "Products"


//        textMessage = findViewById(R.id.message)
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

//        binding.viewPager.addOnPageChangeListener(
//            object : ViewPager.OnPageChangeListener {
//                override fun onPageSelected(p0: Int) {
//                    // no-op
//                }
//
//                override fun onPageScrolled(p0: Int, p1: Float, p2: Int) {
//                    // no-op
//                }
//
//                override fun onPageScrollStateChanged(p0: Int) {
//                    when (p0) {
//                        ViewPager.SCROLL_STATE_SETTLING -> {
////                            if (binding.viewPager.currentItem == images.size - 1) {
////                                binding.buttonNext.text = "end"
////                            } else {
////                                binding.buttonNext.text = "next"
////                            }
//                        }
//                        ViewPager.SCROLL_STATE_IDLE -> {
////                            isLastPage = binding.viewPager.currentItem == images.size - 1
//                        }
//                        else -> {
//                            // no-op
//                        }
//                    }
//                }
//            }
//        )
    }

    private fun productsAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        prodsAdapter = OneAdapter(binding.productsRecycler)
            .attachItemModule(
                productItem()
                    .addEventHook(clickProductEventHook())
            )
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

        viewModel.prodsLive.observe(this, Observer { newProds ->
            prodsAdapter.setItems(newProds)


        })
    }


    private fun categoryItem(): ItemModule<Category> = object : ItemModule<Category>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.category

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@AllProductsActivity,
                    R.animator.category_anim
                )
            }
        }

        override fun onBind(model: Category, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.category_image)
//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(this@AllProductsActivity)
                .load(model.icon)
//                .centerCrop()
                .load(model.icon).into(story1)


//            story2.setText(model.title)


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

//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(this@AllProductsActivity)
//                .load(model.img)

                .load(model.img).centerCrop().into(story1)

            story2.text = model.title
            story3.text = model.price
            story4.text = model.description


//            story2.setText(model.title)


        }
    }

    private fun bundleItem(): ItemModule<BundleProds> = object : ItemModule<BundleProds>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@AllProductsActivity,
                    R.animator.category_anim
                )
            }
        }

        override fun onBind(model: BundleProds, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)

//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(this@AllProductsActivity)
//                .load(model.img)

                .load(model.img).centerCrop().into(story1)

            story2.text = model.title
            story3.text = model.price
            story4.text = model.description


//            story2.setText(model.title)


        }
    }

    private fun clickProductEventHook(): ClickEventHook<Product> =
        object : ClickEventHook<Product>() {
            override fun onClick(model: Product, viewBinder: ViewBinder) {
//            Toast.makeText(this@AllProductsActivity, "${model.title} clicked", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@AllProductsActivity, ScrollingActivity::class.java)
                intent.putExtra("product_name", model.title)
                intent.putExtra("product_image", model.img)
                intent.putExtra("product_price", model.price)
                intent.putExtra("type", "bundle")


//            intent.putExtra(EXTRA_MESSAGE, message)
                startActivityForResult(intent, 1)
//        }

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

            }


        }


    private fun clickBundleEventHook(): ClickEventHook<BundleProds> =
        object : ClickEventHook<BundleProds>() {
            override fun onClick(model: BundleProds, viewBinder: ViewBinder) {
                Toast.makeText(
                    this@AllProductsActivity,
                    "${model.title} clicked",
                    Toast.LENGTH_SHORT
                ).show()


            }


        }

    private fun clickEventHook(): ClickEventHook<Category> = object : ClickEventHook<Category>() {
        override fun onClick(model: Category, viewBinder: ViewBinder) {
//            Toast.makeText(this@MainActivity, "${model.title} clicked", Toast.LENGTH_SHORT).show()

            if (prevRecyclerBinderView != null) {

                prevRecyclerBinderView!!.findViewById<LinearLayout>(R.id.category_background)
                    .setBackgroundResource(R.drawable.category_frame)
                prevRecyclerBinderView!!.findViewById<ImageView>(R.id.category_image)
                    .setColorFilter(
                        ContextCompat.getColor(this@AllProductsActivity, R.color.purple),
                        android.graphics.PorterDuff.Mode.SRC_IN
                    )

            }

            viewBinder.findViewById<LinearLayout>(R.id.category_background)
                .setBackgroundResource(R.drawable.category_frame_clicked)
            viewBinder.findViewById<ImageView>(R.id.category_image)
                .setColorFilter(
                    ContextCompat.getColor(this@AllProductsActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )

            prevRecyclerBinderView = viewBinder

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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}


