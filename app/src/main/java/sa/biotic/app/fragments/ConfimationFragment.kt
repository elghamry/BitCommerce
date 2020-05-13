package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.interfaces.Item
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import sa.biotic.app.R
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.databinding.FragmentConfimationBinding
import sa.biotic.app.model.CheckoutResponse
import sa.biotic.app.model.OrderItem
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.shared_prefrences_model.UserRoute
import sa.biotic.app.viewmodels.ConfirmationViewModel

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [ConfimationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [ConfimationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ConfimationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentConfimationBinding
    lateinit var viewModel: ConfirmationViewModel
    private lateinit var ordersAdapter: OneAdapter
    lateinit var image_loader: GifImageView
//    lateinit var loader_container: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProviders.of(requireActivity()).get(ConfirmationViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_confimation, container, false


        )
        binding.bottle.visibility = GifImageView.VISIBLE
        binding.loaderContainer.visibility = CardView.VISIBLE
        binding.confirmContainer.visibility = View.GONE
        binding.errorContainer.visibility = View.GONE


        var gifFromResource = GifDrawable(resources, R.drawable.search_loader)

        image_loader = binding.bottle

        gifFromResource.start()
        image_loader.setImageDrawable(gifFromResource)







        binding.yourOrderContainer.layoutManager = LinearLayoutManagerWrapper(
            requireContext(),
            RecyclerView.VERTICAL, false
        )

        binding.confirmBtn.setOnClickListener {
            UserRoute.next_step = "home"
            activity?.finish()
        }

        binding.errorBtn.setOnClickListener {
            UserRoute.next_step = "home"
            activity?.finish()
        }


        var stepper =
            (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
                R.id.stepper
            )

        stepper.visibility = View.GONE

        adapterCreation()
        return binding.root
    }

    private fun adapterCreation() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        ordersAdapter = OneAdapter(binding.yourOrderContainer)
            .attachItemModule(
                orderItem()
//                    .addEventHook(clickEventHook())
            )




        Repository.progressbarObservable.observe(viewLifecycleOwner, Observer<Boolean> {

            if (it) {
                binding.loaderContainer.visibility = CardView.VISIBLE
                binding.confirmContainer.visibility = View.GONE
                binding.errorContainer.visibility = View.GONE


            } else {

                binding.bottle.visibility = GifImageView.GONE
                binding.loaderContainer.visibility = CardView.GONE

            }
        })



        viewModel.checkoutResponse.observe(viewLifecycleOwner, Observer<CheckoutResponse> {

            binding.confirmContainer.visibility = View.GONE
            binding.errorContainer.visibility = View.GONE
            binding.bottle.visibility = GifImageView.VISIBLE
            binding.loaderContainer.visibility = CardView.VISIBLE

            Log.d("checkedCon", it.toString())

            if (it.City != "7amada") {

                if (!it.OrderItems.isNullOrEmpty() && it.OrderItems.size > 0 && it.Status) {
                    UserInfo.promo = ""
                    binding.confirmContainer.visibility = View.VISIBLE
                    binding.errorContainer.visibility = View.GONE
                    binding.bottle.visibility = GifImageView.GONE
                    binding.loaderContainer.visibility = CardView.GONE

                    ordersAdapter.setItems(it.OrderItems)

                    binding.orderNoValue.text = it.OrderNumber.toString()
                    binding.orderDateValue.text = it.OrderDate
//            binding.shippingDateValue.text=it.ShippingDate
                    binding.addressDetails.text = it.CustomerName + "\n" +
                            it.Country + "\n" +

                            it.AddressLine1.removeSuffix(", " + it.Country) + "\n" +
                            it.CustomerPhone

                    binding.paymentTotalValue.text =
                        it.TotalPrice.toString() + " " + getString(R.string._sar)


                    it.City = "7amada"


//                it.Status = false
//                it.OrderItems.clear()
                } else {


                    binding.confirmContainer.visibility = View.GONE
                    binding.errorContainer.visibility = View.VISIBLE
                    binding.bottle.visibility = GifImageView.GONE
                    binding.loaderContainer.visibility = CardView.GONE


                    val lottieDone = binding.lottieDone


                    lottieDone.setAnimation("error.json")
//        binding.lottieConnection.setColorFilter(R.color.purple)

                    lottieDone.playAnimation()
                    lottieDone.loop(true)
//                it.Status = true
//                it.OrderItems.clear()
                    it.City = "7amada"

                }

//                Repository.checkoutResponse.postValue(null)

            }


        })


//        viewModel.checkoutResponse.observe(viewLifecycleOwner, Observer { newProds ->
//
////            if (newProds.size == 0) {
////                Repository.getHomeProducts(1, 6)
////            }
////            prodsAdapter = OneAdapter(binding.productsRecycler)
////                .attachItemModule(
////                    productItem()
////                        .addEventHook(clickProductEventHook())
////                )
////            prodsAdapter.setItems(newProds)
////
////            swipeRefresh.setRefreshing(false)
//
//
//        })


    }


    private fun orderItem(): ItemModule<OrderItem> = object : ItemModule<OrderItem>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.confirmation_order

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(requireContext(), R.animator.category_anim)
            }
        }


        override fun onBind(item: Item<OrderItem>, viewBinder: ViewBinder) {


            val quantity = viewBinder.findViewById<TextView>(R.id.product_no)
            val title = viewBinder.findViewById<TextView>(R.id.product_title)
            val price = viewBinder.findViewById<TextView>(R.id.price)
            val descr = viewBinder.findViewById<TextView>(R.id.product_description)

            quantity.text = "X" + item.model.Quantity.toString()
            title.text = item.model.Name_En
            price.text = item.model.Price.toString() + " " + getString(R.string._sar)
            if (!item.model.ProductsNamesEn.isNullOrBlank()) {
                descr.text = item.model.ProductsNamesEn
            } else {
                descr.text = item.model.Description_En
            }


        }
    }



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ConfimationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ConfimationFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
