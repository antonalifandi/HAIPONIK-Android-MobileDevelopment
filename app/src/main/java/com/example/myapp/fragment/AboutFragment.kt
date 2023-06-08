package com.example.myapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myapp.R

class AboutFragment : Fragment() {

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            // Inflate the layout for this fragment
            val view = inflater.inflate(R.layout.fragment_about, container, false)

            val closeButton: Button = view.findViewById(R.id.btnClose)
            closeButton.setOnClickListener {
                activity?.finishAffinity()
            }

            return view
        }
    }
