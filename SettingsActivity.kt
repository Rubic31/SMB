package com.example.mini_project_1


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mini_project_1.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        editor = sp.edit()


        val navigationActivityIntent = Intent(applicationContext, NavigationActivity::class.java)
        //return button starts mainActivity
        binding.btReturn.setOnClickListener {
            startActivity(navigationActivityIntent)
        }

        //switch listener that changes background color
        binding.swDarkmode.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editor.putString("darkmodecolor", "#404040")
                editor.putBoolean("switchmode", binding.swDarkmode.isChecked)
                editor.apply()
            } else {
                editor.putString("darkmodecolor", "#FFFFFF")
                editor.putBoolean("switchmode", binding.swDarkmode.isChecked)
                editor.apply()
            }
            binding.stLay.setBackgroundColor(
                Color.parseColor(
                    sp.getString(
                        "darkmodecolor",
                        "#FFFFFF"
                    )
                )
            )
        }

        //radioGroup2 listener, that changes color
        binding.radioGroup2.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.rbTurquoise.id) {
                editor.putString("textcolor", "#00FFEF")
                editor.putInt("radiogroup2", 1)
                editor.apply()
            } else if (checkedId == binding.rbPurple.id) {
                editor.putString("textcolor", "#6441a5")
                editor.putInt("radiogroup2", 2)
                editor.apply()
            } else if (checkedId == binding.rbBlack.id) {
                editor.putString("textcolor", "#000000")
                editor.putInt("radiogroup2", 3)
                editor.apply()
            }

            binding.tvTxsize.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.tvTxColor.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.rbPurple.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.rbTurquoise.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.rbBlack.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.rbSmall.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.rbMedium.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.rbLarge.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
            binding.swDarkmode.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
//            binding.rbPurple.backgroundTintList =
//                getColorStateList(Color.parseColor(sp.getString("textcolor", "#000000")))
        }

        //radioGroup(1) listener, that changes text size
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == binding.rbSmall.id) {
                editor.putString("textsizesm", "14")
                editor.putString("textsizebi", "30")
                editor.putString("textsizemi", "20")
                editor.putString("textsizeet", "14")

                editor.putInt("radiogroup", 1)
                editor.apply()
            } else if (checkedId == binding.rbMedium.id) {
                editor.putString("textsizesm", "18")
                editor.putString("textsizebi", "34")
                editor.putString("textsizemi", "22")
                editor.putString("textsizeet", "16")
                editor.putInt("radiogroup", 2)
                editor.apply()
            } else if (checkedId == binding.rbLarge.id) {
                editor.putString("textsizesm", "22")
                editor.putString("textsizebi", "38")
                editor.putString("textsizemi", "24")
                editor.putString("textsizeet", "18")
                editor.putInt("radiogroup", 3)
                editor.apply()
            }

            binding.rbSmall.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.rbMedium.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.rbLarge.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.rbBlack.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.rbPurple.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.rbTurquoise.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.btReturn.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
            binding.tvTxsize.setTextSize(sp.getString("textsizebi", "34")!!.toFloat())
            binding.tvTxColor.setTextSize(sp.getString("textsizebi", "34")!!.toFloat())
            binding.swDarkmode.setTextSize(sp.getString("textsizebi", "34")!!.toFloat())

        }

    }


    //checks the state of the buttons and switches them to position
    override fun onStart() {
        super.onStart()

        binding.swDarkmode.isChecked = sp.getBoolean("switchmode", false)

        if (sp.getInt("radiogroup2", 1) == 1) {
            binding.rbTurquoise.isChecked = true
        } else if (sp.getInt("radiogroup2", 1) == 2) {
            binding.rbPurple.isChecked = true
        } else if (sp.getInt("radiogroup2", 1) == 3) {
            binding.rbBlack.isChecked = true
        }

        if (sp.getInt("radiogroup", 1) == 1) {
            binding.rbSmall.isChecked = true
        } else if (sp.getInt("radiogroup", 1) == 2) {
            binding.rbMedium.isChecked = true
        } else if (sp.getInt("radiogroup", 1) == 3) {
            binding.rbLarge.isChecked = true
        }

    }
}