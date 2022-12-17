package com.example.mini_project_1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.FirebaseUserMetadata
import com.google.firebase.database.FirebaseDatabase

class ProductViewModel(app: Application) : AndroidViewModel(app) {

    private val repo: ProductRepository
    var firebaseDatabase: FirebaseDatabase
    var firebaseUser: FirebaseUser
    var firebaseAuth: FirebaseAuth
    val allProducts: MutableLiveData<HashMap<String, Product>>//LiveData<List<Product>>

    init {
        firebaseDatabase = FirebaseDatabase.getInstance()
        //firebaseUser = FirebaseAuth.getInstance().currentUser!!
        //val productDAO = ProductDB.getDatabase(app.applicationContext)!!.getProductDao()
        firebaseAuth = FirebaseAuth.getInstance()
        repo = ProductRepository(firebaseDatabase, firebaseAuth)
        allProducts = repo.allProducts
    }

    fun insert(product: Product) = repo.insert(product)

    fun update(product: Product) = repo.update(product)

    fun delete(product: Product) = repo.delete(product)

    fun deleteAll() = repo.deleteAll()


}
