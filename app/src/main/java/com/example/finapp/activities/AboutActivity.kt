package com.example.finapp.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finapp.databinding.ActivityAboutBinding



class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.buttonRateApp.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${applicationContext.packageName}")))
        }

    }
}