package com.example.farmhouseapp.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_animalin_seller.*
import kotlinx.android.synthetic.main.fragment_add_animals.*
import kotlinx.android.synthetic.main.fragment_add_animals.nameofanimal_et
import kotlinx.android.synthetic.main.fragment_add_animals.view.*
import kotlinx.android.synthetic.main.fragment_add_farm_house.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException


class AddAnimals : Fragment(), AdapterView.OnItemSelectedListener {
    private val adViewModel: MainViewModel by sharedViewModel()
    val PICK_IMAGE = 1
    private var progressDailog: ProgressDialog? = null
    var animalFilePath: Uri? = null

    lateinit var farmName: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            com.example.farmhouseapp.R.layout.fragment_add_animals,
            container,
            false
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDailog = ProgressDialog(requireContext())

        adViewModel.getAllFarms {
            farmName = arrayListOf()
            Toast.makeText(requireContext(), "${it.size}", Toast.LENGTH_SHORT).show()
            for (i in it) {
                farmName.add(i.name)
                Log.e("szdjsdkj", "${i.name}")
            }
            val adapter: ArrayAdapter<String> = ArrayAdapter<String>(
                requireContext(),
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
                progressDailog?.show()
                uploadProfileImage(animalFilePath!!)


            }
        }
    }
    private fun uploadProfileImage(filePath: Uri) {
        adViewModel.uploadImage(filePath, ::uploadedProfilePhoto, requireContext())
    }

    private fun uploadedProfilePhoto(s: String) {
        var animals = Animal()
        animals.name = nameofanimal_et.text.toString()
        animals.catagory = nameofbreed_et.text.toString()
        animals.farmName = farm.getSelectedItem().toString()
        animals.price = price_et.text.toString()
        animals.images = s
        animals.quantity = qunatityofanimal_et.text.toString()
        adViewModel.adAnimal(animals, ::Done)
    }

    private fun Done(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        progressDailog?.dismiss()
        findNavController().navigate(R.id.action_addAnimals_to_adminMainScreen)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            if (requestCode == PICK_IMAGE) {



                if (data == null || data.data == null) {
                    return
                }
                animalFilePath = data.data
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(requireContext().contentResolver, animalFilePath)
                    profile_image.setImageBitmap(bitmap);
                    // uploadImage(coverFilePath!!)

                } catch (e: IOException) {
                    e.printStackTrace()
                }

                /* val selectedImage = data?.data
                 Toast.makeText(requireContext(), "xjhjhfdfh${selectedImage}", Toast.LENGTH_SHORT).show()
                 val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
                 val cursor: Cursor? = requireContext().getContentResolver().query(
                     Uri.parse(selectedImage.toString()),
                     filePathColumn, null, null, null
                 )
                 cursor?.moveToFirst()
                 val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                 val picturePath = columnIndex?.let { cursor?.getString(it) }
                 cursor?.close()*/
                /*  Toast.makeText(requireContext(), "xjhjhfdfh${picturePath}", Toast.LENGTH_SHORT).show()
                  profile_image.setImageBitmap(BitmapFactory.decodeFile(picturePath))*/
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.e("selected", "${p2}")
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {


    }


}