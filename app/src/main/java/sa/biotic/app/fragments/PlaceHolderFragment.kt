package sa.biotic.app.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import sa.biotic.app.R
import sa.biotic.app.model.BundleProds
import sa.biotic.app.model.Offer
import sa.biotic.app.model.Product
import sa.biotic.app.viewmodels.AboutProductViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class PlaceholderFragment : Fragment() {

    private lateinit var aboutProductViewModel: AboutProductViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        aboutProductViewModel =
            ViewModelProviders.of(this).get(AboutProductViewModel::class.java).apply {
                setIndex(arguments?.getInt(ARG_SECTION_NUMBER) ?: 1)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_main, container, false)
        val textView: TextView = root.findViewById(R.id.section_label)
        val description: TextView = root.findViewById(R.id.product_description)
        aboutProductViewModel.text.observe(this, Observer<String> {
            textView.text = it
        })


        if (activity?.intent?.getStringExtra("type") == "bundle") {
            val bundle: BundleProds? = activity?.intent?.getParcelableExtra("BundleItem")



            description.text = bundle?.BundleDescription_En


        } else {
            if (activity?.intent?.getStringExtra("type") == "product") {
                val product: Product? = activity?.intent?.getParcelableExtra("ProductItem")

                description.text = product?.ProductDescription_En

            } else {
                val offer: Offer? = activity?.intent?.getParcelableExtra("OfferItem")

                description.text = offer?.ProductDescreption_En


            }


        }


        return root
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
        fun newInstance(sectionNumber: Int): PlaceholderFragment {
            return PlaceholderFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}