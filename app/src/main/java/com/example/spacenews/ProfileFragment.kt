package com.example.spacenews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast

class ProfileFragment : Fragment() {

    private lateinit var email: EditText
    private lateinit var password: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view:View = inflater.inflate(R.layout.fragment_profile, container, false)
        email = view.findViewById(R.id.email)
        password = view.findViewById(R.id.password)

        addListeners()
        return view
    }

    private fun addListeners()
    {

        email.setOnFocusChangeListener { v, hasFocus ->
            Toast.makeText(context, "Not available yet", Toast.LENGTH_SHORT).show()
            email.text.clear()
        }

        password.setOnFocusChangeListener { v, hasFocus ->
            Toast.makeText(context, "Not available yet", Toast.LENGTH_SHORT).show()
            password.text.clear()
        }
    }
}