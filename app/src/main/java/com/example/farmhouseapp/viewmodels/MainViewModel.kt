package com.example.farmhouseapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.repositories.MainRepository

class MainViewModel(var repository: MainRepository) : ViewModel() {

    var userTile: MutableLiveData<String>? = MutableLiveData()
    var userType: MutableLiveData<String>? = MutableLiveData()

    fun addUser(name: String, num: String, role: String, email: String, password: String) {
        repository.addUser(name, num, role, email, password)
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


    fun uploadImage(filePath: String, callback: (String) -> Unit, context: Context) {
        repository.uploadImage(filePath, callback, context)
    }

    fun getAllFarms(callback: (ArrayList<FarmName>) -> Unit) {
        repository.getAllFarms {
            callback(it)
        }


    }
}