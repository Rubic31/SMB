package com.example.mini_project_1

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

class ProductRepository(/*private val productDAO: ProductDAO*/ private val firebaseDatabase: FirebaseDatabase, user: FirebaseUser) {

    val allProducts: MutableLiveData<HashMap<String, Product>> =
        MutableLiveData<HashMap<String, Product>>().also {
            it.value = HashMap<String, Product>()
        }

    init {
        //val ref = firebaseDatabase.getReference("users/"+"zbyszek"/*user.uid*/)
        //val people_ref = ref.child("people")
        firebaseDatabase.getReference(/*"users/"+"zbyszek/"*//*user.uid*/ /*+*/ "products")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                    val product = Product(
                        id = snapshot.ref.key as String,
                        name = snapshot.child("name").value as String,
                        price = snapshot.child("price").value as String,
                        quantity = snapshot.child("quantity").value as Long,
                        bought = snapshot.child("bought").value as Boolean
                    )
                    allProducts.value?.put(product.id, product)
                    allProducts.postValue(allProducts.value)
                    Log.i("productAdd", "Product added: ${allProducts.value.toString()}")
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                    val product = Product(
                        id = snapshot.child("id").value as String,
                        name = snapshot.child("name").value as String,
                        price = snapshot.child("price").value as String,
                        quantity = snapshot.child("quantity").value as Long,
                        bought = snapshot.child("bought").value as Boolean
                    )
                    allProducts.value?.set(product.id, product)
                    allProducts.postValue(allProducts.value)
                    Log.i("productChange", "Product updated: ${product.id}")
                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val product = Product(
                        id = snapshot.ref.key as String,
                        name = snapshot.child("name").value as String,
                        price = snapshot.child("price").value as String,
                        quantity = snapshot.child("quantity").value as Long,
                        bought = snapshot.child("bought").value as Boolean
                    )
                    Log.i("removed", product.toString())
                    allProducts.value?.remove(product.id)
                    allProducts.postValue(allProducts.value)
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    //TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }

            })

    }

    fun insert(product: Product/*, firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()*/) {
        firebaseDatabase.getReference("products").push().also {
            product.id = it.ref.key!!
            it.setValue(product)
        }
    }

    fun update(product: Product) {
        var ref = firebaseDatabase.getReference("products/${product.id}")
        ref.child("name").setValue(product.name)
        ref.child("price").setValue(product.price)
        ref.child("quantity").setValue(product.quantity)
        ref.child("bought").setValue(product.bought)
    }

    fun delete(product: Product) =
        firebaseDatabase.getReference("products/${product.id}").removeValue()

    fun deleteAll() = firebaseDatabase.getReference("products").removeValue()

//    val allProducts = productDAO.getProducts()
//
//    suspend fun insert(product: Product) = productDAO.insert(product)
//
//    suspend fun update(product: Product) = productDAO.update(product)
//
//    suspend fun delete(product: Product) = productDAO.delete(product)
//
//    suspend fun deleteAll() = productDAO.deleteAll()

}