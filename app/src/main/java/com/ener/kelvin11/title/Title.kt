package com.ener.kelvin11.title

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ener.kelvin11.R
import com.ener.kelvin11.databinding.TitleFragmentBinding

class Title : Fragment() {

    private lateinit var viewModel: TitleViewModel
    lateinit var binding:TitleFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TitleFragmentBinding.inflate(layoutInflater,container,false)
        binding.signinButton.setOnClickListener { findNavController().navigate(R.id.action_title2_to_signIn) }
        binding.signupButton.setOnClickListener { findNavController().navigate(R.id.action_title2_to_signUp) }
        return binding.root
    }


}