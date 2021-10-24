package com.ener.kelvin11.mainaccount.add
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddContactViewModel : ViewModel() {


    var _isSuccess = false

   var name = ""
   var surname = ""
   var email = ""
   var phone = ""
   var service = ""
   var products = ""
   var price = ""
   var payment = ""
   var date = ""




    fun UploadtToDataBase() {
        val db = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val newData = CustomerData(name, surname, email, phone, service, products, price, payment, date)
        if (uid != null) {
            db.child("Users").child(uid).child("Customers").push().setValue(newData).addOnCompleteListener {
                _isSuccess = it.isSuccessful
            }
        }
    }


}

class CustomerData(
    val name: String,
    val surname: String,
    val email: String,
    val phone: String,
    val service: String,
    val products: String,
    val price: String,
    val payment: String,
    val date: String
) {

    constructor() : this("", "", "", "", "", "", "", "", "")


}