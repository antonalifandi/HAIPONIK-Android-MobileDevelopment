package com.example.myapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.myapp.databinding.FragmentWhatsappBinding


class WhatsappFragment : Fragment() {

    private var _binding: FragmentWhatsappBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWhatsappBinding.inflate(inflater, container, false)
        return  binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnWhatsApp.setOnClickListener{
            WhatsApp()
        }

        view
    }

    private fun WhatsApp() {
        val wa = "+6285697710843"
        val uri = Uri.parse("https://api.whatsapp.com/send?phone=$+6285697710843")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}

