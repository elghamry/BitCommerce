package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.eftimoff.viewpagertransformers.ZoomOutTranformer
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.external.modules.ItemSelectionModule

import com.idanatz.oneadapter.external.modules.ItemSelectionModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import kotlinx.android.synthetic.main.activity_main.*
import sa.biotic.app.AllProductsActivity
import sa.biotic.app.R
import sa.biotic.app.ScrollingActivity
import sa.biotic.app.adapters.OfferPagerAdapter
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.databinding.FragmentHomeBinding
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Category
import sa.biotic.app.model.Product
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.HomeViewModel


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




        binding.viewPager.setPageTransformer(true, ZoomOutTranformer())
        binding.pageIndicatorView.setViewPager(binding.viewPager)

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
        viewModel.offersLive.observe(this, Observer { newOffers ->
            //            binding.wordText.text = newWord

            view_pager.adapter = OfferPagerAdapter(requireContext(), newOffers)


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

        binding.viewPager.setOnClickListener {

            Toast.makeText(
                requireContext(),
                binding.viewPager.currentItem.toString() + " clicked",
                Toast.LENGTH_SHORT
            ).show()

//           Log.d("offer no ",binding.viewPager.currentItem.toString())


        }


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.GONE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 0F)

        return binding.root
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
        bundlesAdapter = OneAdapter(binding.bundlesRecycler)
            .attachItemModule(
                bundleItem()
                    .addEventHook(clickBundleEventHook())
            )








        viewModel.bundlesLive.observe(this, Observer { newBunds ->
            bundlesAdapter.setItems(newBunds)
        })

        viewModel.prodsLive.observe(this, Observer { newProds ->
            prodsAdapter.setItems(newProds)


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


        viewModel.catsLive.observe(this, Observer { newCats ->
            oneAdapter.setItems(newCats)
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

        override fun onBind(model: Category, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.category_image)
//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(requireContext())
                .load(model.CategoryIconPurble)
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


        override fun onBind(model: Product, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.calories)

//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(requireContext())
//                .load(model.img)

                .load(model.ProductImage).centerCrop().into(story1)

            story2.text = model.ProductName_En
            story3.text = model.ProductPrice + " SR"
            story4.text = model.ProductDescription_En
            story5.text = model.ProductCallories.toString()


//            story2.setText(model.title)


        }
    }

    private fun bundleItem(): ItemModule<BundleProds> = object : ItemModule<BundleProds>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(requireContext(), R.animator.category_anim)
            }
        }

        override fun onBind(model: BundleProds, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.price)
            val story4 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story5 = viewBinder.findViewById<TextView>(R.id.calories)
            val story6 = viewBinder.findViewById<ImageView>(R.id.cal_icon)
//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(requireContext())
//                .load(model.img)

                .load(model.BundleImage).centerCrop().into(story1)

            story2.text = model.BundleName_En
            story3.text = model.BundlePrice + " SR"
            story4.text = model.BundleDescription_En
            story5.visibility = TextView.INVISIBLE
            story6.visibility = ImageView.INVISIBLE


//            story2.setText(model.title)


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
                val intent = Intent(activity, ScrollingActivity::class.java)
//                intent.putExtra("product_name", model.title)
//                intent.putExtra("product_image", model.img)
//                intent.putExtra("product_price", model.price)
                intent.putExtra("type", "product")
                intent.putExtra("ProductItem", model)


//            intent.putExtra(EXTRA_MESSAGE, message)
                startActivityForResult(intent, 1)

            }


        }


    private fun clickBundleEventHook(): ClickEventHook<BundleProds> =
        object : ClickEventHook<BundleProds>() {
            override fun onClick(model: BundleProds, viewBinder: ViewBinder) {
//            Toast.makeText(requireContext(), "${model.title} clicked", Toast.LENGTH_SHORT).show()

                val intent = Intent(activity, ScrollingActivity::class.java)
//                intent.putExtra("product_name", model.title)
//                intent.putExtra("product_image", model.img)
//                intent.putExtra("product_price", model.price)
                intent.putExtra("BundleItem", model)
                intent.putExtra("type", "bundle")


//            intent.putExtra(EXTRA_MESSAGE, message)
                startActivityForResult(intent, 1)
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


}
