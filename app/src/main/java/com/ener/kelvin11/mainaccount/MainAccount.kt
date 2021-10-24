package com.ener.kelvin11.mainaccount

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ener.kelvin11.R
import com.ener.kelvin11.databinding.ActivityMainAccountBinding
import com.ener.kelvin11.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class MainAccount : AppCompatActivity() {
    lateinit var binding:ActivityMainAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.account_navController) as NavHostFragment
        val navHostController = navHostFragment.findNavController()
        binding.bottomNav.setupWithNavController(navHostController)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.log_out->{
                    val auth = FirebaseAuth.getInstance()
                    auth.signOut()
                    startActivity(Intent(this,MainAccount::class.java))
                    true
                }else->false
            }
        }
        CheckIfUserIsLoggedIn()

    }

    private fun CheckIfUserIsLoggedIn() {
        val auth = FirebaseAuth.getInstance().currentUser
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null || !auth!!.isEmailVerified) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}