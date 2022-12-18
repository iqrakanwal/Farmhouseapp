package com.example.farmhouseapp.models

import android.media.AudioProfile
import android.media.Image

class FarmName() {
    var name: String = ""
    var location: String = ""
    var profileImages: String = ""
    var coverProfile: String = ""
    var phoneNo: String = ""
    var farmOwner: String = ""

    fun FarmName(
        name1: String,
        location1: String,
        profileImage1: String,
        coverProfile1: String,
        phoneNo1: String,
        farmOwner1:String
    ) {
        name = name1
        location = location1
        profileImages = profileImage1
        coverProfile = coverProfile1
        phoneNo = phoneNo1


    }


}






