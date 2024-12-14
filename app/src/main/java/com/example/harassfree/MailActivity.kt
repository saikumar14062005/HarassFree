package com.example.harassfree

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MailActivity : AppCompatActivity() {

    private lateinit var etEmail: EditText
    private lateinit var btnSubmit: Button
    private lateinit var incidentType: String
    private lateinit var incidentLocation: String
    private lateinit var incidentTime: String
    private lateinit var incidentDate: String
    private lateinit var incidentHarasserDetails: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mail)

        // Initialize views
        etEmail = findViewById(R.id.etLocation)  // EditText for recipient email
        btnSubmit = findViewById(R.id.btnSubmit)

        // Retrieve the incident data from the intent
        incidentType = intent.getStringExtra("type") ?: "N/A"
        incidentLocation = intent.getStringExtra("location") ?: "N/A"
        incidentTime = intent.getStringExtra("time") ?: "N/A"
        incidentDate = intent.getStringExtra("date") ?: "N/A"
        incidentHarasserDetails = intent.getStringExtra("harasserDetails") ?: "N/A"

        btnSubmit.setOnClickListener {
            val recipientEmail = etEmail.text.toString().trim()

            if (recipientEmail.isEmpty()) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Prepare email content
            val subject = "Harassment Incident Report"
            val body = """
                Incident Type: $incidentType
                Location: $incidentLocation
                Time: $incidentTime
                Date: $incidentDate
                Harasser Details: $incidentHarasserDetails
            """.trimIndent()

            sendEmail(recipientEmail, subject, body)
        }
    }

    private fun sendEmail(recipientEmail: String, subject: String, body: String) {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"  // MIME type for email
            putExtra(Intent.EXTRA_EMAIL, arrayOf(recipientEmail))
            putExtra(Intent.EXTRA_SUBJECT, subject)
            putExtra(Intent.EXTRA_TEXT, body)
        }

        try {
            startActivity(Intent.createChooser(emailIntent, "Choose an email client"))
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "No email client found", Toast.LENGTH_SHORT).show()
        }
    }
}

