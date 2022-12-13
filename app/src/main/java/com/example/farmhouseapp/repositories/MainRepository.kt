package com.example.farmhouseapp.repositories

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.util.Log
import com.example.farmhouseapp.Possibilities
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.models.User
import com.example.farmhouseapp.utils.Constants
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class MainRepository(var context: Context) {
    private var mFirebaseInstance: FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var mFirebaseDatabase: DatabaseReference? = mFirebaseInstance?.getReference()
    private lateinit var farms: ArrayList<FarmName>


    var storage: FirebaseStorage? = FirebaseStorage.getInstance();
    var storageReference: StorageReference? = storage?.getReference();
    fun addUser(name: String, num: String, role: String, email: String, password: String) {
        var user = User()
        user.name = name
        user.mobile_num = num
        user.role = role
        user.email = email
        user.password = Constants.adminPassword
        mFirebaseDatabase?.ref?.child("user")?.push()?.setValue(user)?.addOnSuccessListener {
            Log.e("addUser", "add")
            // startActivity(Intent(this@SignUpForm, MainScreen::class.java))
            // finish()
        }?.addOnFailureListener(object : OnFailureListener {
            override fun onFailure(p0: Exception) {
                Log.e("addUser", "add{${p0.message}}")

            }

        });


    }


    fun deleteUser(name: String, num: String, role: String, email: String, password: String) {


    }


    fun addAnimal(animals: Animal, callback: (String) -> Unit) {
        mFirebaseDatabase?.ref?.child("Animals")?.push()?.setValue(animals)
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
        mFirebaseDatabase?.ref?.child("Farms")?.push()?.setValue(farmName)
            ?.addOnSuccessListener {
                callback("done")
            }?.addOnFailureListener {
                callback("failed")
            }
    }


    fun getAllFarms(callback: (ArrayList<FarmName>) -> Unit) {
        farms = arrayListOf()
        mFirebaseDatabase?.ref?.child("Farms")?.get()?.addOnSuccessListener {
            for ( it in it.getChildren()) {
                val day: FarmName = it.getValue(FarmName::class.java)!!
                farms.add(day)
            }
           // Log.e("cvjlxkcvjl", "${it}")

           // val day: FarmName = it.getValue(FarmName::class.java)!!
        //    farms?.add(day)
            callback(farms)


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

    fun uploadImage(filePath: String, callback: (String) -> Unit, context: Context) {
        if (filePath != null) {
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            val ref: StorageReference? =
                storageReference?.child("images/mountains.jpg")
            ref?.putFile(Uri.parse(filePath))
                ?.addOnSuccessListener {
                    callback("${Possibilities.SUCCESS}")
                    progressDialog.dismiss()
                }
                ?.addOnFailureListener { e ->
                    progressDialog.dismiss()
                    callback("${Possibilities.SUCCESS}")

                }
                ?.addOnProgressListener { taskSnapshot ->
                    val progress = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot
                        .totalByteCount
                    progressDialog.setMessage("Uploaded " + progress.toInt() + "%")
                }
        }
    }


}


