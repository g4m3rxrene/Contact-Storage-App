package com.ener.kelvin11

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ener.kelvin11.databinding.ActivityRecyclerviewTempBinding

class RecyclerviewTemp : AppCompatActivity() {
    lateinit var binding:ActivityRecyclerviewTempBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerviewTempBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}