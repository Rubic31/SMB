package com.example.mini_project_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mini_project_1.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNavigationBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        editor = sp.edit()

        val productListActivityIntent = Intent(applicationContext, ProductListActivity::class.java)

        //starts list activity
        binding.btList2.setOnClickListener {
            startActivity(productListActivityIntent)
        }

        val settingsActivityIntent = Intent(applicationContext, SettingsActivity::class.java)

        //starts options activity
        binding.btOptions2.setOnClickListener {
            startActivity(settingsActivityIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        binding.navigationLay.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "darkmodecolor",
                    "#FFFFFF"
                )
            )
        )

        binding.btList2.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
        binding.btOptions2.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())

    }
}