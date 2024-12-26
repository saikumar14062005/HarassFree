package com.example.harassfree

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        val etName: EditText = findViewById(R.id.etName)
        val etAge: EditText = findViewById(R.id.etAge)
        val etUniversity: EditText = findViewById(R.id.etUniversity)
        val radioGroupGender: RadioGroup = findViewById(R.id.radioGroupGender)
        val etEmergencyContact: EditText = findViewById(R.id.etEmergencyContact)
        val etGmail: EditText = findViewById(R.id.etGmail)
        val etPhoneNumber: EditText = findViewById(R.id.etPhoneNumber)
        val etPassword: EditText = findViewById(R.id.etPassword)
        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            val name = etName.text.toString().trim()
            val age = etAge.text.toString().trim()
            val university = etUniversity.text.toString().trim()
            val emergencyContact = etEmergencyContact.text.toString().trim()
            val gmail = etGmail.text.toString().trim()
            val phoneNumber = etPhoneNumber.text.toString().trim()
            val password = etPassword.text.toString().trim()

            val selectedGenderId = radioGroupGender.checkedRadioButtonId
            val gender = if (selectedGenderId != -1) {
                val selectedGenderButton: RadioButton = findViewById(selectedGenderId)
                selectedGenderButton.text.toString()
            } else "Not specified"

            if (name.isEmpty() || age.isEmpty() || university.isEmpty() || emergencyContact.isEmpty() || gmail.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(gmail, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = User(name, age, university, gender, emergencyContact, gmail, phoneNumber)
                    database.reference.child("Users").child(auth.currentUser!!.uid).setValue(user)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        .addOnFailureListener {
                            Toast.makeText(this, "Failed to save details", Toast.LENGTH_SHORT).show()
                        }
                } else {
                    Toast.makeText(this, "Authentication Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
