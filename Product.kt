package com.example.mini_project_1

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity
data class Product(
    //@PrimaryKey(autoGenerate = true) val id: Long = 0,
    var id: String,
    var name: String,
    var price: String,
    var quantity: Long,
    var bought: Boolean = false
)