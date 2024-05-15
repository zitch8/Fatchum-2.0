package com.example.fatchum2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class Main : AppCompatActivity() {
    lateinit var mainAppBarLayout: AppBarLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainAppBarLayout = findViewById(R.id.appBar)

        // Initial fragment
        replaceFragment(Home())

        // Change fragment when bottom nav icon is clicked
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            .setOnItemSelectedListener {
                when(it.itemId){
                    R.id.home -> replaceFragment(Home())
                    R.id.favorite -> replaceFragment(Favorite())
                    R.id.profile -> replaceFragment(Profile())
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
}