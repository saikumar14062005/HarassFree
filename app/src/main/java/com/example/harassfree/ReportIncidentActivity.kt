package com.example.harassfree

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// Data class to store incident details
data class Incident(
    var type: String = "",
    var location: String = "",
    var time: String = "",
    var date: String = "",
    var harasserDetails: String = ""
)

class ReportIncidentActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_incident)

        // Initialize Firebase database reference
        database = FirebaseDatabase.getInstance().reference.child("HarassmentIncidents")

        // Bind UI components
        val spinnerType: Spinner = findViewById(R.id.spinnerType)
        val etLocation: EditText = findViewById(R.id.etLocation)
        val etTime: EditText = findViewById(R.id.etTime)
        val etDate: EditText = findViewById(R.id.etDate)
        val etHarasserDetails: EditText = findViewById(R.id.etHarasserDetails)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)

        // Set up the Spinner with harassment types
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
            val date = etDate.text.toString().trim()
            val harasserDetails = etHarasserDetails.text.toString().trim()

            // Check if all fields are filled
            if (type.isEmpty() || location.isEmpty() || time.isEmpty() || date.isEmpty() || harasserDetails.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Save incident to Firebase
            val incidentId = database.push().key ?: return@setOnClickListener
            val incident = Incident(type, location, time, date, harasserDetails)

            database.child(incidentId).setValue(incident)
                .addOnSuccessListener {
                    Toast.makeText(this, "Report saved successfully", Toast.LENGTH_SHORT).show()

                    // Prepare email content
                    val emailSubject = "Harassment Incident Report"
                    val emailBody = """
                        Incident Type: $type
                        Location: $location
                        Time: $time
                        Date: $date
                        Harasser Details: $harasserDetails
                    """.trimIndent()

                    // Create and send email intent using ACTION_SENDTO
                    val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:") // Only email apps should handle this
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(
                            "kadavakallubabu17@gmail.com",
                            "ro200774@rguktong.ac.in",
                            "sukumarsmiley62@gmail.com"
                        ))
                        putExtra(Intent.EXTRA_SUBJECT, emailSubject)
                        putExtra(Intent.EXTRA_TEXT, emailBody)
                    }

                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send Email Using:"))
                    } catch (e: Exception) {
                        Toast.makeText(this, "No compatible email apps installed", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save report", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
