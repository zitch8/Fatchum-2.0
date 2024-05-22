package com.example.fatchum2

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast

class Loading : AppCompatActivity() {
    private val handler = Handler()
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var retryButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        loadingProgressBar = findViewById(R.id.loadingProgressBar)
        retryButton = findViewById(R.id.retryButton)
        loadingProgressBar.visibility = View.VISIBLE
        checkInternetConnection()

        retryButton.setOnClickListener {
            retryButton.visibility = View.GONE
            loadingProgressBar.visibility = View.VISIBLE
            checkInternetConnection()
        }

    }

    //For checking internet connection
    private fun checkInternetConnection(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        val isConnected = network != null && (capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) ?: false)
        Log.d("InternetConnection", "Is connected: $isConnected")
        if (isConnected) {
            // Loading Screen
            handler.postDelayed({
                //Go to next page after loading
                val intent = Intent(this@Loading, Login::class.java)
                startActivity(intent)
                finish();
            }, 1000)
        } else {
            // No internet connection
            loadingProgressBar.visibility = View.GONE
            Toast.makeText(this, "No Internet Connection.", Toast.LENGTH_SHORT).show()
            retryButton.visibility = View.VISIBLE
        }
    }
}