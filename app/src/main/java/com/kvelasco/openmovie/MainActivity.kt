package com.kvelasco.openmovie

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kvelasco.openmovie.trending.TrendingMoviesFragment

class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val tag = "page:${item.itemId}"
        val manager = supportFragmentManager
        val fragment: Fragment = manager.findFragmentByTag(tag) ?: when (item.itemId) {
            R.id.navigation_home -> {
                TrendingMoviesFragment.newInstance()
            }
            R.id.navigation_dashboard -> {
                TrendingMoviesFragment.newInstance()
            }
            R.id.navigation_notifications -> {
                TrendingMoviesFragment.newInstance()
            }
            else -> {
                throw IllegalArgumentException("Unknown itemId: ${item.itemId}")
            }
        }

        manager.executePendingTransactions()
        val current: Fragment? = manager.findFragmentById(R.id.content)
        if (current != fragment) {
            val tx = manager.beginTransaction()
            if (current != null) {
                tx.detach(current)
            }
            if (fragment.isDetached){
                tx.attach(fragment)
            } else {
                tx.replace(R.id.content, fragment!!, tag)
            }
            tx.commitAllowingStateLoss()
            return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navView.selectedItemId = R.id.navigation_home
    }
}
