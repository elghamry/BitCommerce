package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook

import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import com.rishabhharit.roundedimageview.RoundedImageView
import sa.biotic.app.R
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.databinding.FragmentReviewsBinding
import sa.biotic.app.model.Review
import sa.biotic.app.viewmodels.ReviewsViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ReviewsFragment : Fragment() {


    private lateinit var reviewsAdapter: OneAdapter

    private lateinit var reviewsViewModel: ReviewsViewModel
    lateinit var binding: FragmentReviewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reviewsViewModel = ViewModelProviders.of(this).get(ReviewsViewModel::class.java).apply {
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
            inflater, R.layout.fragment_reviews, container, false
        )
        binding.revsRecycler.layoutManager =
            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        reviewsAdapterCreation()

        return binding.root


    }

    private fun reviewsAdapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.


//            .attachItemModule(headerItem())
//            .attachItemModule(messageItem()

//                .addState(selectionState())

//                .addEventHook(swipeEventHook())
//            )
//            .attachEmptinessModule(emptinessModule())
//            .attachPagingModule(pagingModule())
//            .attachItemSelectionModule(itemSelectionModule())


        reviewsAdapter = OneAdapter(binding.revsRecycler).attachItemModule(reviewItem())


//
        reviewsViewModel.revsLive.observe(viewLifecycleOwner, Observer { newRevs ->
            reviewsAdapter.setItems(newRevs)


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


    private fun reviewItem(): ItemModule<Review> = object : ItemModule<Review>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.review_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@ReviewsFragment.context,
                    R.animator.category_anim
                )
            }
        }


        override fun onBind(model: Review, viewBinder: ViewBinder) {
            val story1 = viewBinder.findViewById<RoundedImageView>(R.id.review_image)
            val story2 = viewBinder.findViewById<TextView>(R.id.review_name)
            val story3 =
                viewBinder.findViewById<sa.biotic.app.components.RatingBar>(R.id.smart_rating_bar)

            Log.d("hello", model.toString())


//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

////
            Glide.with(this@ReviewsFragment)
//                .load(model.img)
//
                .load(model.img).centerCrop().into(story1)


            story2.text = model.name
            story3.ratingNum = model.rate.toFloat()


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
        fun newInstance(sectionNumber: Int): ReviewsFragment {
            return ReviewsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}