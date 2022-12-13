package com.example.farmhouseapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.farmhouseapp.AnimalAdaptor
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Animal
import kotlinx.android.synthetic.main.activity_main_screen.*


class MainScreen : AppCompatActivity() {
    lateinit var animalAdaptor: AnimalAdaptor
    lateinit var arrayList: ArrayList<Animal>
    lateinit var layout: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_screen)
        layout = LinearLayoutManager(this)
        arrayList = arrayListOf()
     /*   arrayList.add(Animal("Asian Cat", "Australorp","bielefelder", "12 PKR", "Alhuda cattle farms", "2"))
        arrayList.add(Animal("Hansel and Gretel", "Barnvelder","breedaustralorps", "15 PKR", "Awan Poultry farming", "4"))
        arrayList.add(Animal("Laverne and Shirley", "Bielefelder","breedbarnevelder", "500 PKR", "Fancy Hens Point","6"))
        arrayList.add(Animal("Lucy and Desi", "Black Star / Red Star","breedbrahma", "100 PKR", "Alhuda Farm","7"))
        arrayList.add(Animal("Romeo and Juliet", "Brahma","breedchantecler", "200 PKR", "Awan Poultry farming","8"))
        arrayList.add(Animal("Salt and Pepper", "Buckeye","breedstarred", "250 PKR", "Fancy Hens Point", "10"))*/
        animalAdaptor = AnimalAdaptor(this, arrayList)
        animals.layoutManager = layout
        val dividerItemDecoration = DividerItemDecoration(
            animals.getContext(),
            layout.getOrientation()
        )
        animals.addItemDecoration(dividerItemDecoration)
        animals.adapter = animalAdaptor



    }


}