package sa.biotic.app.fragments

import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.ContentValues
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
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
import com.idanatz.oneadapter.external.events.ClickEventHook
import com.idanatz.oneadapter.external.modules.ItemModule
import com.idanatz.oneadapter.external.modules.ItemModuleConfig
import com.idanatz.oneadapter.internal.holders.ViewBinder
import kotlinx.android.synthetic.main.activity_main.*
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView
import sa.biotic.app.GridLayoutManagerWrapper
import sa.biotic.app.R
import sa.biotic.app.ScrollingActivity
import sa.biotic.app.databinding.FragmentSearchBinding
import sa.biotic.app.model.SearchItem
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
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var viewModel: SearchViewModel
    private var param1: String? = null
    private var param2: String? = null
    lateinit var image_loader: GifImageView
    private lateinit var prodsAdapter: OneAdapter
    lateinit var binding: FragmentSearchBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var gifFromResource: GifDrawable
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
                binding.noOfPr.visibility = TextView.INVISIBLE
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
            .observe(this, Observer { newProds ->
                prodsAdapter = OneAdapter(binding.prodsRecycler)
                    .attachItemModule(
                        productItem()
//                    .addEventHook(clickProductEventHook())
                    )

//                prodsAdapter.clear()
//                prodsAdapter.update()
                prodsAdapter.setItems(newProds)

                Log.d("noIs", newProds.size.toString())

//            gifFromResource.stop()
//            image_loader.setImageDrawable(gifFromResource)

                binding.noOfPr.text = newProds.size.toString() + " Items"
                binding.bottle.visibility = GifImageView.GONE
                binding.noOfPr.visibility = TextView.VISIBLE
                binding.prodsRecycler.visibility = RecyclerView.VISIBLE


            })
    }


    private fun clickProductEventHook(): ClickEventHook<SearchItem> =
        object : ClickEventHook<SearchItem>() {
            override fun onClick(model: SearchItem, viewBinder: ViewBinder) {
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
                val intent = Intent(requireContext(), ScrollingActivity::class.java)
//                intent.putExtra("product_name", model.title)
//                intent.putExtra("product_image", model.img)
//                intent.putExtra("product_price", model.price)
                intent.putExtra("type", "search")
                intent.putExtra("SearchItem", model)


//            intent.putExtra(EXTRA_MESSAGE, message)
                startActivityForResult(intent, 1)

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
//            val story7 = viewBinder.findViewById<CardView>(R.id.cal_card)
            val story8 = viewBinder.findViewById<TextView>(R.id.oldprice)

//            val story2 = viewBinder.findViewById<TextView>(R.id.category_text)

//
            Glide.with(requireContext())
//                .load(model.img)

                .load(model.SImage).centerCrop().into(story1)

            story2.text = model.Name_En
            story3.text = model.Price + " " + getString(R.string._sar)
            story4.text = model.Description_En
            story5.text = model.Callories.toString()


            story6.paintFlags =
                story6.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG



            if (model.ProductOfferPrice.toFloat() > 0) {
                story3.text = model.ProductOfferPrice + " " + getString(R.string._sar)
                story6.text = model.Price + " " + getString(R.string._sar)
                story6.visibility = TextView.VISIBLE
            }

            if (model.TypeIsProduct == "0") {

//                story7.visibility= CardView.INVISIBLE
                story8.visibility = TextView.INVISIBLE


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
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
