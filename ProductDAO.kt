package com.example.mini_project_1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

//@Dao
interface ProductDAO {

    //@Query("SELECT * FROM product")
    fun getProducts(): LiveData<List<Product>>

    //@Insert
    fun insert(product: Product)

    //@Update
    fun update(product: Product)

    //@Delete
    fun delete(product: Product)

    //@Query("DELETE FROM product")
    fun deleteAll()
}