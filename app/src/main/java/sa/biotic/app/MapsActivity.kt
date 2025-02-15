///*
// * Copyright (c) 2018 Razeware LLC
// *
// * Permission is hereby granted, free of charge, to any person obtaining a copy
// * of this software and associated documentation files (the "Software"), to deal
// * in the Software without restriction, including without limitation the rights
// * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// * copies of the Software, and to permit persons to whom the Software is
// * furnished to do so, subject to the following conditions:
// *
// * The above copyright notice and this permission notice shall be included in
// * all copies or substantial portions of the Software.
// *
// * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
// * distribute, sublicense, create a derivative work, and/or sell copies of the
// * Software in any work that is designed, intended, or marketed for pedagogical or
// * instructional purposes related to programming, coding, application development,
// * or information technology.  Permission for such use, copying, modification,
// * merger, publication, distribution, sublicensing, creation of derivative works,
// * or sale is expressly withheld.
// *
// * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// * THE SOFTWARE.
// */
//
//package sa.biotic.app
//
//import android.app.Activity
//import android.content.Intent
//import android.content.IntentSender
//import android.content.pm.PackageManager
//import android.location.Address
//import android.location.Geocoder
//import android.location.Location
//import android.os.Bundle
//import android.support.design.widget.FloatingActionButton
//import android.support.v4.app.ActivityCompat
//import android.support.v7.app.AppCompatActivity
//import android.util.Log
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.gms.common.GooglePlayServicesNotAvailableException
//import com.google.android.gms.common.GooglePlayServicesRepairableException
//import com.google.android.gms.common.api.ResolvableApiException
//import com.google.android.gms.location.*
//import com.google.android.gms.location.places.ui.PlacePicker
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.Marker
//import com.google.android.gms.maps.model.MarkerOptions
//import java.io.IOException
//
//
//class MapsActivity : AppCompatActivity(), OnMapReadyCallback,
//    GoogleMap.OnMarkerClickListener {
//
//  private lateinit var map: GoogleMap
//  private lateinit var fusedLocationClient: FusedLocationProviderClient
//  private lateinit var lastLocation: Location
//
//  private lateinit var locationCallback: LocationCallback
//  private lateinit var locationRequest: LocationRequest
//  private var locationUpdateState = false
//
//  companion object {
//    private const val LOCATION_PERMISSION_REQUEST_CODE = 1
//    private const val REQUEST_CHECK_SETTINGS = 2
//    private const val PLACE_PICKER_REQUEST = 3
//  }
//
//  override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//    setContentView(R.layout.activity_maps)
//    // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//    val mapFragment = supportFragmentManager
//        .findFragmentById(R.id.map) as SupportMapFragment
//    mapFragment.getMapAsync(this)
//
//    fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
//
//    locationCallback = object : LocationCallback() {
//      override fun onLocationResult(p0: LocationResult) {
//        super.onLocationResult(p0)
//
//        lastLocation = p0.lastLocation
//        placeMarkerOnMap(LatLng(lastLocation.latitude, lastLocation.longitude))
//      }
//    }
//
//    createLocationRequest()
//
//    val fab = findViewById<FloatingActionButton>(R.id.fab)
//    fab.setOnClickListener {
//      loadPlacePicker()
//    }
//  }
//
//  override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
//    super.onActivityResult(requestCode, resultCode, data)
//    if (requestCode == REQUEST_CHECK_SETTINGS) {
//      if (resultCode == Activity.RESULT_OK) {
//        locationUpdateState = true
//        startLocationUpdates()
//      }
//    }
//    if (requestCode == PLACE_PICKER_REQUEST) {
//      if (resultCode == RESULT_OK) {
//        val place = PlacePicker.getPlace(this, data)
//        var addressText = place.name.toString()
//        addressText += "\n" + place.address.toString()
//
//        placeMarkerOnMap(place.latLng)
//      }
//    }
//  }
//
//    override fun onPause() {
//    super.onPause()
//    fusedLocationClient.removeLocationUpdates(locationCallback)
//  }
//
//  public override fun onResume() {
//    super.onResume()
//    if (!locationUpdateState) {
//      startLocationUpdates()
//    }
//  }
//
//  /**
//   * Manipulates the map once available.
//   * This callback is triggered when the map is ready to be used.
//   * This is where we can add markers or lines, add listeners or move the camera. In this case,
//   * we just add a marker near Sydney, Australia.
//   * If Google Play services is not installed on the device, the user will be prompted to install
//   * it inside the SupportMapFragment. This method will only be triggered once the user has
//   * installed Google Play services and returned to the app.
//   */
//  override fun onMapReady(googleMap: GoogleMap) {
//    map = googleMap
//
//    map.uiSettings.isZoomControlsEnabled = true
//    map.setOnMarkerClickListener(this)
//
//    setUpMap()
//  }
//
//  override fun onMarkerClick(p0: Marker?) = false
//
//  private fun setUpMap() {
//    if (ActivityCompat.checkSelfPermission(this,
//        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//      ActivityCompat.requestPermissions(this,
//          arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
//      return
//    }
//
//    map.isMyLocationEnabled = true
//    map.mapType = GoogleMap.MAP_TYPE_NORMAL
//
//    fusedLocationClient.lastLocation.addOnSuccessListener(this) { location ->
//      // Got last known location. In some rare situations this can be null.
//      if (location != null) {
//        lastLocation = location
//        val currentLatLng = LatLng(location.latitude, location.longitude)
//        placeMarkerOnMap(currentLatLng)
//        map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
//      }
//    }
//  }
//
//  private fun placeMarkerOnMap(location: LatLng) {
//    val markerOptions = MarkerOptions().position(location)
//
//    val titleStr = getAddress(location)  // add these two lines
//
//
//    markerOptions.title(titleStr)
//
//    map.addMarker(markerOptions)
//  }
//
//  private fun getAddress(latLng: LatLng): String {
//    // 1
//    val geocoder = Geocoder(this)
//    val addresses: List<Address>?
//    val address: Address?
//    var addressText = ""
//
//    try {
//      // 2
//      addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
//
//      // 3
//      if (null != addresses && !addresses.isEmpty()) {
//        address = addresses[0]
//        Log.d("helloAdd",address.maxAddressLineIndex.toString())
////        Log.d("addresss",addresses.toString())
////        for (i in 0 until address.maxAddressLineIndex) {
////
//////          addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(i)
////
////        }
//        var address = addresses.get(0).getAddressLine(0) // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
//  var city  = addresses.get(0).getLocality()
//var state = addresses.get(0).getAdminArea()
//var country = addresses.get(0).getCountryName()
//var postalCode = addresses.get(0).getPostalCode()
//var knownName = addresses.get(0).getFeatureName()
//
//        addressText=address+"\n"+city+"\n"+state+"\n"+country+postalCode+knownName
//        Log.d("addresss",addressText.toString())
//      }
//
//
//
//    } catch (e: IOException) {
//      Log.e("MapsActivity", e.localizedMessage)
//    }
//
//
//
//
//
//    return addressText
//  }
//
//  private fun startLocationUpdates() {
//    if (ActivityCompat.checkSelfPermission(this,
//        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//      ActivityCompat.requestPermissions(this,
//          arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//          LOCATION_PERMISSION_REQUEST_CODE)
//      return
//    }
//    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null /* Looper */)
//  }
//
//  private fun createLocationRequest() {
//    locationRequest = LocationRequest()
//    locationRequest.interval = 1 * 60 * 1000
//    locationRequest.fastestInterval = 50000
//    locationRequest.setSmallestDisplacement(100f) //100 m
//    locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//
//    val builder = LocationSettingsRequest.Builder()
//        .addLocationRequest(locationRequest)
//    val client = LocationServices.getSettingsClient(this)
//    val task = client.checkLocationSettings(builder.build())
//
//    task.addOnSuccessListener {
//      locationUpdateState = true
//      startLocationUpdates()
//    }
//    task.addOnFailureListener { e ->
//      if (e is ResolvableApiException) {
//        // Location settings are not satisfied, but this can be fixed
//        // by showing the user a dialog.
//        try {
//          // Show the dialog by calling startResolutionForResult(),
//          // and check the result in onActivityResult().
//          e.startResolutionForResult(this@MapsActivity,
//              REQUEST_CHECK_SETTINGS)
//        } catch (sendEx: IntentSender.SendIntentException) {
//          // Ignore the error.
//        }
//      }
//    }
//  }
//
//  private fun loadPlacePicker() {
//    val builder = PlacePicker.IntentBuilder()
//
//    try {
//      startActivityForResult(builder.build(this@MapsActivity), PLACE_PICKER_REQUEST)
//    } catch (e: GooglePlayServicesRepairableException) {
//      e.printStackTrace()
//    } catch (e: GooglePlayServicesNotAvailableException) {
//      e.printStackTrace()
//    }
//  }
//}
