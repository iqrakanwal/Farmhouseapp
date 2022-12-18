package com.example.farmhouseapp.ui
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_animals.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddAnimalActivty : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private val adViewModel: MainViewModel by viewModel()
    val PICK_IMAGE = 1
    lateinit var farmName: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_animal_activty)
        adViewModel.getAllFarms {
            farmName = arrayListOf()
            Toast.makeText(this, "${it.size}", Toast.LENGTH_SHORT).show()
            for (i in it) {
                farmName.add(i.name)
                Log.e("szdjsdkj", "${i.name}")
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item, farmName
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            farm.adapter = adapter
            farm.getSelectedItem().toString()
            //    farm
        }
        addimage.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        addAnimal.setOnClickListener {

            if (TextUtils.isEmpty(nameofanimal_et.getText())) {
                nameofanimal_et.setError("required")
                nameofanimal_et.requestFocus()
            } else if (TextUtils.isEmpty(nameofbreed_et.getText())) {
                nameofbreed_et.setError("required")
                nameofbreed_et.requestFocus()
            } else if (TextUtils.isEmpty(qunatityofanimal_et.getText())) {
                qunatityofanimal_et.setError("required")
                qunatityofanimal_et.requestFocus()
            } else if (TextUtils.isEmpty(price_et.getText())) {
                price_et.setError("required")
                price_et.requestFocus()
            } else {
                var animals = Animal()
                animals.name = nameofanimal_et.text.toString()
                animals.catagory = nameofbreed_et.text.toString()
                animals.farmName = farm.getSelectedItem().toString()
                animals.price = price_et.text.toString()
                animals.images = "no images"
                animals.quantity = qunatityofanimal_et.text.toString()
                adViewModel.adAnimal(animals, ::Done)
            }
        }
    }

    private fun Done(s: String) {
        Toast.makeText(this, "${s}", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, AnimalListActivity::class.java))
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE) {
            val selectedImage = data?.data
            Toast.makeText(this, "xjhjhfdfh${selectedImage}", Toast.LENGTH_SHORT).show()
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = getContentResolver().query(
                Uri.parse(selectedImage.toString()),
                filePathColumn, null, null, null
            )
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val picturePath = columnIndex?.let { cursor?.getString(it) }
            cursor?.close()
            Toast.makeText(this, "xjhjhfdfh${picturePath}", Toast.LENGTH_SHORT).show()
            profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath))
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.e("selected", "${p2}")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {


    }
}