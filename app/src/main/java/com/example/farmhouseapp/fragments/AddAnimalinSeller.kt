package com.example.farmhouseapp.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.utils.Constants.Companion.farmid
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.fragment_add_animalin_seller.*
import kotlinx.android.synthetic.main.fragment_add_animals.*
import kotlinx.android.synthetic.main.fragment_add_farm_in_seller.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.IOException


class AddAnimalinSeller : Fragment() {

    private var progressDailog: ProgressDialog? = null



    private val adViewModel: MainViewModel by sharedViewModel()
    val PICK_IMAGE = 1
    var animalpath: String?= ""
    var animalFilePath: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_animalin_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDailog = ProgressDialog(requireContext())


        addimageseller.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        }
        addAnimalSeller.setOnClickListener {
            if (TextUtils.isEmpty(nameofanimal_et_seller.getText())) {
                nameofanimal_et_seller.setError("required")
                nameofanimal_et_seller.requestFocus()
            } else if (TextUtils.isEmpty(nameofbreed_et_seller.getText())) {
                nameofbreed_et_seller.setError("required")
                nameofbreed_et_seller.requestFocus()
            } else if (TextUtils.isEmpty(qunatityofanimal_et_seller.getText())) {
                qunatityofanimal_et_seller.setError("required")
                qunatityofanimal_et_seller.requestFocus()
            } else if (TextUtils.isEmpty(price_et_seller.getText())) {
                price_et_seller.setError("required")
                price_et_seller.requestFocus()
            } else {

                progressDailog?.show()


                uploadProfileImage(animalFilePath!!)


            }
        }

    }
    private fun Done(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        progressDailog?.dismiss()

        findNavController().navigate(R.id.action_addAnimalinSeller_to_showFarmFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {



            if (data == null || data.data == null) {
                return
            }
            animalFilePath = data.data
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(requireContext().contentResolver, animalFilePath)
                profile_image_seller.setImageBitmap(bitmap);
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


    private fun uploadProfileImage(filePath: Uri) {
        adViewModel.uploadImage(filePath, ::uploadedProfilePhoto, requireContext())
    }

    private fun uploadedProfilePhoto(s: String) {
        var animals = Animal()
        animals.name = nameofanimal_et_seller.text.toString()
        animals.catagory = nameofbreed_et_seller.text.toString()
        animals.farmName = farmid.name
        animals.price = price_et_seller.text.toString()
        animals.images =  s
        animals.quantity = qunatityofanimal_et_seller.text.toString()
        adViewModel.adAnimal(animals, ::Done)
    }


}