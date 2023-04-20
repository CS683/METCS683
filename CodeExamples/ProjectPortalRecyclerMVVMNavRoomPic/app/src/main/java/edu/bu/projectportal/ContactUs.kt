package edu.bu.projectportal

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions;

class ContactUs : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var phoneNum: String
    lateinit var mGoogleMap: GoogleMap

    @RequiresApi(Build.VERSION_CODES.N)
    val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION,
                false) -> {
                // Precise location access granted.
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION,
                false) -> {
                // Only approximate location access granted.
            } else -> {
            // No location access granted.
        }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        phoneNum = findViewById<TextView>(R.id.phoneNum).text.toString()

        // Get a handle to the fragment and register the callback.
        val mapFragment:SupportMapFragment =
            supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    // Get a handle to the GoogleMap object and display marker.
    override fun onMapReady(googleMap:GoogleMap) {
        mGoogleMap = googleMap
        val bucsmet = LatLng(42.351354, -71.1210252)
        googleMap.addMarker(MarkerOptions()
            .position(bucsmet)
            .title("BUMET CS Dept"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(bucsmet))
    }

    //Starting in Android 11 (API level 30), if the user taps Deny for a specific
    // permission more than once during your app's lifetime of installation on
    // a device, the user doesn't see the system permissions dialog
    // if your app requests that permission again.
    val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
                isGranted ->
            if (isGranted) {
                startActivity(Intent(Intent.ACTION_CALL,
                    Uri.parse("tel:" + phoneNum)))
            } else {
                Toast.makeText(this,
                    "we cannot make phonecall without permission granted",
                    Toast.LENGTH_LONG).show()
            }
        }


    @RequiresApi(Build.VERSION_CODES.M)
    fun callUs(v: View) {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) ==
            PackageManager.PERMISSION_GRANTED)  {
            startActivity(Intent(Intent.ACTION_CALL,
                Uri.parse("tel:" + phoneNum)))
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE)) {
                Toast.makeText(this, "we need permission to make phone call",
                    Toast.LENGTH_LONG).show()
            }
            requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)


        }
    }



}