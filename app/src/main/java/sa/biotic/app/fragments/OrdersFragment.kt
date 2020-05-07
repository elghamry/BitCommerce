package sa.biotic.app.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentOrdersBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [OrdersFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [OrdersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrdersFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var binding: FragmentOrdersBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_orders, container, false
        )

//        var sectionsPagerAdapter = OrdersSectionsPagerAdapter(requireContext(), requireActivity().supportFragmentManager)
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> {
                        UpcomingOrdersFragment.newInstance(0)
                    }
                    else -> {
                        PastOrdersFragment.newInstance(1)
                    }
                }
            }

            override fun getItemCount(): Int {
                return 2
            }
        }

//        binding.tabs.setupWithViewPager(binding.viewPager)
//        binding.viewPager.adapter?.notifyDataSetChanged()

        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.upcoming)
                else -> getString(R.string.past)
            }
        }.attach()


        // Inflate the layout for this fragment
        return binding.root
    }


    //    override fun onPause() {
//        super.onPause()
//
//    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OrdersFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OrdersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
