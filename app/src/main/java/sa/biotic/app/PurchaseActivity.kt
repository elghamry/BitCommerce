package sa.biotic.app

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import kotlinx.android.synthetic.main.activity_purchase.*
import sa.biotic.app.databinding.ActivityPurchaseBinding
import sa.biotic.app.viewmodels.SearchViewModel


class PurchaseActivity : AppCompatActivity() {


    lateinit var binding: ActivityPurchaseBinding
    private lateinit var viewModel: SearchViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_search)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_purchase)


        setSupportActionBar(toolbar)


//        var languageToLoad = "zh_CN"
//        var locale =  Locale(languageToLoad)
//        Locale.setDefault(locale)
//        var config = Configuration()
//        config.locale = locale
//        this?.getBaseContext()?.getResources()?.updateConfiguration(config,
//            this.getResources().getDisplayMetrics())


//        button_test1.setOnClickListener {
//            stepper.goToPreviousStep()
//
//        }
//
//        button_test2.setOnClickListener {
//            stepper.goToNextStep()
//        }


        window.setBackgroundDrawableResource(R.drawable.purple_bg)


        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                PurchaseActivity.LOCATION_PERMISSION_REQUEST_CODE
            )

        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == android.R.id.home) {
            finish()
        }
        return true
    }


    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
    }


}