package sa.biotic.app


import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.forEachIndexed
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.veyo.autorefreshnetworkconnection.CheckNetworkConnectionHelper
import com.veyo.autorefreshnetworkconnection.listener.OnNetworkConnectionChangeListener
import sa.biotic.app.databinding.ActivityMainBinding
import sa.biotic.app.retrofit_service.Repository


class MainActivity : AppCompatActivity() {
    //private  lateinit var viewModel:HomeViewModel
    private lateinit var binding: ActivityMainBinding


    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//// set an enter transition
//        getWindow().setEnterTransition( Explode())
//// set an exit transition
//        getWindow().setExitTransition( Explode())

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
            setSupportActionBar(binding.toolbar)

        } // Else, need to wait for onRestoreInstanceState

        window.setBackgroundDrawableResource(R.drawable.white_bg)


        CheckNetworkConnectionHelper
            .getInstance()
            .registerNetworkChangeListener(object : OnNetworkConnectionChangeListener {
                override fun onConnected() {
//                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    binding.checkLayout.visibility = ConstraintLayout.GONE
                }

                override fun onDisconnected() { //Do your task on Network Disconnected!
//                    Log.e(FragmentActivity.TAG, "onDisconnected")
                    binding.checkLayout.visibility = ConstraintLayout.VISIBLE

                    binding.lottieConnection.setAnimation("disconnected.json")
//        binding.lottieConnection.setColorFilter(R.color.purple)

                    binding.lottieConnection.playAnimation()
                    binding.lottieConnection.loop(true)

                }

//                override fun onNetworkConnected() { //Do your task on Network Connected!
////                    Log.e(FragmentActivity.TAG, "onConnected")
////                    Repository.getCategories()
////                    Repository.callBackResponse.observe(this@SplashActivity, Observer { callBack ->
////                            //            binding.wordText.text = newWord
////                        Log.d("callBackRes",callBack.toString())
////                        if(callBack)
////                        {
////
////                        }
////
////
////
////                        })
//
//
//
//                }

                override fun getContext(): Context {
                    return this@MainActivity
                }
            })


//        Repository.getCartDetails(GetCartDetailsModel(UserInfo.uid,UserInfo.access_token))

//        Log.d("checkerofuser",UserInfo.access_token+" "+UserInfo.uid+" "+UserInfo.signed)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()


    }

    /**
     * Called on first creation and when restoring state.
     */


    private fun setupBottomNavigationBar() {
        var bottomNavigationView: BottomNavigationView = binding.navView


        val navGraphIds =
            listOf(R.navigation.home, R.navigation.search, R.navigation.cart, R.navigation.profile)

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )


        // Whenever the selected controller changes, setup the action bar.
        controller.observe(this, Observer { navController ->
            setupActionBarWithNavController(this@MainActivity, navController)
        })
        currentNavController = controller

        if (intent.getStringExtra("root") == "search") {


            bottomNavigationView.selectedItemId = R.id.search

        }

        if (intent.getStringExtra("root") == "cart") {


            bottomNavigationView.selectedItemId = R.id.cart

        }

//            UserInfo.asLiveData(UserInfo::lang)
//            .observe(this, Observer<Int> {
//               Log.d("shared",it.toString())
//            })
////
//        val itemData = bottomNavigationView.menu?.findItem(R.id.cart)
//        val actionView = itemData?.actionView as CartCounterActionView
//        actionView.setItemData(bottomNavigationView.menu, itemData)
//        Repository.getCartLocal().observe(this, Observer { count ->
//
//            actionView.count=count.size
//            //            binding.wordText.text = newWord
//
//
//
//
//        })
        val badgeDrawable = bottomNavigationView.getOrCreateBadge(R.id.cart)
        badgeDrawable.isVisible = false
        bottomNavigationView.menu.forEachIndexed { index, item ->
            if (index == 2) {
                val badgeDrawable = bottomNavigationView.getOrCreateBadge(item.itemId)
                badgeDrawable.backgroundColor = resources.getColor(R.color.colorPrimary)
            }
        }
        //here is the badge

//        Repository.getCartLocal().observe(this, Observer { count ->
//
//            badgeDrawable.isVisible = true
//            badgeDrawable.number = count.size
//            if (count.size == 0) badgeDrawable.isVisible = false
//            //            binding.wordText.text = newWord
//
//
//        })

        Repository.getCartCount.observe(this, Observer { count ->

            badgeDrawable.isVisible = true

            badgeDrawable.number = count
            if (count == 0)
                badgeDrawable.isVisible = false
            //            binding.wordText.text = newWord


        })







    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

//    fun clickCheckout(view: View) {
//
////        CartFragment().clickCheckout(view)
//
//
//
//    }
//
//    fun continueShoppingClick(view: View) {
//        CartFragment().continueShoppingClick(view)
//    }


//        textMessage = findViewById(R.id.message)
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


//    }




}


