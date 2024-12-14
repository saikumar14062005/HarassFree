package com.example.harassfree

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

data class Incident(
    var type: String = "",
    var location: String = "",
    var time: String = "",
    var date: String = "", // Added date field
    var harasserDetails: String = ""
)

class ReportIncidentActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_incident)

        database = FirebaseDatabase.getInstance().reference.child("HarassmentIncidents")

        val spinnerType: Spinner = findViewById(R.id.spinnerType)
        val etLocation: EditText = findViewById(R.id.etLocation)
        val etTime: EditText = findViewById(R.id.etTime)
        val etDate: EditText = findViewById(R.id.etDate) // New Date EditText
        val etHarasserDetails: EditText = findViewById(R.id.etHarasserDetails)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        // Set up the Spinner
        val harassmentTypes = resources.getStringArray(R.array.harassment_types)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            harassmentTypes
        )
        spinnerType.adapter = adapter

        btnSubmit.setOnClickListener {
            val type = spinnerType.selectedItem.toString()
            val location = etLocation.text.toString().trim()
            val time = etTime.text.toString().trim()
            val date = etDate.text.toString().trim() // Get date input
            val harasserDetails = etHarasserDetails.text.toString().trim()

            if (type.isEmpty() || location.isEmpty() || time.isEmpty() || date.isEmpty() || harasserDetails.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val incidentId = database.push().key ?: return@setOnClickListener
            val incident = Incident(type, location, time, date, harasserDetails)

            database.child(incidentId).setValue(incident)
                .addOnSuccessListener {
                    Toast.makeText(this, "Report submitted successfully", Toast.LENGTH_SHORT).show()
                    btnSubmit.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.green))
                    btnSubmit.isEnabled = false
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to submit report", Toast.LENGTH_SHORT).show()
                }

//             Passing the data to MailActivity
            val intent = Intent(this, MailActivity::class.java).apply {
                putExtra("type", type)
                putExtra("location", location)
                putExtra("time", time)
                putExtra("date", date)
                putExtra("harasserDetails", harasserDetails)
            }
            startActivity(intent)
        }

    }
}
