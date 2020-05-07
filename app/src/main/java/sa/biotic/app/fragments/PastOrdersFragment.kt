package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import sa.biotic.app.R
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.databinding.FragmentOrdersForTabBinding
import sa.biotic.app.model.Order
import sa.biotic.app.model.Review
import sa.biotic.app.viewmodels.OrdersViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PastOrdersFragment : Fragment() {


    private lateinit var ordersAdapter: OneAdapter

    private lateinit var viewModel: OrdersViewModel
    lateinit var binding: FragmentOrdersForTabBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(OrdersViewModel::class.java).apply {
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

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_orders_for_tab, container, true
        )
        binding.ordsRecycler.layoutManager =
            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        ordersAdapterCreation()

        return binding.root


    }

    private fun ordersAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


//            .attachItemModule(headerItem())
//            .attachItemModule(messageItem()

//                .addState(selectionState())

//                .addEventHook(swipeEventHook())
//            )
//            .attachEmptinessModule(emptinessModule())
//            .attachPagingModule(pagingModule())
//            .attachItemSelectionModule(itemSelectionModule())


        ordersAdapter = OneAdapter(binding.ordsRecycler).attachItemModule(orderItem())


//
        viewModel.ordsLive.observe(viewLifecycleOwner, Observer { newOrds ->


            ordersAdapter.setItems(newOrds)


//            Log.d("hello",reviewsAdapter.modulesActions.toString())
//
//
//
        })


    }

    private fun clickEventHook(): ClickEventHook<Review> = object : ClickEventHook<Review>() {
        override fun onClick(model: Review, viewBinder: ViewBinder) {
            Toast.makeText(context, "${model.name} clicked", Toast.LENGTH_SHORT).show()


        }


    }


    private fun orderItem(): ItemModule<Order> = object : ItemModule<Order>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.order_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@PastOrdersFragment.context,
                    R.animator.category_anim
                )
            }
        }


        override fun onBind(model: Order, viewBinder: ViewBinder) {
//            val story1 = viewBinder.findViewById<RoundedImageView>(R.id.review_image)
//            val story2 = viewBinder.findViewById<TextView>(R.id.review_name)
            val story3 = viewBinder.findViewById<View>(R.id.line3)
            val story4 = viewBinder.findViewById<MaterialButton>(R.id.track_btn)

            story4.visibility = MaterialButton.GONE

//            viewModel.ordsLive.observe(viewLifecycleOwner, Observer { newOrds ->
//
//
//               if(newOrds.get(newOrds.size-1)==model)
//               {
//                   story3.visibility=View.VISIBLE
//               }
//
//
////            Log.d("hello",reviewsAdapter.modulesActions.toString())
////
////
////
//            })


            Log.d("hello", model.toString())
//
//
////            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)
//
//////
//            Glide.with(this@UpcomingOrdersFragment)
////                .load(model.img)
////
//                .load(model.img).centerCrop().into(story1)
//
//
//            story2.text = model.name
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
        fun newInstance(sectionNumber: Int): PastOrdersFragment {
            return PastOrdersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}