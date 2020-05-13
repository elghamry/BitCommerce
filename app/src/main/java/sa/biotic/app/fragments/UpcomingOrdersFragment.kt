package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.button.MaterialButton
import com.idanatz.oneadapter.OneAdapter
import com.idanatz.oneadapter.external.event_hooks.ClickEventHook
import com.idanatz.oneadapter.external.interfaces.Item
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import kotlinx.android.synthetic.main.activity_main.*
import sa.biotic.app.R
import sa.biotic.app.components.LinearLayoutManagerWrapper
import sa.biotic.app.databinding.FragmentOrdersForTabBinding
import sa.biotic.app.model.OrderAfterConfModel
import sa.biotic.app.model.OrderAfterConfResponse
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.shared_prefrences_model.UserInfo
import sa.biotic.app.utils.margin
import sa.biotic.app.viewmodels.OrdersViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class UpcomingOrdersFragment : Fragment() {


    private lateinit var ordersAdapter: OneAdapter

    private lateinit var viewModel: OrdersViewModel
    lateinit var binding: FragmentOrdersForTabBinding
    lateinit var bottomNavigationView: BottomNavigationView



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



        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_orders_for_tab, container, false
        )

        bottomNavigationView =
            (activity as AppCompatActivity).findViewById<BottomNavigationView>(sa.biotic.app.R.id.nav_view)
        binding.ordsRecycler.layoutManager =
            LinearLayoutManagerWrapper(this.requireContext(), RecyclerView.VERTICAL, false)

        var toolbar: Toolbar = ((activity as AppCompatActivity).toolbar)
        toolbar.visibility = Toolbar.VISIBLE
        var container: FragmentContainerView =
            (activity as AppCompatActivity).findViewById<FragmentContainerView>(R.id.nav_host_container)
        container.margin(top = 40F)

        var searchView: ConstraintLayout =
            (activity as AppCompatActivity).findViewById<ConstraintLayout>(R.id.search_widget)
        if (searchView.isVisible)
            searchView.visibility = ConstraintLayout.GONE


        Repository.getUpcomingUserOrders(
            OrderAfterConfModel(
                UserInfo.access_token,
                UserInfo.uid,
                1,
                UserInfo.device_token
            )
        )

        ordersAdapterCreation()


        binding.viewOffersBtn.setOnClickListener {


            bottomNavigationView.selectedItemId = R.id.home
        }



        return binding.root


    }

    private fun ordersAdapterCreation() {


        ordersAdapter = OneAdapter(binding.ordsRecycler).attachItemModule(
            orderItem()
                .addEventHook(clickEventHook())
        )


        Repository.upcomingOrders.observe(viewLifecycleOwner, Observer { newOrds ->

            if (newOrds.size == 0 || newOrds.isNullOrEmpty()) {


                binding.viewOffersContainer.visibility = View.VISIBLE

                binding.ordsRecycler.visibility = View.GONE
            } else {
                ordersAdapter.setItems(newOrds)

                binding.viewOffersContainer.visibility = View.GONE

                binding.ordsRecycler.visibility = View.VISIBLE

            }


        })


    }

    private fun clickEventHook(): ClickEventHook<OrderAfterConfResponse> =
        object : ClickEventHook<OrderAfterConfResponse>() {
            override fun onClick(model: OrderAfterConfResponse, viewBinder: ViewBinder) {
//            Toast.makeText(context, "${model.name} clicked", Toast.LENGTH_SHORT).show()

                var bundle = bundleOf("order_no" to model.OrderNumber)
                bundle.putString("type", "upcoming")


                Navigation.findNavController(binding.root)
                    .navigate(R.id.orderInformationFragment, bundle)
        }


    }


    private fun orderItem(): ItemModule<OrderAfterConfResponse> =
        object : ItemModule<OrderAfterConfResponse>() {
        override fun provideModuleConfig(): ItemModuleConfig = object : ItemModuleConfig() {
            override fun withLayoutResource(): Int = R.layout.order_item

            override fun withFirstBindAnimation(): Animator {
                // can be implemented by inflating Animator Xml
                return AnimatorInflater.loadAnimator(
                    this@UpcomingOrdersFragment.context,
                    R.animator.category_anim
                )
            }
        }


            override fun onBind(item: Item<OrderAfterConfResponse>, viewBinder: ViewBinder) {

                val order_no = viewBinder.findViewById<TextView>(R.id.order_no_value)
                val order_date = viewBinder.findViewById<TextView>(R.id.order_date_value)
                val price = viewBinder.findViewById<TextView>(R.id.totalTv)
                val details = viewBinder.findViewById<MaterialButton>(R.id.track_btn)

                var model = item.model

                details.setOnClickListener {
                    var bundle = bundleOf("order_no" to model.OrderNumber)
                    bundle.putString("type", "upcoming")


                    Navigation.findNavController(binding.root)
                        .navigate(R.id.orderInformationFragment, bundle)
                }


            val story3 = viewBinder.findViewById<View>(R.id.line3)
                val story4 = viewBinder.findViewById<MaterialButton>(R.id.track_btn)


                order_no.text = model.OrderNumber.toString()
                order_date.text = model.OrderDate
                price.text = "%.2f".format(model.TotalPrice) + " " + getString(R.string._sar)


//            story4.visibility = MaterialButton.GONE






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
        fun newInstance(sectionNumber: Int): UpcomingOrdersFragment {
            return UpcomingOrdersFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_SECTION_NUMBER, sectionNumber)
                }
            }
        }
    }
}