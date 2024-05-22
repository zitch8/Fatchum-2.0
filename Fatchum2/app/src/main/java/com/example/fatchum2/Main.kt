package com.example.fatchum2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class Main : AppCompatActivity() {
    lateinit var mainAppBarLayout: AppBarLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainAppBarLayout = findViewById(R.id.appBar)

        // ID's
        val mainSearch = findViewById<EditText>(R.id.mainSearch)
//        val btnTwo = findViewById<TextView>(R.id.textView3)
        mainSearch.setOnFocusChangeListener{ _, hasFocus ->
            if (hasFocus) {
                val intent = Intent(this@Main, SearchArea::class.java)
                startActivity(intent)
            }
//            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        // Initial fragment
        replaceFragment(Home())

        // Change fragment when bottom nav icon is clicked
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home -> {
                        showToolbar()
                        replaceFragment(Home())
                    }
                    R.id.favorite -> {
                        hideToolbar()
                        replaceFragment(Favorite())
                    }
                    R.id.profile -> {
                        hideToolbar()
                        replaceFragment(Profile())
                    }
                    else -> {}
                }
                true
            }


    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameMain, fragment)
        fragmentTransaction.commit()
    }

    // To hide the search bar
    fun hideToolbar(){
        mainAppBarLayout.visibility = View.INVISIBLE
    }

    // To show the search bar
    fun showToolbar(){
        mainAppBarLayout.visibility = View.VISIBLE
    }
}