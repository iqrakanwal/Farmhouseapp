package com.example.farmhouseapp.fragments

import android.app.ProgressDialog
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.R
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.viewmodels.MainViewModel
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_add_farm_house.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.*


class AddFarmHouse : Fragment() {
    val PICK_IMAGE = 1
    val PROFILE_PICK_IMAGE = 2
    lateinit var coverpath: String
    lateinit var profileId: String

    private val adViewModel: MainViewModel by sharedViewModel()
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
        return inflater.inflate(R.layout.fragment_add_farm_house, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        df.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)

        }



        changeprofilepicture.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PROFILE_PICK_IMAGE
            )

        }

        addFarm.setOnClickListener {
            if (TextUtils.isEmpty(nameofanimal_et.getText())) {
                nameofanimal_et.setError("required")
                nameofanimal_et.requestFocus()
            } else if (TextUtils.isEmpty(locations_et.getText())) {
                locations_et.setError("required")
                locations_et.requestFocus()
            } else if (TextUtils.isEmpty(phone_et_farm.getText())) {
                phone_et_farm.setError("required")
                phone_et_farm.requestFocus()
            } else {
                var farmName = FarmName()
                farmName.name = nameofanimal_et.text.toString()
                farmName.location = locations_et.text.toString()
                farmName.phoneNo = phone_et_farm.text.toString()
                farmName.coverProfile = "No image"
                farmName.profileImages = "No image"
                adViewModel.addFarm(farmName, ::Done)
            }


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            val selectedImage = data?.data
            Toast.makeText(requireContext(), "xjhjhfdfh${selectedImage}", Toast.LENGTH_SHORT).show()
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = requireContext().getContentResolver().query(
                Uri.parse(selectedImage.toString()),
                filePathColumn, null, null, null
            )
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val picturePath = columnIndex?.let { cursor?.getString(it) }
            coverpath = picturePath.toString()

            uploadImage(coverpath.toString())
            cursor?.close()
            Toast.makeText(requireContext(), "xjhjhfdfh${picturePath}", Toast.LENGTH_SHORT).show()
            cover.setImageBitmap(BitmapFactory.decodeFile(picturePath))

            /* Glide
                 .with(requireContext())
                 .load(selectedImagePath)
                 // .load(Uri.parse("file:///android_asset/images/images_one.png"))
                 .into(profile_image)*/
        } else if (requestCode == PROFILE_PICK_IMAGE) {
            val selectedImage = data?.data
            Toast.makeText(requireContext(), "xjhjhfdfh${selectedImage}", Toast.LENGTH_SHORT).show()
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
            val cursor: Cursor? = requireContext().getContentResolver().query(
                Uri.parse(selectedImage.toString()),
                filePathColumn, null, null, null
            )
            cursor?.moveToFirst()
            val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
            val picturePath = columnIndex?.let { cursor?.getString(it) }
            cursor?.close()
            profileId = picturePath.toString()
            uploadImage(profileId.toString())

            Toast.makeText(requireContext(), "xjhjhfdfh${picturePath}", Toast.LENGTH_SHORT).show()
            profileimagefarm.setImageBitmap(BitmapFactory.decodeFile(profileId))

            /* Glide
                 .with(requireContext())
                 .load(selectedImagePath)
                 // .load(Uri.parse("file:///android_asset/images/images_one.png"))
                 .into(profile_image)*/

        }
    }

    private fun Done(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        nameofanimal_et.getText()?.clear();
        locations_et.getText()?.clear();
        phone_et_farm.getText()?.clear();
        findNavController().navigate(R.id.action_addFarmHouse_to_adminMainScreen)
    }


    private fun uploadImage(filePath: String) {
        adViewModel.uploadImage(filePath, ::ImageUploaded, requireContext())
    }

    private fun ImageUploaded(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
    }
}