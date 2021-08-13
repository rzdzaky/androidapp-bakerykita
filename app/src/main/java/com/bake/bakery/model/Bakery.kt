package com.bake.bakery.model

data class Bakery(
    val id: Int,
    val name: String,
    val image: String,
    val price: Int,
    var countInCart: Int = 0
)