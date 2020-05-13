package sa.biotic.app.fragments

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.common.GooglePlayServicesRepairableException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import sa.biotic.app.R
import sa.biotic.app.databinding.FragmentGetMyLocationBinding
import sa.biotic.app.retrofit_service.Repository
import sa.biotic.app.viewmodels.PurchaseViewModel
import java.io.IOException
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GetMyLocationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GetMyLocationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GetMyLocationFragment : Fragment(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {

    lateinit var binding: FragmentGetMyLocationBinding
    lateinit var purchaseViewModel: PurchaseViewModel
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest
    private var locationUpdateState = false

    lateinit var markerCenter: Marker

    var addressCon = ""

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
        private const val REQUEST_CHECK_SETTINGS = 2
        private const val PLACE_PICKER_REQUEST = 3
    }
//    override fun onMapReady(p0: GoogleMap?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun onMarkerClick(p0: Marker?): Boolean {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
//    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }

        purchaseViewModel =
            ViewModelProviders.of(requireActivity()).get(PurchaseViewModel::class.java)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_get_my_location, container, false
        )


        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)

                lastLocation = p0.lastLocation
//                placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
            }
        }


        createLocationRequest()


        binding.confirmBtn.setOnClickListener {
            var stepper =
                (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
                    R.id.stepper
                )


            getAddress(markerCenter.position)
//            getAddressAR(markerCenter.position)
            stepper.goToNextStep()
            stepper.goToNextStep()


//                    naviaget from here
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_getMyLocationFragment_to_showAddressFragment)


        }

        binding.backBtn.setOnClickListener {
            var stepper =
                (activity as AppCompatActivity).findViewById<sa.biotic.app.components.stepper.StepperView>(
                    R.id.stepper
                )

            stepper.goToPreviousStep()
            stepper.goToPreviousStep()
            Navigation.findNavController(binding.root).popBackStack()

        }

        return binding.root
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == GetMyLocationFragment.REQUEST_CHECK_SETTINGS) {
//            if (resultCode == Activity.RESULT_OK) {
//                locationUpdateState = true
//                startLocationUpdates()
//            }
//        }
//        if (requestCode == GetMyLocationFragment.PLACE_PICKER_REQUEST) {
//            if (resultCode == AppCompatActivity.RESULT_OK) {
//                val place = PlacePicker.getPlace(requireContext(), data)
//                var addressText = place.name.toString()
//                addressText += "\n" + place.address.toString()
//
//                placeMarkerOnMap(place.latLng)
//            }
//        }
//    }

    override fun onPause() {
        super.onPause()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUpdateState) {
            startLocationUpdates()
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap


        map.uiSettings.isZoomControlsEnabled = true
        map.setOnMarkerClickListener(this)

        setUpMap()


//        var height = 100
//        var width = 100
//        var bitmapdraw = getResources().getDrawable(R.drawable.home_marker) as BitmapDrawable
//        var b = bitmapdraw.bitmap
//        var smallMarker = Bitmap.createScaledBitmap(b, width, height, false)

        var marker: Bitmap = BitmapFactory.decodeResource(
            requireContext().resources,
            R.drawable.pin
        )


        val markerOptions = MarkerOptions()
        markerOptions.position(map.cameraPosition.target)
        markerCenter = map.addMarker(markerOptions)
        markerCenter.setIcon(BitmapDescriptorFactory.fromBitmap(marker))
        map.setOnCameraMoveListener(GoogleMap.OnCameraMoveListener {
            markerCenter.position = map.cameraPosition.target
        })


    }

    override fun onMarkerClick(p0: Marker?) = false

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                GetMyLocationFragment.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }

        map.isMyLocationEnabled = true
        map.mapType = GoogleMap.MAP_TYPE_NORMAL

        fusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->
            // Got last known location. In some rare situations this can be null.
            if (location != null) {
                lastLocation = location
                val currentLatLng = LatLng(location.latitude, location.longitude)
//                placeMarkerOnMap(currentLatLng)
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
            }
        }
    }

    private fun placeMarkerOnMap(location: LatLng) {
        val markerOptions = MarkerOptions().position(location)

        val titleStr = getAddress(location)  // add these two lines


        markerOptions.title(titleStr)

        markerOptions.draggable(true)

//        markerOptions.position(map.getCameraPosition().target);
////        markerCenter = mMap.addMarker(markerOptions);


        map.addMarker(markerOptions)
    }


//@Override
//    private  fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//        val markerOptions = MarkerOptions()
//        markerOptions.position(mMap.getCameraPosition().target)
//        markerCenter = mMap.addMarker(markerOptions)
//        mMap.setOnCameraMoveListener(GoogleMap.OnCameraMoveListener { markerCenter.setPosition(mMap.getCameraPosition().target) })
//    }



    private fun getAddress(latLng: LatLng): String {
        // 1
        var loc = Locale("en", "US")//en US
        var aLocale = Locale.Builder().setLanguage("en").setScript("Latn").setRegion("RS").build()
        val geocoder = Geocoder(requireContext(), loc)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                Log.d("helloAdd", address.maxAddressLineIndex.toString())
//        Log.d("addresss",addresses.toString())
//        for (i in 0 until address.maxAddressLineIndex) {
//
////          addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
//
////        }
                addressCon = addresses.get(0)
                    .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                var city = addresses.get(0).locality
//                var state = addresses.get(0).adminArea
//                var country = addresses.get(0).countryName
//                var postalCode = addresses.get(0).postalCode
//                var knownName = addresses.get(0).featureName
//                var var1 = addresses.get(0).thoroughfare
//                var var2 = addresses.get(0).subThoroughfare
//                var premises = addresses.get(0).premises
//
//                addressText =
//                    address + "\n" + city + "\n" + state + "\n" + country + "\n" + postalCode + "\n" + knownName + "\n" + var1 + "\n" + var2 + "\n" + premises
//                Log.d("addresss", addressText.toString())
//
//
//                Toast.makeText(context, address, Toast.LENGTH_LONG).show()

                Repository.setAddressCheckout(addresses.get(0))


            }


        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }





        return addressText
    }


    private fun getAddressAR(latLng: LatLng): String {
        // 1
        var loc = Locale("ar", "sa")//en US
        var aLocale = Locale.Builder().setLanguage("en").setScript("Latn").setRegion("RS").build()
        val geocoder = Geocoder(requireContext(), loc)
        val addresses: List<Address>?
        val address: Address?
        var addressText = ""

        try {
            // 2
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            // 3
            if (null != addresses && !addresses.isEmpty()) {
                address = addresses[0]
                Log.d("helloAdd", address.maxAddressLineIndex.toString())
//        Log.d("addresss",addresses.toString())
//        for (i in 0 until address.maxAddressLineIndex) {
//
////          addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
//
////        }
                addressCon = addressCon + addresses.get(0)
                    .getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//                var city = addresses.get(0).locality
//                var state = addresses.get(0).adminArea
//                var country = addresses.get(0).countryName
//                var postalCode = addresses.get(0).postalCode
//                var knownName = addresses.get(0).featureName
//                var var1 = addresses.get(0).thoroughfare
//                var var2 = addresses.get(0).subThoroughfare
//                var premises = addresses.get(0).premises
//
//                addressText =
//                    address + "\n" + city + "\n" + state + "\n" + country + "\n" + postalCode + "\n" + knownName + "\n" + var1 + "\n" + var2 + "\n" + premises
//                Log.d("addresss", addressText.toString())
//
//
//                Toast.makeText(context, address, Toast.LENGTH_LONG).show()

                Repository.setAddressCheckout(addresses.get(0))

                val clipboard: ClipboardManager =
                    requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("Address is ", addressCon)
                clipboard.setPrimaryClip(clip)
            }





        } catch (e: IOException) {
            Log.e("MapsActivity", e.localizedMessage)
        }





        return addressText
    }

    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this.requireActivity(),
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                GetMyLocationFragment.LOCATION_PERMISSION_REQUEST_CODE
            )
            return
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null /* Looper */
        )
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest()
        locationRequest.interval = 10000 //
        locationRequest.fastestInterval = 5000
        locationRequest.smallestDisplacement = 100f //100 m
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        val builder = LocationSettingsRequest.Builder()
            .addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(this.requireActivity())
        val task = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener {
            locationUpdateState = true
            startLocationUpdates()
        }
        task.addOnFailureListener { e ->
            if (e is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    e.startResolutionForResult(
                        activity,
                        GetMyLocationFragment.REQUEST_CHECK_SETTINGS
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }
    }

    private fun loadPlacePicker() {
        val builder = PlacePicker.IntentBuilder()

        try {
            startActivityForResult(
                builder.build(activity),
                GetMyLocationFragment.PLACE_PICKER_REQUEST
            )
        } catch (e: GooglePlayServicesRepairableException) {
            e.printStackTrace()
        } catch (e: GooglePlayServicesNotAvailableException) {
            e.printStackTrace()
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        listener?.onFragmentInteraction(uri)
//    }
//
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

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment GetMyLocationFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            GetMyLocationFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}
