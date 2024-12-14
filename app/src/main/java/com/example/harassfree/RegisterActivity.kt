package com.example.harassfree

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

data class User(
    var name: String = "",
    var age: String = "",
    var university: String = "",
    var gender: String = "",
    var emergencyContact: String = "",
    var gmail: String = "",
    var phoneNumber: String = ""
)

class RegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val etName: EditText = findViewById(R.id.etName)
        val etAge: EditText = findViewById(R.id.etAge)
        val etUniversity: EditText = findViewById(R.id.etUniversity)
        val radioGroupGender: RadioGroup = findViewById(R.id.radioGroupGender)
        val etEmergencyContact: EditText = findViewById(R.id.etEmergencyContact)
        val etGmail: EditText = findViewById(R.id.etGmail)
        val etPhoneNumber: EditText = findViewById(R.id.etPhoneNumber)
        val etPassword: EditText = findViewById(R.id.etPassword)  // Password field
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().trim()
            val university = etUniversity.text.toString().trim()
            val emergencyContact = etEmergencyContact.text.toString().trim()
            val gmail = etGmail.text.toString().trim()
            val phoneNumber = etPhoneNumber.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Get selected gender from the RadioGroup
            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedGenderButton: RadioButton = findViewById(selectedGenderId)
                selectedGenderButton.text.toString()
            } else {
                "Not specified" // Default if no gender is selected
            }

            // Validate the inputs
            if (name.isEmpty() || age.isEmpty() || university.isEmpty() || emergencyContact.isEmpty() || gmail.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Register the user in Firebase Authentication (email/password)
            auth.createUserWithEmailAndPassword(gmail, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Successfully created user in Authentication
                        val user = User(name, age, university, gender, emergencyContact, gmail, phoneNumber)

                        // Save user details to Firebase Realtime Database
                        database.reference.child("Users").child(auth.currentUser!!.uid).setValue(user)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                                finish()  // Proceed to next screen
                            }
                            .addOnFailureListener {
                                Toast.makeText(this, "Failed to save user details in database", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        // Handle registration failure (e.g., email already in use)
                        Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }


}
