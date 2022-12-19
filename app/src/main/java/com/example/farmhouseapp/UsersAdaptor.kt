package com.example.farmhouseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.farmhouseapp.models.User


class UsersAdaptor(
    var context: Context,
    var images: java.util.ArrayList<User?>,


) : RecyclerView.Adapter<UsersAdaptor.ImageItem>() {

    inner class ImageItem(view: View) : RecyclerView.ViewHolder(view) {
        var username: TextView = view.findViewById(R.id.username)
        var role: TextView = view.findViewById(R.id.role)
        var email: TextView = view.findViewById(R.id.email)
        var itemms: ConstraintLayout = view.findViewById(R.id.itemms)
        /* override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
             onclickListener.onClick()
         }*/
        //   var name: TextView = view.findViewById(R.id.size)
        //   var size: TextView = view.findViewById(R.id.duration)
        //   var createdtime: ImageView = view.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {


        var view: View = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return ImageItem(view)
    }


    override fun getItemCount(): Int {
        return images.size

    }

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        holder.username.text = images.get(position)?.name
        holder.role.text = images.get(position)?.role
        holder.email.text = images.get(position)?.email
        holder.itemms.setOnClickListener {

        }
       // Glide.with(context).load(images.get(position)?.animalid?.images).into(holder.imageproduct)


    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: java.util.ArrayList<User?>) {
        // below line is to add our filtered
        // list in our course array list.
        images = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

}