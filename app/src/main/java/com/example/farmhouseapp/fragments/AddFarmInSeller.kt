package com.example.farmhouseapp.fragments

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.example.farmhouseapp.models.FarmName
import com.example.farmhouseapp.ui.FirstScreen.Companion.userAccount
import com.example.farmhouseapp.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_tactivity.*
import kotlinx.android.synthetic.main.fragment_add_farm_in_seller.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.io.File
import java.io.IOException


class AddFarmInSeller : Fragment() {
    val PICK_IMAGE = 1
    val PROFILE_PICK_IMAGE = 2
    var coverpath: String? = ""
    private var progressDailog: ProgressDialog? = null
    var coverFilePath: Uri? = null
    var profileFilePath: Uri? = null
    private val adViewModel: MainViewModel by sharedViewModel()
    var profileId: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_farm_in_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDailog = ProgressDialog(requireContext())

        dfseller.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)

        }

        profileimagefarmseller.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select Picture"),
                PROFILE_PICK_IMAGE
            )

        }

        addFarmSeller.setOnClickListener {
            if (TextUtils.isEmpty(nameofarm_et_seller.getText())) {
                nameofarm_et_seller.setError("required")
                nameofarm_et_seller.requestFocus()
            } else if (TextUtils.isEmpty(locations_et_seller.getText())) {
                locations_et_seller.setError("required")
                locations_et_seller.requestFocus()
            } else if (TextUtils.isEmpty(phone_et_farm_seller.getText())) {
                phone_et_farm_seller.setError("required")
                phone_et_farm_seller.requestFocus()
            } else {
                progressDailog?.show()
                uploadCoverPhotoImage(coverFilePath!!)
                Handler().postDelayed({
                }, 2000)
            }


        }


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {

            if (data == null || data.data == null) {
                return
            }
            coverFilePath = data.data
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        coverFilePath
                    )
                coverseller.setImageBitmap(bitmap);
                // uploadImage(coverFilePath!!)

            } catch (e: IOException) {
                e.printStackTrace()
            }


        } else if (requestCode == PROFILE_PICK_IMAGE) {

            if (data == null || data.data == null) {
                return
            }
            profileFilePath = data.data
            try {
                val bitmap =
                    MediaStore.Images.Media.getBitmap(
                        requireContext().contentResolver,
                        profileFilePath
                    )
                profileimagefarmseller.setImageBitmap(bitmap);
                // uploadImage(profileFilePath!!)

            } catch (e: IOException) {
                e.printStackTrace()
            }


        }
    }

    private fun Done(s: String) {
        Toast.makeText(requireContext(), "${s}", Toast.LENGTH_SHORT).show()
        nameofarm_et_seller.getText()?.clear();
        locations_et_seller.getText()?.clear();
        phone_et_farm_seller.getText()?.clear();
        progressDailog?.dismiss()
        findNavController().navigate(R.id.action_addFarmInSeller_to_showFarmFragment)
    }

    private fun uploadCoverPhotoImage(filePath: Uri) {
        adViewModel.uploadImage(filePath, ::ImageUploaded, requireContext())
    }


    private fun uploadProfileImage(filePath: Uri) {
        adViewModel.uploadImage(filePath, ::uploadedProfilePhoto, requireContext())
    }

    private fun uploadedProfilePhoto(s: String) {
        Toast.makeText(requireContext(), "${profileId}", Toast.LENGTH_SHORT).show()
        profileId = s
        Toast.makeText(requireContext(), "${profileId}", Toast.LENGTH_SHORT).show()
        var farmName = FarmName()
        farmName.name = nameofarm_et_seller.text.toString()
        farmName.location = locations_et_seller.text.toString()
        farmName.phoneNo = phone_et_farm_seller.text.toString()
        farmName.coverProfile = coverpath.toString()
        farmName.profileImages = profileId.toString()
        farmName.farmOwner = userAccount.userName
        adViewModel.addFarm(farmName, ::Done)
    }


    private fun ImageUploaded(s: String) {


        Toast.makeText(requireContext(), "${coverpath}", Toast.LENGTH_SHORT).show()
        coverpath = s
        uploadProfileImage(profileFilePath!!)
        Toast.makeText(requireContext(), "${coverpath}", Toast.LENGTH_SHORT).show()


    }


    fun getPath(context: Context, uri: Uri?): String? {
        var result: String? = null
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        val cursor: Cursor? =
            requireContext().getContentResolver().query(uri!!, proj, null, null, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(proj[0])
                result = cursor.getString(column_index)
            }
            cursor.close()
        }
        if (result == null) {
            result = "Not found"
        }
        return result
    }

    private fun createImageFile(path: String): File? {
        var imageFile: File? = null
        val dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        val imageFileName = "JPEG_" + path + "_"
        try {
            imageFile = File.createTempFile(imageFileName, ".jpg", dir)
        } catch (e: IOException) {
            Log.d("YJW", e.message!!)
        }
        return imageFile
    }


    private fun readImage(uri: Uri, context: Context): Bitmap? {
        val inputStream = context.contentResolver.openInputStream(uri)
        val res = BitmapFactory.decodeStream(inputStream)
        inputStream!!.close()
        return res
    }
}
