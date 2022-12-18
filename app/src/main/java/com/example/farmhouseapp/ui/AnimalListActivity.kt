package com.example.farmhouseapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Animal
import kotlinx.android.synthetic.main.activity_animal_list.*
import kotlinx.android.synthetic.main.activity_main_screen.*

class AnimalListActivity : AppCompatActivity() {
    lateinit var animalAdaptor: AnimalAdaptor
    lateinit var arrayList: java.util.ArrayList<Animal?>
    lateinit var layout: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_list)
        layout = LinearLayoutManager(this)
        arrayList = arrayListOf()
        var animal = Animal()
        animal.name = "Asian Cat"
        animal.catagory = "Australorp"
        animal.images = "bielefelder"
        animal.price = "12 PKR"
        animal.farmName = "Alhuda cattle farms"
        animal.quantity = "2"
        arrayList.add(
            animal
        )
        /*arrayList.add(
            Animal(
                "Hansel and Gretel",
                "Barnvelder",
                "breedaustralorps",
                "15 PKR",
                "Awan Poultry farming",
                "4"
            )
        )
        arrayList.add(
            Animal(
                "Laverne and Shirley",
                "Bielefelder",
                "breedbarnevelder",
                "500 PKR",
                "Fancy Hens Point",
                "6"
            )
        )
        arrayList.add(
            Animal(
                "Lucy and Desi",
                "Black Star / Red Star",
                "breedbrahma",
                "100 PKR",
                "Alhuda Farm",
                "7"
            )
        )
        arrayList.add(
            Animal(
                "Romeo and Juliet",
                "Brahma",
                "breedchantecler",
                "200 PKR",
                "Awan Poultry farming",
                "8"
            )
        )
        arrayList.add(
            Animal(
                "Salt and Pepper",
                "Buckeye",
                "breedstarred",
                "250 PKR",
                "Fancy Hens Point",
                "10"
            )
        )*/
        animalAdaptor = AnimalAdaptor(this, arrayList, ::itemSelected)
        animals.layoutManager = layout
        val dividerItemDecoration = DividerItemDecoration(
            animals.getContext(),
            layout.getOrientation()
        )
        animals.addItemDecoration(dividerItemDecoration)
        animals.adapter = animalAdaptor
    }

    private fun itemSelected(animal: Animal) {

    }
}