package com.example.farmhouseapp.models

class Doctor {
    var name: String = ""
    var designation: String = " "
    var profilepicture: String = ""
    var email: String = ""
    var hospital: String = ""
    var contactnum: String = ""
    var slot: String = ""
    fun Doctor(
        name1: String,
        designation1: String,
        profilepicture1: String,
        email1: String,
        hospital1: String,
        contactnum1: String, slot1:String
    ) {
        name = name1
        designation = designation1
        profilepicture = profilepicture1
        email = email1
        hospital = hospital1
        contactnum = contactnum1
        slot= slot1
    }


}

