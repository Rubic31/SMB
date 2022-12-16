package com.example.mini_project_1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mini_project_1.databinding.ElementBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProductAdapter(private val viewmodel: ProductViewModel) :
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {
    private var products = emptyList<Product>()


    class ViewHolder(val binding: ElementBinding) : RecyclerView.ViewHolder(binding.root)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ElementBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    //var tsum: Double = 0.0
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.tvProductName.text = products[position].name
        holder.binding.tvProductPrice.text = products[position].price.toString()
        holder.binding.tvProductQuantity.text = products[position].quantity.toString()
        holder.binding.cbProductBought.isChecked = products[position].bought

        var sp: SharedPreferences
        //var editor: SharedPreferences.Editor

        sp = holder.binding.root.context.getSharedPreferences("PREFERENCES", Context.MODE_PRIVATE)
        //editor = sp.edit()

        holder.binding.tvProductName.setOnClickListener {

            val updateProductActivityIntent =
                Intent(holder.binding.root.context, UpdateProductActivity::class.java)
            updateProductActivityIntent.putExtra(
                "tvname",
                holder.binding.tvProductName.text.toString()
            )
            updateProductActivityIntent.putExtra(
                "tvprice",
                holder.binding.tvProductPrice.text.toString()
            )
            updateProductActivityIntent.putExtra(
                "tvquantity",
                holder.binding.tvProductQuantity.text.toString()
            )
            updateProductActivityIntent.putExtra("productid", products[position].id)
            updateProductActivityIntent.putExtra("productbought", products[position].bought)
            holder.binding.root.context.startActivity(updateProductActivityIntent)
        }

        holder.binding.tvProductPrice.setOnClickListener {

            val updateProductActivityIntent =
                Intent(holder.binding.root.context, UpdateProductActivity::class.java)
            updateProductActivityIntent.putExtra(
                "tvname",
                holder.binding.tvProductName.text.toString()
            )
            updateProductActivityIntent.putExtra(
                "tvprice",
                holder.binding.tvProductPrice.text.toString()
            )
            updateProductActivityIntent.putExtra(
                "tvquantity",
                holder.binding.tvProductQuantity.text.toString()
            )
            updateProductActivityIntent.putExtra("productid", products[position].id)
            updateProductActivityIntent.putExtra("productbought", products[position].bought)
            holder.binding.root.context.startActivity(updateProductActivityIntent)
        }

        holder.binding.tvProductQuantity.setOnClickListener {

            val updateProductActivityIntent =
                Intent(holder.binding.root.context, UpdateProductActivity::class.java)
            updateProductActivityIntent.putExtra(
                "tvname",
                holder.binding.tvProductName.text.toString()
            )
            updateProductActivityIntent.putExtra(
                "tvprice",
                holder.binding.tvProductPrice.text.toString()
            )
            updateProductActivityIntent.putExtra(
                "tvquantity",
                holder.binding.tvProductQuantity.text.toString()
            )
            updateProductActivityIntent.putExtra("productid", products[position].id)
            updateProductActivityIntent.putExtra("productbought", products[position].bought)
            holder.binding.root.context.startActivity(updateProductActivityIntent)
        }


        //checkbutton
        holder.binding.cbProductBought.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                update(
                    Product(
                        id = products[position].id,
                        name = products[position].name,
                        price = products[position].price,
                        quantity = products[position].quantity,
                        bought = !products[position].bought
                    )
                )
            }
        }

        //delete button
        holder.binding.ibtDelete.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewmodel.delete(products[position])
            }
            Toast.makeText(
                holder.binding.root.context, "Usunięto ${products[position].name}",
                Toast.LENGTH_SHORT
            ).show()
        }

        //set text color and text size
        holder.binding.tvProductName.setTextColor(
            Color.parseColor(
                sp.getString(
                    "textcolor",
                    "#000000"
                )
            )
        )
        holder.binding.tvProductPrice.setTextColor(
            Color.parseColor(
                sp.getString(
                    "textcolor",
                    "#000000"
                )
            )
        )
        holder.binding.tvProductQuantity.setTextColor(
            Color.parseColor(
                sp.getString(
                    "textcolor",
                    "#000000"
                )
            )
        )
        holder.binding.tvProductName.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
        holder.binding.tvProductPrice.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())
        holder.binding.tvProductQuantity.setTextSize(sp.getString("textsizemi", "24")!!.toFloat())

        //tsum += products[position].price

/*        if (products[position] == products.last()) {
            Log.d("total pay : ", tsum.toString())
        }*/
    }

    override fun getItemCount(): Int = products.size

    fun dodaj(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            viewmodel.insert(product)
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }

    fun setProducts(dbproducts: List<Product>) {
        products = dbproducts
        notifyDataSetChanged()
    }

    fun update(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            viewmodel.update(product)
        }
//        viewmodel.update(product)
//        withContext(Dispatchers.Main) {
//            notifyDataSetChanged()
//        }
    }

}