package com.example.harassfree

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize BottomNavigationView
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        // Set listener for navigation item selection
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_login -> {
                    // Navigate to LoginActivity
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_report_incident -> {
                    // Navigate to ReportIncidentActivity
                    val intent = Intent(this, ReportIncidentActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_contact -> {
                    // Navigate to ContactActivity (or handle logic here)
                    val intent = Intent(this, ContactActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}
