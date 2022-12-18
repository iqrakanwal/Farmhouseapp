package com.example.farmhouseapp.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.Doctor
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.models.Orders
import com.example.farmhouseapp.repositories.MainRepository

class MainViewModel(var repository: MainRepository) : ViewModel() {
    var userTile: MutableLiveData<String>? = MutableLiveData()
    var userType: MutableLiveData<String>? = MutableLiveData()

    lateinit var animalObject: Animal
    lateinit var farmObject: FarmName
    fun addUser(name: String, num: String, role: String, email: String, password: String) {
        repository.addUser(name, num, role, email, password)
    }

    fun setAnimal(animal1: Animal) {
        animalObject = animal1
    }

    fun setFarm(farmname1: FarmName) {
        farmObject = farmname1
    }

    fun getAnimal(): Animal {
        return animalObject
    }

    fun getFarm(): FarmName {
        return farmObject
    }


    fun setTitle(title: String) {
        userTile?.value = title
    }

    fun setUserType(title: String) {
        userType?.value = title
    }

    fun getTitle(): String? {
        return userTile?.value
    }

    fun getUserType(): String? {
        return userType?.value
    }

    fun addFarm(farmName: FarmName, callback: (String) -> Unit) {
        repository.insertFarm(farmName, callback)
    }


    fun adAnimal(animal: Animal, callback: (String) -> Unit) {
        repository.addAnimal(animal, callback)

    }


    fun uploadImage(filePath: Uri, callback: (String) -> Unit, context: Context) {
        repository.uploadImage(filePath, callback, context)
    }

    fun getAllFarms(callback: (ArrayList<FarmName>) -> Unit) {
        repository.getAllFarms {
            callback(it)
        }


    }

    fun getSellerFarm(user: String?, callback: (FarmName) -> Unit) {
        repository.getSellerFarm(user, callback)
    }

    fun getAnimalsofFarm(farmName: String, list: (java.util.ArrayList<Animal?>) -> Unit) {
        repository.getAnimalOfFarm(farmName, list)
    }

    fun getAllAnimals(list: (ArrayList<Animal>) -> Unit) {
        repository.getAllAnimalS(list)
    }
    fun getAllOrders(list: (ArrayList<Orders>) -> Unit) {
        repository.getAllOrders(list)
    }

    fun getFarmFromAnimal(animal: String, callback: (FarmName) -> Unit) {
        repository.getFarmFromAnimal(animal, callback)
    }

    fun adDoctor(doctor: Doctor, callback: (String) -> Unit) {
        repository.addDoctor(doctor, callback)
    }

    fun getDoctor(
        userEmail: String?,
        firstTime: String?,
        callback: (Doctor?) -> Unit
    ) {


        repository.getDoctor(
            userEmail,
            firstTime,
            callback
        )
    }

    fun placeOrder(order: Orders, callback: (String) -> Unit) {
repository.placeOrder(order,callback )
    }


}