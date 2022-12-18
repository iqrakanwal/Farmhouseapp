package com.example.farmhouseapp.models

class Orders() {
    var farmName: String = " "
    var buyerName: String = ""
    var totalAmount: String = ""
    lateinit var animalid: Animal
    var ownerName: String = " "
    var orderstatus:String= ""

    fun Orders(farmName1: String, buyerName1: String, totalAnimal1: String, animal1: Animal, ownerName1:String, orderstatus1:String) {
        farmName = farmName1
        buyerName = buyerName1
        totalAmount = totalAnimal1
        animalid = animal1
        ownerName= ownerName1
        orderstatus= orderstatus1
    }


}
