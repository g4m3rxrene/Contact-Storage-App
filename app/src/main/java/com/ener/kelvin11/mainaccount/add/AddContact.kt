package com.ener.kelvin11.mainaccount.add

import android.os.Bundle
import android.os.Handler
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ener.kelvin11.R
import com.ener.kelvin11.databinding.AddContactFragmentBinding
import java.text.DateFormat
import java.util.*
import kotlin.concurrent.timerTask

class AddContact : Fragment() {

    private lateinit var viewModel: AddContactViewModel
    lateinit var binding: AddContactFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddContactFragmentBinding.inflate(layoutInflater, container, false)
        observeSpinnerValues()
        viewModel = AddContactViewModel()
        SetListeners()
        return binding.root
    }



    private fun observeSpinnerValues() {
        val services = arrayOf("Service", "Haircut", "Shave", "Haircut + Shave")
        val productslist = arrayOf("Products", "Beard Oil", "Shaving Cream", "Hair Food")
        val prices = arrayOf("Price", "R80", "R60", "R30")
        val paymentMethods = arrayOf("Payment Method", "Cash", "Bank Transfer", "Credit Card")

        binding.serviceSpinner.adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    services
                )
            }

        binding.productSpinner.adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    productslist
                )
            }
        binding.priceSpinner.adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    prices
                )
            }
        binding.paymentMethodSpinner.adapter =
            context?.let {
                ArrayAdapter(
                    it,
                    android.R.layout.simple_expandable_list_item_1,
                    paymentMethods
                )
            }
    }


    private fun SetListeners() {
        binding.submitButton.isEnabled = false
        binding.termsCheckbox.setOnCheckedChangeListener { buttonView, isChecked ->
            binding.submitButton.isEnabled = isChecked
        }
        binding.submitButton.setOnClickListener {

            DataChecks()
            //!! code Below too long, need more efficient way and condesned format, also seems to be a bug if we wrap this code in the above function, data will get submitted nypassing the checks ,, need future reinfrcement

            //A very long list of code making checks
            if (binding.firstName.text.toString().trim().isEmpty()) {
                binding.firstName.setError("Please enter your name!")
                binding.firstName.requestFocus()
                return@setOnClickListener
            }
            if (binding.firstName.text.toString().length <= 3) {
                binding.firstName.setError("Name should be more than 3 characters")
                binding.firstName.requestFocus()

                return@setOnClickListener
            }

            if (binding.surname.text.toString().trim().isEmpty()) {
                binding.surname.setError("Please enter your surname!")
                binding.surname.requestFocus()
                return@setOnClickListener
            }
            if (binding.surname.text.toString().length <= 3) {
                binding.surname.setError("Surname should be more than 3 characters")
                binding.surname.requestFocus()
                return@setOnClickListener
            }

            if (binding.email.text.toString().trim().isEmpty()) {
                binding.email.setError("Enter your email address")
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(binding.email.text.toString()).matches()) {
                binding.email.setError("Enter a valid email address")
                binding.email.requestFocus()
                return@setOnClickListener
            }
            if (binding.phone.text.toString().trim().isEmpty()) {
                binding.phone.setError("Enter your phone number!")
                binding.phone.requestFocus()
                return@setOnClickListener
            }
            if (binding.phone.text.toString().length != 10) {
                binding.phone.setError("Phone number should be more than 10 digits")
                binding.phone.requestFocus()
                return@setOnClickListener
            }

            if (binding.serviceSpinner.selectedItem.toString() == "Service") {
                val errorText = binding.serviceSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            if (binding.productSpinner.selectedItem.toString() == "Products") {
                val errorText = binding.productSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            if (binding.priceSpinner.selectedItem.toString() == "Price") {
                val errorText = binding.priceSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            if (binding.paymentMethodSpinner.selectedItem.toString() == "Payment Method") {
                val errorText = binding.paymentMethodSpinner.selectedView as TextView
                errorText.error = "client required"
                errorText.requestFocus()
                return@setOnClickListener
            }
            // The code ends here,lol,that was a handful

            assignValuestoViewModel()
            viewModel.UploadtToDataBase()


        }
    }


    private fun DataChecks() {

        return
    }

    private fun getDate(): String {
        val calender = Calendar.getInstance()
        return DateFormat.getDateInstance(DateFormat.FULL).format(calender.time)

    }

    fun assignValuestoViewModel() {
        viewModel.name = binding.firstName.text.toString().trim()
        viewModel.surname = binding.surname.text.toString().trim()
        viewModel.email = binding.email.text.toString().trim()
        viewModel.phone = binding.phone.text.toString().trim()
        viewModel.service = binding.serviceSpinner.selectedItem.toString()
        viewModel.products = binding.productSpinner.selectedItem.toString()
        viewModel.price = binding.priceSpinner.selectedItem.toString()
        viewModel.payment = binding.paymentMethodSpinner.selectedItem.toString()
        viewModel.date = getDate()
    }


}