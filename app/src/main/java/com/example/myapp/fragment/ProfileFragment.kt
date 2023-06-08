package com.example.myapp.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapp.LoginActivity
import com.example.myapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    lateinit var auth: FirebaseAuth
    lateinit var imgUri: Uri
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser

        if (user != null) {
            binding.etName.setText(user.displayName)
            binding.etEmail.setText(user.email)

            if (user?.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding.ivProfile)
            } else {
                Picasso.get().load("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                    .into(binding.ivProfile)
            }
        }

        binding.btnLogout.setOnClickListener {
            tombolkeluar()
        }
        binding.ivProfile.setOnClickListener {
            gotoCamera()
        }
        binding.btnSave.setOnClickListener btnSave@{
            val image = when {
                ::imgUri.isInitialized -> imgUri
                user?.photoUrl == null -> Uri.parse("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                else -> user.photoUrl
            }

            val name = binding.etName.text.toString()

            if (name.isEmpty()) {
                binding.etName.error = " Nama Belum Di isi !"
                binding.etName.requestFocus()
                return@btnSave
            }

            // update datanya
            UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build().also {
                    user?.updateProfile(it)?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val toast = Toast.makeText(
                                activity,
                                "Data Berhasil Disimpan",
                                Toast.LENGTH_SHORT
                            )
                            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                            toast.show()
                        } else {
                            Toast.makeText(
                                activity,
                                "${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

    private fun gotoCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {intent ->
            activity?.packageManager?.let {
                intent?.resolveActivity(it).also {
                    startActivityForResult(intent, REQ_CODE)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_CODE && resultCode == RESULT_OK){
            val imgBitmap = data?.extras?.get("data") as Bitmap

            uploadImageToFirebase(imgBitmap)

        }
    }

    private fun uploadImageToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()

        // masuk ke direktori firebase
        val ref =
            FirebaseStorage.getInstance().reference.child("image_user/${FirebaseAuth.getInstance().currentUser?.email}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val img = baos.toByteArray()
        ref.putBytes(img)
            .addOnCompleteListener {
                if (it.isSuccessful){
                    ref.downloadUrl.addOnCompleteListener{ Task ->
                        Task.result?.let { uri ->
                            imgUri = uri
                            binding.ivProfile.setImageBitmap(imgBitmap)
                        }
                    }
                }
            }

    }

    private fun tombolkeluar(){
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val i = Intent(context, LoginActivity::class.java)
        startActivity(i)
        activity?.finish()
    }

    companion object{
        const val REQ_CODE = 100
    }

}





