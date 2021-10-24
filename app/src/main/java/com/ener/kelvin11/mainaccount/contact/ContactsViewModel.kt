package com.ener.kelvin11.mainaccount.contact

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ener.kelvin11.mainaccount.add.CustomerData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ContactsViewModel : ViewModel() {

    private val _userData = MutableLiveData<CustomerData>()
    val userData:LiveData<CustomerData>

    get() = _userData


     fun fetchUsers() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        uid?.let {
            FirebaseDatabase.getInstance().getReference("/Users").child(it).child("Customers")
        }?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    Log.d(ContentValues.TAG, "$it")
                    val user = it.getValue(CustomerData::class.java)

                    if (user != null) {
                        _userData.value = user!!
                    }



                }

            }

            override fun onCancelled(error: DatabaseError) {
                //To be implemented??
            }

        })
    }

}