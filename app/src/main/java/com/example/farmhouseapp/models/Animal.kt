package com.example.farmhouseapp.models

class Animal {
    var name: String = ""
    var catagory: String = " "
    var images: String = ""
    var price: String = ""
    var farmName: String = ""
    var quantity: String = ""

    fun Animal(
        name1: String,
        catagory1: String,
        images1: String,
        price1: String,
        farmHouse1: String,
        quantity1: String
    ) {
        name = name1
        catagory = catagory1
        images = images1
        price = price1
        farmName = farmHouse1
        quantity= quantity1
    }


}

