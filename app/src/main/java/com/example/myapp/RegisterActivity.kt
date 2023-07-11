package com.example.myapp

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest

class RegisterActivity : AppCompatActivity() {
    private lateinit var editFullName: EditText
    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var editPasswordConf: EditText
    private lateinit var buttonRegister: Button
    private lateinit var buttonLogin: Button
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser != null) {
            firebaseAuth.signOut()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        editFullName = findViewById(R.id.full_name)
        editEmail = findViewById(R.id.email)
        editPassword = findViewById(R.id.password)
        editPasswordConf = findViewById(R.id.password_conf)
        buttonLogin = findViewById(R.id.button_login)
        buttonRegister = findViewById(R.id.button_register)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan tunggu...")

        firebaseAuth = FirebaseAuth.getInstance()

        buttonLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        buttonRegister.setOnClickListener {
            if (editFullName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()) {
                if (editPassword.text.toString() == editPasswordConf.text.toString()) {
                    // LAUNCH REGISTER
                    processRegister()
                } else {
                    Toast.makeText(this, "Konfirmasi kata sandi harus sama!", LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Silahkan isi semua data", LENGTH_SHORT).show()
            }
        }
    }

    private fun processRegister() {
        val fullName = editFullName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = fullName
                    }

                    val user = firebaseAuth.currentUser
                    user?.updateProfile(userUpdateProfile)
                        ?.addOnCompleteListener {
                            progressDialog.dismiss()
                            Toast.makeText(
                                this,
                                "Registrasi berhasil! Data disimpan.",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(this, LoginActivity::class.java))
                            finish()
                        }
                        ?.addOnFailureListener { error2 ->
                            progressDialog.dismiss()
                            Toast.makeText(this, error2.localizedMessage, Toast.LENGTH_SHORT).show()
                        }
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(this, "Registrasi gagal.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { error ->
                progressDialog.dismiss()
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
            }
    }
}
