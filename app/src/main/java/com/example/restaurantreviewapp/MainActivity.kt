package com.example.restaurantreviewapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantreviewapp.fragments.*
import kotlinx.android.synthetic.main.main_view.*

class MainActivity: AppCompatActivity() {

    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view)


        val reviewsFragment = ReviewsFragment()
        val searchFragment = SearchFragment()
        val rateRestaurantFragment = RateRestaurantFragment()
        val profileFragment = ProfileFragment()
        val notificationFragment = NotificationFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, reviewsFragment)
            commit()
        }

        bottom_navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_review -> {
                    setCurrentFragment(reviewsFragment)
                    Log.i(TAG, "Review selected")
                }
                R.id.nav_search -> {
                    setCurrentFragment(searchFragment)
                    Log.i(TAG, "Search selected")
                }
                R.id.nav_edit -> {
                    //setCurrentFragment(rateRestaurantFragment)
                    Log.i(TAG, "Edit selected")
                    startActivity(Intent(this, WriteReviewActivity:: class.java))
//                    setContentView(R.layout.write_review)
                }
                R.id.nav_profile -> {
                    setCurrentFragment(profileFragment)
                    Log.i(TAG, "Profile selected")
                }
                R.id.nav_notification -> {
                    setCurrentFragment(notificationFragment)
                    Log.i(TAG, "Notification selected")
                }
            }
            true
        }


    }



    private fun setCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.fl_wrapper, fragment)
        commit()
    }

}