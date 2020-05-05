package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import sa.biotic.app.R
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.databinding.FragmentProdsBundsRelaBinding
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.ProductBund
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.viewmodels.ProdsBundsRelaViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ProdsBundsRelaFragment : Fragment() {


    private lateinit var prodsAdapter: OneAdapter

    private lateinit var prodsRelatedViewModel: ProdsBundsRelaViewModel
    lateinit var binding: FragmentProdsBundsRelaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prodsRelatedViewModel =
            ViewModelProviders.of(this).get(ProdsBundsRelaViewModel::class.java).apply {
            //
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
////        val root = inflater.inflate(R.layout.fragment_reviews, container, false)
////        val textView: TextView = root.findViewById(R.id.section_label)
////        reviewsViewModel.text.observe(this, Observer<String> {
////            textView.text = it
////        })
////        return root

//
//        return FragmentReviewsBinding.inflate(
//            inflater,
//            container,
//            false
//        ).apply {
//            setLifecycleOwner(this@ReviewsFragment)
//            reviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java).apply {
//
//            }    // Attach your view model here
//        }.root
        val bundle: BundleProds? = activity?.intent?.getParcelableExtra("BundleItem")
        Repository.getBundleProduct(bundle!!.BundleID)

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_prods_bunds_rela, container, false
        )
        binding.prodsRecycler.layoutManager =
            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        prodsAdapterCreation()

        return binding.root


    }

    private fun prodsAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


//            .attachItemModule(headerItem())
//            .attachItemModule(messageItem()

//                .addState(selectionState())

//                .addEventHook(swipeEventHook())
//            )
//            .attachEmptinessModule(emptinessModule())
//            .attachPagingModule(pagingModule())
//            .attachItemSelectionModule(itemSelectionModule())


        prodsAdapter = OneAdapter(binding.prodsRecycler).attachItemModule(productItem())


//
        prodsRelatedViewModel.prodsLive.observe(this, Observer { newRevs ->
            prodsAdapter.setItems(newRevs)


//            Log.d("hello",reviewsAdapter.modulesActions.toString())
//
//
//
        })


    }

    private fun clickEventHook(): ClickEventHook<ProductBund> =
        object : ClickEventHook<ProductBund>() {
            override fun onClick(model: ProductBund, viewBinder: ViewBinder) {
//            Toast.makeText(context, "${model.name} clicked", Toast.LENGTH_SHORT).show()


        }


    }


    private fun productItem(): ItemModule<ProductBund> = object : ItemModule<ProductBund>() {

        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.product_item_bundle_related

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@ProdsBundsRelaFragment.context,
                    R.animator.category_anim
                )
            }
        }


        override fun onBind(model: ProductBund, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<ImageView>(R.id.product_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.product_title)
            val story3 = viewBinder.findViewById<TextView>(R.id.product_description)
            val story4 = viewBinder.findViewById<TextView>(R.id.calories)
            val story5 = viewBinder.findViewById<TextView>(R.id.price)

//            val story3 =
//                viewBinder.findViewById<sa.biotic.app.components.RatingBar>(R.id.smart_rating_bar)

//            Log.d("hello", model.toString())


//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//////
            Glide.with(this@ProdsBundsRelaFragment)
//                .load(model.img)
//
                .load(model.ProductImage).centerCrop().into(story1)


            story2.text = model.ProductName_En
            story3.text = model.ProductDescription_En
            story4.text = model.ProductCallories.toString()
            story5.text = model.ProductPrice + " " + getString(R.string._sar)
//            story3.ratingNum = model.rate.toFloat()


//            story2.setText(model.title)


        }
    }

    companion object {
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
        fun newInstance(sectionNumber: Int): ProdsBundsRelaFragment {
            return ProdsBundsRelaFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}