package com.example.farmhouseapp.models

 class User{
     var name: String= ""
     var password: String = ""
     var mobile_num: String= ""
     var role:String= ""
     var email:String = ""
     fun User(name1:String, password1:String, mobileno1:String, role1:String, email1:String){
         name=name1
         password=password1
         mobile_num=mobileno1
         role=role1
         email=email1
     }


 }