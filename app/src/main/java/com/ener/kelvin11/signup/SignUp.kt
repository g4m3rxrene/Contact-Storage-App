package com.ener.kelvin11.signup

import android.content.ContentValues
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
import com.ener.kelvin11.databinding.SignUpFragmentBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUp : Fragment() {


    lateinit var binding: SignUpFragmentBinding
    private var mAuth: FirebaseAuth? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignUpFragmentBinding.inflate(layoutInflater, container, false)
        mAuth = FirebaseAuth.getInstance()
        setButtonBinding()  // Later Update to dataBinding!!
        return binding.root
    }


    private fun setButtonBinding() {
        binding.signupButton.setOnClickListener {
          PerformDataChecks()
            CreateAccount() // Note!! Implemenet Data binding to clean fragmant


        }

        }

    private fun CreateAccount() {
        binding.progressBar.show()
        mAuth?.createUserWithEmailAndPassword(
            binding.email.text.toString().trim(),
            binding.password.text.toString()
        )
            ?.addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val userdata = UserData(
                        binding.name.text.toString().trim(),
                        binding.surname.text.toString().trim(),
                        binding.email.text.toString().trim(),
                        binding.password.text.toString().trim()
                    )
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().currentUser!!.uid).setValue(userdata)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                //Tell user that account creation was a sucess
                                    binding.progressBar.hide()
                                Log.i(ContentValues.TAG, "State: User Account creation success")
                                Toast.makeText(context,"Successfully created account",Toast.LENGTH_SHORT).show()
                                findNavController().navigate(R.id.action_signUp_to_signIn)
                                // Go To Login Page

                            } else {
                                binding.progressBar.hide()
                                Log.w(
                                    ContentValues.TAG,
                                    "createUserWithEmail:failure",
                                    task.exception
                                )
                                // Tell user that task failed and they should try again or check connection!
                                Toast.makeText(context,"Failed, check network and try again.",Toast.LENGTH_SHORT).show()

                            }
                        }

                } else {
                    binding.progressBar.hide()
                    // If sign in fails, display a message to the user.
                    Toast.makeText(context,"Failed, check network and try again.",Toast.LENGTH_SHORT).show()
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                    //Done!
                }

            }
    }

    private fun PerformDataChecks() {
        // Perfoming User Input Checks
        if (binding.name.text.toString().trim().isEmpty()) {
            binding.name.setError("Full Name is required!")
            binding.name.requestFocus()
            return
        }

        if (binding.surname.text.toString().trim().isEmpty()) {
            binding.surname.setError("Surname is required!")
            binding.surname.requestFocus()
            return
        }
        if (binding.email.text.toString().trim().isEmpty() ) {
            binding.email.setError("Input an email address!")
            binding.email.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString().trim()).matches()){
            binding.email.setError("Input a valid email!")
            binding.email.requestFocus()
            return

        }
        if (binding.email.text.toString().trim().isEmpty()) {
            binding.email.setError("Input a valid email!")
            binding.email.requestFocus()
            return
        }
        if (binding.password.text.toString().isEmpty()) {
            binding.password.setError("Input a valid password!")
            binding.password.requestFocus()
            return
        }
        if (binding.password.text.toString().length < 6) {
            binding.password.setError("Minimum password length is 6 characters!")
            binding.password.requestFocus()
            return

        }
        // Ending Checks
    }
}

    data class UserData(
        val name: String,
        val surname: String,
        val email: String,
        val password: String
    )