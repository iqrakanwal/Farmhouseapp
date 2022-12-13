package com.example.farmmanagment.models

import java.util.Date

data class Order(var data:Date,
                 var farmerNo:String,
                 var buyerNo:String,
                 var animal:String  )