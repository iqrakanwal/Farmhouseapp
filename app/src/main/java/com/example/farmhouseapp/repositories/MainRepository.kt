package com.example.farmhouseapp.repositories

import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.farmhouseapp.Possibilities
import com.example.farmhouseapp.models.*
import com.example.farmhouseapp.utils.Constants
import com.example.farmhouseapp.utils.Constants.Companion.animals
import com.example.farmhouseapp.utils.Constants.Companion.doctors
import com.example.farmhouseapp.utils.Constants.Companion.farms
import com.example.farmhouseapp.utils.Constants.Companion.orders
import com.example.farmhouseapp.utils.Constants.Companion.users
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseError
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*


class MainRepository(var context: Context) {
    private var mFirebaseInstance: FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var mFirebaseDatabase: DatabaseReference? = mFirebaseInstance?.getReference()
    private lateinit var farmsd: ArrayList<FarmName>
    private lateinit var animalslist: ArrayList<Animal?>


    var storage: FirebaseStorage? = FirebaseStorage.getInstance();
    var storageReference: StorageReference? = storage?.getReference();
    fun addUser(
        name: String,
        num: String,
        role: String,
        email: String,
        password: String,
        callback: (String) -> Unit
    ) {
        var user = User()
        user.name = name
        user.mobile_num = num
        user.role = role
        user.email = email
        user.password = Constants.adminPassword
        mFirebaseDatabase?.ref?.child("${users}")?.push()?.setValue(user)?.addOnSuccessListener {
            Log.e("addUser", "add")
            callback("${Possibilities.SUCCESS}")

            // startActivity(Intent(this@SignUpForm, MainScreen::class.java))
            // finish()
        }?.addOnFailureListener(object : OnFailureListener {
            override fun onFailure(p0: Exception) {
                callback("${Possibilities.FAILED}")
                Log.e("addUser", "add{${p0.message}}")

            }

        });


    }


    fun deleteUser(name: String, num: String, role: String, email: String, password: String) {


    }


    fun addAnimal(animals1: Animal, callback: (String) -> Unit) {
        mFirebaseDatabase?.ref?.child("${animals}")?.push()?.setValue(animals1)
            ?.addOnSuccessListener {
                callback("done")
            }?.addOnFailureListener {
                callback("failed")
            }
    }


    fun deleteAnimal(animals: Animal) {


    }


    fun addFarm(farmName: FarmName) {


    }

    fun insertFarm(farmName: FarmName, callback: (String) -> Unit) {
        mFirebaseDatabase?.ref?.child("${farms}")?.push()?.setValue(farmName)
            ?.addOnSuccessListener {
                callback("done")
            }?.addOnFailureListener {
                callback("failed")
            }
    }


    fun getAllFarms(callback: (ArrayList<FarmName>) -> Unit) {
        farmsd = arrayListOf()
        mFirebaseDatabase?.ref?.child("${farms}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: FarmName = it.getValue(FarmName::class.java)!!
                farmsd.add(day)
            }
            // Log.e("cvjlxkcvjl", "${it}")

            // val day: FarmName = it.getValue(FarmName::class.java)!!
            //    farms?.add(day)
            callback(farmsd)


        }


        /*   mFirebaseDatabase?.ref?.child("Farms")?.addChildEventListener(object : ChildEventListener {
               override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

                   Log.e("cvjlxkcvjl", "${snapshot.toString()}")

                   val day: FarmName? = snapshot.getValue(FarmName::class.java)
                   farms?.add(day!!)
                   callback(farms!!)
               }

               override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {


               }

               override fun onChildRemoved(snapshot: DataSnapshot) {


               }

               override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

               }

               override fun onCancelled(error: DatabaseError) {

               }


           })
   */

    }

    fun uploadImage(bitmap: Uri, callback: (String) -> Unit, context: Context) {
        Log.e("referece", "${storageReference.toString()}")
        if (bitmap != null) {
            if (bitmap != null) {
                val ref = storageReference?.child("uploads/" + UUID.randomUUID().toString())
                val uploadTask = ref?.putFile(bitmap!!)

                val urlTask =
                    uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>>
                    { task ->
                        if (!task.isSuccessful) {
                            task.exception?.let {
                                throw it
                            }
                        }
                        return@Continuation ref.downloadUrl
                    })?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val downloadUri = task.result
                            callback("${downloadUri}")
                            //Toast.makeText(this,"${downloadUri}", Toast.LENGTH_SHORT).show()
                            //addUploadRecordToDb(downloadUri.toString())
                        } else {
                            // Handle failures
                        }
                    }
                        ?.addOnFailureListener {
                            callback("${Possibilities.FAILED}")
                        }

            }

        }

    }

    fun getSellerFarm(user: String?, callback: (FarmName) -> Unit) {
        var array: ArrayList<FarmName> = arrayListOf()
        var farmName: FarmName
        mFirebaseDatabase?.child("$farms")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: FarmName = it.getValue(FarmName::class.java)!!
                Log.e("sdffdjdfkj", "${day.name}")
                if (day.farmOwner == user) {
                    array.add(day)
                    callback(day)
                }


            }


        }?.addOnFailureListener {


        }
    }

    fun getAnimalOfFarm(farmName: String, list: (ArrayList<Animal?>) -> Unit) {
        animalslist = arrayListOf()
        mFirebaseDatabase?.ref?.child("${animals}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: Animal = it.getValue(Animal::class.java)!!
                if (day.farmName == farmName) {
                    animalslist.add(day)

                }
            }
            list(animalslist)
        }
    }


    fun getAllAnimalS(list: (ArrayList<Animal>) -> Unit) {
        var animals: ArrayList<Animal> = arrayListOf()
        mFirebaseDatabase?.ref?.child("${Constants.animals}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: Animal = it.getValue(Animal::class.java)!!
                animals.add(day)

            }
            list(animals)
        }


    }

    fun getFarmFromAnimal(animal: String, callback: (FarmName) -> Unit) {
        mFirebaseDatabase?.ref?.child("${farms}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: FarmName = it.getValue(FarmName::class.java)!!
                if (day.name == animal) {
                    callback(day)
                }
            }


            // Log.e("cvjlxkcvjl", "${it}")

            // val day: FarmName = it.getValue(FarmName::class.java)!!
            //    farms?.add(day)


        }
    }

    fun addDoctor(doctor: Doctor, callback: (String) -> Unit) {
        mFirebaseDatabase?.ref?.child("${doctors}")?.push()?.setValue(doctor)
            ?.addOnSuccessListener {
                callback("done")
            }?.addOnFailureListener {
                callback("failed")
            }
    }

    fun getDoctor(
        userEmail: String?,
        firstName: String?,
        callback: (Doctor?) -> Unit
    ) {
        mFirebaseDatabase?.ref?.child("${doctors}")?.get()?.addOnSuccessListener {
            if (it.value != null) {
                for (it in it.getChildren()) {
                    val day: Doctor = it.getValue(Doctor::class.java)!!
                    if ((day.name == firstName) && (day.email == userEmail)) {
                        callback(day)
                    }
                }
            } else {
                callback(null)
            }


        }?.addOnFailureListener {
            callback(null)

        }


    }

    fun placeOrder(order: Orders, callback: (String) -> Unit) {
        mFirebaseDatabase?.ref?.child("${orders}")?.push()?.setValue(order)
            ?.addOnSuccessListener {
                callback("${Possibilities.SUCCESS}")
            }?.addOnFailureListener {
                callback("${Possibilities.FAILED}")
            }

    }

    fun getAllOrders(list: (ArrayList<Orders>) -> Unit) {
        var orders: ArrayList<Orders> = arrayListOf()
        mFirebaseDatabase?.ref?.child("${Constants.orders}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: Orders = it.getValue(Orders::class.java)!!
                orders.add(day)
            }
            list(orders)
        }
    }

    fun getOrderFromBuyer(firstName: String?, list: (ArrayList<Orders>) -> Unit) {
        var orders: ArrayList<Orders> = arrayListOf()
        mFirebaseDatabase?.ref?.child("${Constants.orders}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: Orders = it.getValue(Orders::class.java)!!
                if (day.buyerName == firstName) {
                    orders.add(day)
                }
            }
            list(orders)
        }


    }

/*
    fun getAllUsers(list: (ArrayList<User>) -> Unit) {
        var users: ArrayList<User> = arrayListOf()
        mFirebaseDatabase?.ref?.child("${Constants.users}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: User = it.getValue(User::class.java)!!
                users.add(day)
            }
            list(users)
        }
    }
*/

    fun getAllUser(userName: String, list: (ArrayList<User>) -> Unit) {
        var users: ArrayList<User> = arrayListOf()
        mFirebaseDatabase?.ref?.child("${Constants.users}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: User = it.getValue(User::class.java)!!
                if (day.role == userName) {
                    users.add(day)
                }
            }
            list(users)
        }
    }

    fun getOrderfromSeller(firstName: String?, list: (ArrayList<Orders>) -> Unit) {
        var orders: ArrayList<Orders> = arrayListOf()
        mFirebaseDatabase?.ref?.child("${Constants.orders}")?.get()?.addOnSuccessListener {
            for (it in it.getChildren()) {
                val day: Orders = it.getValue(Orders::class.java)!!
                if (day.ownerName == firstName) {
                    orders.add(day)
                }
            }
            list(orders)
        }
    }

    fun updateOrder(order: String?, callback: (String) -> Unit) {
        mFirebaseDatabase?.ref?.child("${orders}")
            ?.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {


                    if (dataSnapshot.exists()) {
                        for (datas in dataSnapshot.children) {
                            if (datas.hasChild("orderstatus")) {
                                mFirebaseDatabase?.ref?.child("${orders}")?.child(datas.getKey()!!)
                                    ?.child("orderstatus")
                                    ?.setValue(order);
                            }




                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }

                fun onCancelled(firebaseError: FirebaseError?) {}
            })

    }

}


