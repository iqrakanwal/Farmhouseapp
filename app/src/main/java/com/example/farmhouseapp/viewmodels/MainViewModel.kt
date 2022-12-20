package com.example.farmhouseapp.viewmodels

import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.farmhouseapp.models.*
import com.example.farmhouseapp.repositories.MainRepository
import com.google.firestore.v1.StructuredQuery.Order

class MainViewModel(var repository: MainRepository) : ViewModel() {
    var userTile: MutableLiveData<String>? = MutableLiveData()
    var userType: MutableLiveData<String>? = MutableLiveData()


    var orderforDetail: Orders = Orders()
    var doctorsdeatail: Doctor = Doctor()
    var appointmentsdeatail: Appointments = Appointments()

    fun addUser(
        name: String,
        num: String,
        role: String,
        email: String,
        password: String,
        callback: (String) -> Unit
    ) {
        repository.addUser(name, num, role, email, password, callback)
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
        repository.placeOrder(order, callback)
    }

    fun getOrderFromBuyer(firstName: String?, list: (ArrayList<Orders>) -> Unit) {
        repository.getOrderFromBuyer(firstName, list)
    }

    fun getAllUsers(userName: String, list: (ArrayList<User>) -> Unit) {
        repository.getAllUser(userName, list)

    }

    fun getOrderfromSeller(firstName: String?, list: (ArrayList<Orders>) -> Unit) {
        repository.getOrderfromSeller(firstName, list)
    }

    fun setOrder(orders: Orders) {
        orderforDetail = orders
    }

    fun setDoctors(orders: Doctor) {
        doctorsdeatail = orders
    }


    fun getOrder(): Orders? {
        return orderforDetail;
    }

    fun getDoctor(): Doctor? {
        return doctorsdeatail;
    }

    fun updateOrder(order: String?, callback: (String) -> Unit) {
        repository?.updateOrder(order, callback)
    }

    fun getAllDoctors(userName: String?, callback: (java.util.ArrayList<Doctor?>) -> Unit) {
        repository.getAllDoctors(userName, callback)

    }

    fun takeApointment(s: Appointments, callback: (String) -> Unit) {
        repository.insertAppoint(s, callback)
    }

    fun getAppointment(s: String, list: (ArrayList<Appointments>) -> Unit) {
repository.getAppoimentForDoctor(s, list)
    }

    fun serAppointment(orders: Appointments) {
        appointmentsdeatail= orders
    }

    fun getAppointment():Appointments{
        return appointmentsdeatail

    }

    fun updateAppointment(s: String,  callback: (String) -> Unit) {
        repository?.updateAppointment(s, callback)
    }

}