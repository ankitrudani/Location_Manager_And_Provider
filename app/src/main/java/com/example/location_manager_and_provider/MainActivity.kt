package com.example.location_manager_and_provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    lateinit var tv :TextView
    lateinit var tv2 :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tv = findViewById<TextView>(R.id.textView)
        tv2 = findViewById<TextView>(R.id.textView2)

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)!=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION),111)
        }
        else{
            getLocation()
        }
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            getLocation()
        }
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        var lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        if(loc!=null){
            tv.setText("Longitude : "+loc.longitude +" \nLatitude : "+loc.latitude)
        }

        var ll = object : LocationListener {
            override fun onLocationChanged(p0: Location) {
                tv2.setText("Longitude : "+p0.longitude +" \nLatitude : "+p0.latitude)
            }
        }
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,100,100.2f,ll)
    }
}