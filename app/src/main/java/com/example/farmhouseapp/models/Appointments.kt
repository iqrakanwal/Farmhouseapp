package com.example.farmhouseapp.models

class Appointments {
    lateinit var doctorname: Doctor
    lateinit var sellername: String
    var appointmentstatus: String = ""
    var slot: String = ""


    fun Appointments(doctorname1: Doctor, sellername1: String, slot1: String) {
        doctorname = doctorname1
        sellername = sellername1
        slot = slot1

    }

}