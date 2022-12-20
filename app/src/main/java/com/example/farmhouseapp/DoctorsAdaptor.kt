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
import com.example.farmhouseapp.models.Animal
import com.example.farmhouseapp.models.Doctor


class DoctorsAdaptor(
    var context: Context,
    var images: java.util.ArrayList<Doctor?>,
    var animal: (Doctor) -> Unit

) : RecyclerView.Adapter<DoctorsAdaptor.ImageItem>() {

    inner class ImageItem(view: View) : RecyclerView.ViewHolder(view) {
        var doctorname: TextView = view.findViewById(R.id.doctorname)
        var desgiation: TextView = view.findViewById(R.id.desgiation)
        var hospitalxfv: TextView = view.findViewById(R.id.hospitalxfv)
        var contactnumdoc: TextView = view.findViewById(R.id.contactnumdoc)
        var imagedoc: ImageView = view.findViewById(R.id.imagedoc)
        var itemdsfd: ConstraintLayout = view.findViewById(R.id.itemdsfd)
        /* override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
             onclickListener.onClick()
         }*/
        //   var name: TextView = view.findViewById(R.id.size)
        //   var size: TextView = view.findViewById(R.id.duration)
        //   var createdtime: ImageView = view.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {


        var view: View = LayoutInflater.from(context).inflate(R.layout.doc_item, parent, false)
        return ImageItem(view)
    }


    override fun getItemCount(): Int {
        return images.size

    }

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        holder.doctorname.text = images.get(position)?.name
        holder.desgiation.text = images.get(position)?.designation
        holder.hospitalxfv.text = images.get(position)?.hospital
        holder.contactnumdoc.text = images.get(position)?.contactnum
        holder.itemdsfd.setOnClickListener {
            animal(images.get(position)!!)
        }
        Glide.with(context).load(images.get(position)?.profilepicture).into(holder.imagedoc)


    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: java.util.ArrayList<Doctor?>) {
        // below line is to add our filtered
        // list in our course array list.
        images = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

}