package com.example.mini_project_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mini_project_1.databinding.ActivityProductListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        editor = sp.edit()

        var productViewModel = ProductViewModel(application)
        var productadapter = ProductAdapter(productViewModel)

        binding.rv.adapter = productadapter


        productViewModel.allProducts.observe(this, Observer { productList ->
            productList.let{
                productadapter.setProducts(productList.values.toList())
            }
        })



        val addProductActivityIntent = Intent(applicationContext, AddProductActivity::class.java)


        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        //binding.rv.adapter = productadapter


        //starts addProductActivity
        binding.btAdd.setOnClickListener {
            startActivity(addProductActivityIntent)
        }


        //dev button that adds cebula, REMOVE for evaluation
//        binding.btDev.setOnClickListener {
//            CoroutineScope(Dispatchers.IO).launch {
//                productadapter.dodaj(
//                    Product(
//                        id="new",
//                        name = "Cebula",
//                        price = 3.43,
//                        quantity = 4
//                    )
//                )
//            }
//        }


        val navigationActivityIntent = Intent(applicationContext, NavigationActivity::class.java)
        //return to main menu
        binding.btReturnMain.setOnClickListener {
            startActivity(navigationActivityIntent)
        }

    }

    //changing background and color, text size on start
    override fun onStart() {
        super.onStart()
        binding.prListLay.setBackgroundColor(
            Color.parseColor(
                sp.getString(
                    "darkmodecolor",
                    "#FFFFFF"
                )
            )
        )
        binding.tvNavBought.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.tvNavDel.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.tvNavName.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.tvNavPrice.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.tvNavQuantity.setTextColor(Color.parseColor(sp.getString("textcolor", "#000000")))
        binding.tvNavQuantity.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
        binding.tvNavPrice.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
        binding.tvNavDel.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
        binding.tvNavName.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
        binding.tvNavBought.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
    }
}