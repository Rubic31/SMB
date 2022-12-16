package com.example.mini_project_1

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.Observer
import com.example.mini_project_1.databinding.ActivityAddProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddProductActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddProductBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        editor = sp.edit()

        var productViewModel = ProductViewModel(application)
        var productadapter = ProductAdapter(productViewModel)


        productViewModel.allProducts.observe(this, Observer { productList ->
            productList.let {
                productadapter.setProducts(productList.values.toList())
            }
        })


        val productListActivityIntent = Intent(applicationContext, ProductListActivity::class.java)

        //adds to product adapter 1 item and returns to list activity
        binding.btAddProduct.setOnClickListener {
            var product = Product(
                id = "new",
                name = binding.etName.text.toString(),
                price = binding.etPrice.text.toString(),
                quantity = binding.etQuantity.text.toString().toLong()
            )
            //CoroutineScope(Dispatchers.IO).launch {
            productadapter.dodaj(product)
            //}

            val broadcastIntent = Intent().also {
                it.component = ComponentName(
                    "com.example.productreceiverapp",
                    "com.example.productreceiverapp.ProductReceiver"
                )
                it.putExtra("productName", product.name)
                //it.action = "my.app.PERMISSION"
            }

            Log.d("ARE NOTIFICATIONS ENABLED",
                NotificationManagerCompat.from(applicationContext).areNotificationsEnabled()
                    .toString()
            )
            sendBroadcast(broadcastIntent, "com.example.mini_project_1.MY_PERMISSION")

//            val broadcastIntentToSelf = Intent().also {
//                it.component = ComponentName(
//                    "com.example.mini_project_1",
//                    "com.example.mini_project_1.PrReceiver"
//                )
//                it.putExtra("productName", product.name)
//                //it.action = "my.app.PERMISSION"
//            }

            //sendBroadcast(broadcastIntentToSelf)


            startActivity(productListActivityIntent)

        }
    }


    //changing background and color, text size on start
    override fun onStart() {
        super.onStart()
        binding.addLay.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "darkmodecolor",
                    "#FFFFFF"
                )
            )
        )

        binding.etName.setHintTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etPrice.setHintTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etQuantity.setHintTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etName.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etPrice.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etQuantity.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.btAddProduct.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
        binding.etName.setTextSize(sp.getString("textsizeet", "18")!!.toFloat())
        binding.etPrice.setTextSize(sp.getString("textsizeet", "18")!!.toFloat())
        binding.etQuantity.setTextSize(sp.getString("textsizeet", "18")!!.toFloat())
    }

}