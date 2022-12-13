package com.example.farmhouseapp

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmhouseapp.models.Animal

class AnimalAdaptor(
    var context: Context,
    var images: ArrayList<Animal>,

    ) : RecyclerView.Adapter<AnimalAdaptor.ImageItem>() {


    inner class ImageItem(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var catagory: TextView = view.findViewById(R.id.catagory)
        var farmname: TextView = view.findViewById(R.id.farmname)
        var price: TextView = view.findViewById(R.id.price)
        var image: ImageView = view.findViewById(R.id.image)
        /* override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
             onclickListener.onClick()
         }*/
        //   var name: TextView = view.findViewById(R.id.size)
        //   var size: TextView = view.findViewById(R.id.duration)
        //   var createdtime: ImageView = view.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {


        var view: View = LayoutInflater.from(context).inflate(R.layout.safe_box_item, parent, false)
        return ImageItem(view)
    }


    override fun getItemCount(): Int {
        return images.size

    }

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        holder.name.text = images.get(position).name
        holder.price.text = images.get(position).price
        holder.catagory.text = images.get(position).catagory
        holder.farmname.text = images.get(position).farmName

        Glide
            .with(context)
            .load(Uri.parse("file:///android_asset/${images.get(position).images}.webp"))
            // .load(Uri.parse("file:///android_asset/images/images_one.png"))
            .into(holder.image)

    }
}