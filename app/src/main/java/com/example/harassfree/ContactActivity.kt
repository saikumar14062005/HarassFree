package com.example.harassfree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        // Sample data
        val officials = listOf(
            Official("Dr.Bhaskar Patel", "director@rguktong.ac.in", R.drawable.director),
            Official("Mr.N.Chandra Sekhar", "ao@rguktong.ac.in", R.drawable.ao),
            Official("Mr M Rupas Kumar", "da@rguktong.ac.in", R.drawable.da),
            Official("Dr Shaik Meeravali", "fo@rguktong.ac.in", R.drawable.fo),
            Official("Dr.N.Nagaraj Naik", "hod@rguktong.ac.in", R.drawable.coordinator),
        )

        // Set up the RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = OfficialAdapter(officials)
    }
}
