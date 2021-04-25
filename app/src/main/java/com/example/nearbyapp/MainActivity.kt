package com.example.nearbyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.multidex.BuildConfig
import com.example.nearbyapp.ui.NearByPlacesFragment
import com.nearby.app.replaceFragmentToActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragmentToActivity(
            supportFragmentManager,
            NearByPlacesFragment.newInstance(), false
        )

    }
}