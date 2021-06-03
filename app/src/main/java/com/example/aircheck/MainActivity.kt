package com.example.aircheck

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import com.example.aircheck.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

import com.google.android.gms.tasks.CancellationTokenSource

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var cancellationTokenSource : CancellationTokenSource? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initVariables()
        requestLocationPermissions()


    }


    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val locationPermissionGranted =
            requestCode == REQUEST_ACCESS_LOCATION_PERMISSIONS &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED

        if(!locationPermissionGranted){
            finish()
        }else{
            //fetchData
                cancellationTokenSource = CancellationTokenSource()

            fusedLocationProviderClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY,
                cancellationTokenSource!!.token
            ).addOnSuccessListener { location ->

                binding.textView.text = "${ location.latitude }, ${location.longitude}"
            }

        }
    }

    private fun initVariables(){

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
    }

    private fun requestLocationPermissions(){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            REQUEST_ACCESS_LOCATION_PERMISSIONS
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        cancellationTokenSource?.cancel()
    }


    companion object{
        private const val REQUEST_ACCESS_LOCATION_PERMISSIONS = 100
    }
}