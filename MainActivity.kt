package com.example.mini_project_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mini_project_1.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import java.security.KeyStore.TrustedCertificateEntry
import kotlin.properties.Delegates

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        editor = sp.edit()

        auth = FirebaseAuth.getInstance()


        binding.btRegister.setOnClickListener {
            auth.createUserWithEmailAndPassword(
                binding.etLogin.text.toString(),
                binding.etPassword.text.toString()
            )
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(
                            this,
                            "Registered successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }else{
                        Toast.makeText(
                            this,
                            "Failed to register!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("register", it.exception?.message.toString())
                    }
                }
        }



        binding.btLogin.setOnClickListener {
            auth.signInWithEmailAndPassword(
                binding.etLogin.text.toString(),
                binding.etPassword.text.toString()
            )
                .addOnCompleteListener {
                    if(it.isSuccessful){

                        val navigationActivityIntent = Intent(applicationContext, NavigationActivity::class.java)
                        startActivity(navigationActivityIntent)

                    }else{
                        Toast.makeText(
                            this,
                            "Failed to login!",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("login", it.exception?.message.toString())
                    }
                }
        }



    }

    //changing background and text size on start
    override fun onStart() {
        super.onStart()
        binding.mainLay.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "darkmodecolor",
                    "#FFFFFF"
                )
            )
        )

        binding.btLogin.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
        binding.btRegister.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
        binding.etLogin.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
        binding.etPassword.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())

        binding.etLogin.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etPassword.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etLogin.setHintTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etPassword.setHintTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))

    }

}