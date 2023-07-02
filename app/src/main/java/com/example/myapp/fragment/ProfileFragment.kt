package com.example.myapp.fragment

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapp.LoginActivity
import com.example.myapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var imgUri: Uri
    private val binding get() = _binding!!

    companion object {
        const val CAMERA_REQ_CODE = 100
        const val GALLERY_REQ_CODE = 200
        const val CAMERA_PERMISSION_CODE = 101
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

            if (user.photoUrl != null) {
                Picasso.get().load(user.photoUrl).into(binding.ivProfile)
            } else {
                Picasso.get()
                    .load("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                    .into(binding.ivProfile)
            }
        }

        binding.btnLogout.setOnClickListener {
            tombolkeluar()
        }
        binding.ivProfile.setOnClickListener {
            selectImageSource()
        }
        binding.btnSave.setOnClickListener {
            val image = when {
                ::imgUri.isInitialized -> imgUri
                user?.photoUrl == null -> Uri.parse("https://cdn-icons-png.flaticon.com/512/149/149071.png")
                else -> user.photoUrl
            }

            val name = binding.etName.text.toString()

            if (name.isEmpty()) {
                binding.etName.error = "Nama Belum Di isi!"
                binding.etName.requestFocus()
                return@setOnClickListener
            }

            val profileUpdate = UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .setPhotoUri(image)
                .build()

            user?.updateProfile(profileUpdate)?.addOnCompleteListener { task ->
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

    private fun selectImageSource() {
        val options = arrayOf("Kamera", "Galeri")
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setTitle("Pilih Sumber Gambar")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> {
                    if (ContextCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        openCamera()
                    } else {
                        ActivityCompat.requestPermissions(
                            requireActivity(),
                            arrayOf(Manifest.permission.CAMERA),
                            CAMERA_PERMISSION_CODE
                        )
                    }
                }
                1 -> openGallery()
            }
        }
        builder.show()
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQ_CODE)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQ_CODE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCamera()
        } else {
            Toast.makeText(
                requireContext(),
                "Izin akses kamera ditolak",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQ_CODE -> {
                    val imgBitmap = data?.extras?.get("data") as Bitmap
                    uploadImageToFirebase(imgBitmap)
                }
                GALLERY_REQ_CODE -> {
                    val selectedImageUri: Uri? = data?.data
                    if (selectedImageUri != null) {
                        val bitmap =
                            MediaStore.Images.Media.getBitmap(
                                requireActivity().contentResolver,
                                selectedImageUri
                            )
                        uploadImageToFirebase(bitmap)
                    }
                }
            }
        }
    }

    private fun uploadImageToFirebase(imgBitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        val ref =
            FirebaseStorage.getInstance().reference.child("image_user/${FirebaseAuth.getInstance().currentUser?.email}")
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val img = baos.toByteArray()

        ref.putBytes(img).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                ref.downloadUrl.addOnCompleteListener { downloadTask ->
                    if (downloadTask.isSuccessful) {
                        val uri = downloadTask.result
                        imgUri = uri
                        binding.ivProfile.setImageBitmap(imgBitmap)
                    } else {
                        Toast.makeText(activity, "Gagal mengunggah gambar", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(activity, "Gagal mengunggah gambar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun tombolkeluar() {
        auth = FirebaseAuth.getInstance()
        auth.signOut()
        val i = Intent(context, LoginActivity::class.java)
        startActivity(i)
        activity?.finish()
    }
}
