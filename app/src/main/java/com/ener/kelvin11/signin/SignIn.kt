package com.ener.kelvin11.signin

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ener.kelvin11.R
import com.ener.kelvin11.databinding.SignInFragmentBinding
import com.ener.kelvin11.mainaccount.MainAccount
import com.google.firebase.auth.FirebaseAuth

class SignIn : Fragment() {
    private var mAuth: FirebaseAuth? = null
    lateinit var binding: SignInFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignInFragmentBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        setListener()
        return binding.root
    }

    private fun setListener() {

        binding.signinButton.setOnClickListener {
            if (binding.email.text.toString().trim().isEmpty()) {
                binding.email.setError("Input an email address!")
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString().trim()).matches()) {
                binding.email.setError("Input a valid email!")
                binding.email.requestFocus()
                return@setOnClickListener

            }
            if (binding.email.text.toString().trim().isEmpty()) {
                binding.email.setError("Input a valid email!")
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (binding.password.text.toString().isEmpty()) {
                binding.password.setError("Input a valid password!")
                binding.password.requestFocus()
                return@setOnClickListener
            }
            if (binding.password.text.toString().length < 6) {
                binding.password.setError("Minimum password length is 6 characters!")
                binding.password.requestFocus()
                return@setOnClickListener

            }



            mAuth?.signInWithEmailAndPassword(
                binding.email.text.toString().trim(),
                binding.password.text.toString().trim()
            )
                ?.addOnCompleteListener {

                    if (it.isSuccessful) {
                        val auth = FirebaseAuth.getInstance().currentUser
                        if (auth != null) {
                            if (auth.isEmailVerified) {
                                //send to main account
                                val intent = Intent(context, MainAccount::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                            } else {
                                auth.sendEmailVerification()
                                Toast.makeText(
                                    context,
                                    "Verify Your Account",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    } else {
                        Toast.makeText(context, "Incorrect email/password", Toast.LENGTH_SHORT)
                            .show()
                    }

                }


        }


    }
}