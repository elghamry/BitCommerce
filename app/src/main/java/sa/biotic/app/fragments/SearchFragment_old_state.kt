package sa.biotic.app.fragments


import android.animation.Animator
import android.animation.AnimatorInflater
import android.app.ActivityOptions
import android.content.ContentValues
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import kotlinx.android.synthetic.main.activity_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import sa.biotic.app.GridLayoutManagerWrapper
import sa.biotic.app.OnDetailsActivity
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentSearchBinding
import sa.biotic.app.model.BundleProduct
import sa.biotic.app.model.OfferProduct
import sa.biotic.app.model.Product
import sa.biotic.app.model.SearchItem
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.utils.TransitionHelper
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.SearchViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [SearchFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [SearchFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment_old_state : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel: SearchViewModel
    private lateinit var product: Product
    private var param1: String? = null
    private var param2: String? = null
    lateinit var image_loader: GifImageView
    private lateinit var prodsAdapter: OneAdapter
    lateinit var binding: FragmentSearchBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var gifFromResource: GifDrawable
    var products: MutableList<Product> = mutableListOf()
    var bundles: MutableList<BundleProduct> = mutableListOf()
    var productsAsOffer: MutableList<Product> = mutableListOf()
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java).apply {
            //
        }
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

//        var root = inflater.inflate(R.layout.fragment_search, container, false)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_search, container, false
        )


        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var gifFromResource = GifDrawable(resources, R.drawable.search_loader)

        image_loader = binding.bottle

        gifFromResource.stop()
        image_loader.setImageDrawable(gifFromResource)




        Repository.bundleItem.observe(viewLifecycleOwner, Observer { bund ->


            if (!bundles.contains(bund)) {


                bundles.add(bund)

            }


        })


        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)
        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        searchView.visibility = ConstraintLayout.VISIBLE

        var cancel_tv: TextView =
            (activity as AppCompatActivity).findViewById<TextView>(R.id.cancel_tv)
        cancel_tv.visibility = TextView.GONE

        cancel_tv.setOnClickListener {
            (activity as AppCompatActivity).finish()
        }

        var search_et: EditText =
            (activity as AppCompatActivity).findViewById<EditText>(R.id.search_et)
        search_et.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {


                Log.d("search", p0.toString())


//


            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

//                image_loader.setImageResource(R.drawable.loader_test)
                gifFromResource.start()
                image_loader.setImageDrawable(gifFromResource)
                binding.bottle.visibility = GifImageView.VISIBLE
//                binding.noOfPr.visibility = TextView.INVISIBLE
                binding.prodsRecycler.visibility = RecyclerView.INVISIBLE


            }


        })


//
        search_et.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
//                doTheLoginWork()
                viewModel.getSearchItems(search_et.text.toString(), 20, 1, "en")
//                binding.bottle.visibility=GifImageView.VISIBLE
//                binding.noOfPr.visibility=TextView.VISIBLE
//                binding.prodsRecycler.visibility=RecyclerView.VISIBLE

            }
            true
        }





        if ((activity as AppCompatActivity).intent.getStringExtra("root") == "search") {
            searchView.margin(right = 12F)
            cancel_tv.visibility = TextView.VISIBLE

            (activity as AppCompatActivity).intent.putExtra("root", "notSearch")


        }

        bottomNavigationView =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)

        //keyboard
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



        binding.prodsRecycler.layoutManager =
            GridLayoutManagerWrapper(context, 2, GridLayoutManager.VERTICAL, false)


        productsAdapterCreation()

        return binding.root
    }


    private fun productsAdapterCreation() {
//       TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


        viewModel.searchItemsLive
//        Repository.searchItems
            .observe(viewLifecycleOwner, Observer { newProds ->


                prodsAdapter = OneAdapter(binding.prodsRecycler)
                    .attachItemModule(
                        productItem()
                            .addEventHook(clickProductEventHook())
                    )

//                prodsAdapter.clear()
//                prodsAdapter.update()
                prodsAdapter.setItems(newProds)

                products.clear()
                bundles.clear()

                Log.d("noIs", newProds.size.toString())

//            gifFromResource.stop()
//            image_loader.setImageDrawable(gifFromResource)

//                binding.noOfPr.text = newProds.size.toString() + " Items"
                binding.bottle.visibility = GifImageView.GONE
//                binding.noOfPr.visibility = TextView.VISIBLE
                binding.prodsRecycler.visibility = RecyclerView.VISIBLE


            })


        viewModel.prodLive.observe(viewLifecycleOwner, Observer { prod ->


            //Log.d("fffff",prod.ProductOfferPrice)
//            if(prod.ProductOfferPrice.toFloat()>1F){
//
//
//                if(!productsAsOffer.contains(prod)) {
//
//                    productsAsOffer.add(prod)
//
//                }
//
//            }
//            else{

            if (!products.contains(prod) && prod.ProductID != 0) {

                products.add(prod)
                Log.d("getdatafor", prod.ProductID.toString())

            }

//
//
//            }
//
        })
    }


    private fun clickProductEventHook(): ClickEventHook<SearchItem> =
        object : ClickEventHook<SearchItem>() {
            override fun onClick(model: SearchItem, viewBinder: ViewBinder) {
//                var product : Product  = Product(0,"","",
//                    "",0,"","",""
//                ,"","","","","",0)
//                    Repository.getProduct(model.ID)
//                    Repository.prod.observe(viewLifecycleOwner, Observer { prod ->
//
//                      product = prod

                //                    if(product.ProductName_En.isNotEmpty()&&!product.ProductName_En.isNullOrBlank())
                //                    {


                var product = products.find {
                    it.ProductID == model.ID

                }
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


                val intent = Intent(requireContext(), OnDetailsActivity::class.java)

                if (model.TypeIsProduct == "1") {

                    if (product != null) {
                        if (model.ProductOfferDicountValue.toFloat() > 0F) {


                            var productWithOffer = OfferProduct(
                                "dssd",
                                "sdds",
                                "asads",
                                "daadad"
                                ,
                                0,
                                "asdds",
                                "adadas",
                                "adsdasd",
                                "adasas",
                                "dasds",
                                product.ProductCallories,
                                product.ProductDescription_Ar,
                                product.ProductDescription_En,
                                product.ProductID,
                                product.ProductImage,
                                product.ProductName_Ar,
                                product.ProductName_En,
                                product.ProductPrice,
                                product.ProductReviews,
                                product.ProductStockQuantity
                            )

                            intent.putExtra("type", "offer")

                            intent.putExtra("OfferItem", productWithOffer)

                            startActivity(intent, transitionActivityOptions.toBundle())
                        } else {
                            intent.putExtra("type", "product")



                            intent.putExtra("ProductItem", product)
                            startActivity(intent, transitionActivityOptions.toBundle())
                        }
                    }


                } else {

                    var bundle: BundleProduct? = bundles.find {
                        it.BundleID == model.ID
                    }
                    if (bundle != null) {
                        intent.putExtra("BundleItem", bundle)
                        intent.putExtra("type", "bundle")

                        startActivity(intent, transitionActivityOptions.toBundle())
                    }


                }


//                        }


//                    })

                Log.d("countsPro", products.size.toString())
                Log.d("countsOf", productsAsOffer.size.toString())
                Log.d("countsBund", bundles.size.toString())


            }


        }


    private fun productItem(): ItemModule<SearchItem> = object : ItemModule<SearchItem>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item_for_all

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    requireContext(),
                    R.animator.category_anim
                )
            }
        }


        override fun onBind(model: SearchItem, viewBinder: ViewBinder) {
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
//
            Glide.with(requireContext())
//                .load(model.img)

                .load(model.SImage).centerCrop().into(story1)

            story2.text = model.Name_En
            story3.text = model.Price + " " + getString(R.string._sar)
            story4.text = model.Description_En
            story5.text = model.Callories.toString()
//            story6.text=model.ProductOfferPrice+ " " + getString(R.string._sar)
            story7.text = (model.ProductOfferDicountValue.toFloat() * 100).toInt().toString() + "%"





            story6.paintFlags =
                story6.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG



            if (model.ProductOfferPrice.toFloat() > 0) {
                story3.text = model.ProductOfferPrice + " " + getString(R.string._sar)
                story6.text = model.Price + " " + getString(R.string._sar)
                story6.visibility = TextView.VISIBLE
            } else {
                story9.visibility = CardView.INVISIBLE
                story6.visibility = TextView.INVISIBLE

            }

            if (model.TypeIsProduct == "1") {


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

            } else {
                story5.visibility = TextView.INVISIBLE


                if (model.ProductStockQuantity == 0) {

//                    story8.visibility=TextView.VISIBLE
                    story3.visibility = TextView.INVISIBLE

                    story5.text = "Sold out"
                    story5.visibility = TextView.VISIBLE
                    story5.setTextColor(resources.getColor(R.color.stockColor))

                } else {
                    if (model.ProductStockQuantity <= 5) {
                        Log.d("iam here bitch", "hello friend")
                        story5.text =
                            getString(R.string.only) + " " + model.ProductStockQuantity.toString() + " " + getString(
                                R.string.left
                            )
                        story5.visibility = TextView.VISIBLE
                        story5.setTextColor(resources.getColor(R.color.stockColor))

                    }
                }

            }




            if (model.TypeIsProduct == "0") {

                story9.visibility = CardView.INVISIBLE
                story6.visibility = TextView.INVISIBLE
                story10.visibility = ImageView.INVISIBLE
//
                story8.visibility = TextView.INVISIBLE


            }


//Log.d("countsA",model.TypeIsProduct)
            if (model.TypeIsProduct == "1") {
                Log.d("countsA", model.Name_En)
                viewModel.getThisProd(model.ID)
            } else {

                Repository.getBundleProduct(model.ID)

            }


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
//        listener = null
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
         * @return A new instance of fragment SearchFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment_old_state().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
