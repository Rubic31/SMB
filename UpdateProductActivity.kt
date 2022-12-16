package com.example.mini_project_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import androidx.lifecycle.Observer
import com.example.mini_project_1.databinding.ActivityUpdateProductBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUpdateProductBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateProductBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        editor = sp.edit()

        var productViewModel = ProductViewModel(application)
        var productadapter = ProductAdapter(productViewModel)

        productViewModel.allProducts.observe(this, Observer { productList ->
            productList.let{
                productadapter.setProducts(productList.values.toList())
            }
        })

        //gets from intent values for edittexts
        binding.etNameUp.text =
            Editable.Factory.getInstance().newEditable(intent.getStringExtra("tvname"))
        binding.etPriceUp.text =
            Editable.Factory.getInstance().newEditable(intent.getStringExtra("tvprice"))
        binding.etQuantityUp.text =
            Editable.Factory.getInstance().newEditable(intent.getStringExtra("tvquantity"))


        val productListActivityIntent = Intent(applicationContext, ProductListActivity::class.java)

        //updates product adapter 1 item and returns to list activity
        binding.btUpProduct.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                productadapter.update(
                    Product(
                        id = intent.getStringExtra("productid")!!,
                        name = binding.etNameUp.text.toString(),
                        price = binding.etPriceUp.text.toString(),
                        quantity = binding.etQuantityUp.text.toString().toLong(),
                        bought = intent.getBooleanExtra("productbought", false)
                    )
                )
            }
            startActivity(productListActivityIntent)
        }
    }

    //changing background and color, text size on start
    override fun onStart() {
        super.onStart()
        binding.updateLay.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "darkmodecolor",
                    "#FFFFFF"
                )
            )
        )
        binding.etNameUp.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etQuantityUp.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.etPriceUp.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.btUpProduct.setTextSize(sp.getString("textsizesm", "18")!!.toFloat())
        binding.etNameUp.setTextSize(sp.getString("textsizeet", "18")!!.toFloat())
        binding.etPriceUp.setTextSize(sp.getString("textsizeet", "18")!!.toFloat())
        binding.etQuantityUp.setTextSize(sp.getString("textsizeet", "18")!!.toFloat())
    }
}