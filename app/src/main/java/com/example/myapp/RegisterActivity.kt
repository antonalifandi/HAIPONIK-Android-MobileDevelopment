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
    lateinit var editFullName: EditText
    lateinit var editEmail: EditText
    lateinit var editPassword: EditText
    lateinit var editPasswordConf: EditText
    lateinit var buttonRegister: Button
    lateinit var buttonLogin: Button
    lateinit var progressDialog: ProgressDialog

    var firebaseAuth = FirebaseAuth.getInstance()


    override fun onStart() {
        super.onStart()
        if (firebaseAuth.currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
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

        buttonLogin.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        buttonRegister.setOnClickListener{
            if(editFullName.text.isNotEmpty() && editEmail.text.isNotEmpty() && editPassword.text.isNotEmpty()){
                if (editPassword.text.toString() == editPasswordConf.text.toString()) {
                    // LAUNCH REGISTER
                    proscessRegister()
                }else {
                    Toast.makeText(this, "Konfirmasi kata sandi harus sama!", LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Silahkan isi semua data", LENGTH_SHORT).show()
            }
        }
    }

    private fun proscessRegister(){
        val fullName = editFullName.text.toString()
        val email = editEmail.text.toString()
        val password = editPassword.text.toString()

        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val userUpdateProfile = userProfileChangeRequest {
                        displayName = fullName
                    }

                    val user = task.result.user
                    user!!.updateProfile(userUpdateProfile)
                        .addOnCompleteListener {
                            progressDialog.dismiss()
                            startActivity(Intent(this, MainActivity::class.java))
                        }
                        .addOnFailureListener{ error2 ->
                            Toast.makeText(this, error2.localizedMessage, LENGTH_SHORT).show()

                        }
                }else{
                    progressDialog.dismiss()
                }
            }
            .addOnFailureListener{ error ->
                            Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()


            }

    }
}