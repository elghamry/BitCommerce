package sa.biotic.app


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import sa.biotic.app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    //private  lateinit var viewModel:HomeViewModel
    private lateinit var binding: ActivityMainBinding
//    private lateinit var oneAdapter: OneAdapter
//    private lateinit var prodsAdapter: OneAdapter
//    private lateinit var bundlesAdapter: OneAdapter
//    private  var prevRecyclerBinderView: ViewBinder?=null
//    private var currentNavController: LiveData<NavController>? = null
//
////    private lateinit var textMessage: TextView
////    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
////        when (item.itemId) {
////            R.id.navigation_home -> {
//////                textMessage.setText(R.string.title_home)
//////                return@OnNavigationItemSelectedListener true
////            }
////            R.id.navigation_dashboard -> {
//////                textMessage.setText(R.string.title_dashboard)
//////                return@OnNavigationItemSelectedListener true
////            }
//////            R.id.navigation_notifications -> {
////////                textMessage.setText(R.string.title_notifications)
////////                return@OnNavigationItemSelectedListener true
//////            }
////        }
////        false
////    }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_main)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//
//
//
//
//        val navView: BottomNavigationView = binding.navView
////            findViewById(R.id.nav_view)
//
//        val navGraphIds = listOf(R.navigation.nav_graph,R.navigation.nav_graph2)
//
//
//        // Setup the bottom navigation view with a list of navigation graphs
//        val controller = navView.setupWithNavController(
//            navGraphIds = navGraphIds,
//            fragmentManager = supportFragmentManager,
//            containerId = R.id.nav_host_container,
//            intent = intent
//        )
//
//        // Whenever the selected controller changes, setup the action bar.
//        controller.observe(this, Observer { navController ->
//            setupActionBarWithNavController(navController)
//        })
//        currentNavController = controller
//    }
//
//    override fun onSupportNavigateUp(): Boolean {
//        return currentNavController?.value?.navigateUp() ?: false
//    }


    private var currentNavController: LiveData<NavController>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//..........,

//        RxSplashScreen.Builder(this)
//            .setSplashScreen(R.layout.activity_splash, 5, TimeUnit.SECONDS)
//
//
////            .setFirstScreen(R.layout.activity_main)
////            .setConditionalNavigation(true, object : RxSplashScreenInteraction {
////                override fun navigateToSecondScreen(context: Context) {
////                    val navigateToCitizen = Intent(context, HomeActivity::class.java)
////                    startActivity(navigateToCitizen)
////                    finish()
////                }
////            })
//            .splash()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(binding.toolbar)
        if (savedInstanceState == null) {
            setupBottomNavigationBar()
            setSupportActionBar(binding.toolbar)

        } // Else, need to wait for onRestoreInstanceState

        window.setBackgroundDrawableResource(R.drawable.white_bg)
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
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }



//        textMessage = findViewById(R.id.message)
//        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)


//    }




}


