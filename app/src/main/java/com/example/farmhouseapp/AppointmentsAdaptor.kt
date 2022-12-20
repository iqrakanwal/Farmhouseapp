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
import com.example.farmhouseapp.models.Appointments


class AppointmentsAdaptor(
    var context: Context,
    var images: java.util.ArrayList<Appointments?>,
    var animal: (Appointments) -> Unit

) : RecyclerView.Adapter<AppointmentsAdaptor.ImageItem>() {

    inner class ImageItem(view: View) : RecyclerView.ViewHolder(view) {
        var appointmentseller: TextView = view.findViewById(R.id.appointmentseller)
        var appointmentstatus: TextView = view.findViewById(R.id.appointmentstatus)
        var appointmentorder: ConstraintLayout = view.findViewById(R.id.appointmentorder)

        /* override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
             onclickListener.onClick()
         }*/
        //   var name: TextView = view.findViewById(R.id.size)
        //   var size: TextView = view.findViewById(R.id.duration)
        //   var createdtime: ImageView = view.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {


        var view: View = LayoutInflater.from(context).inflate(R.layout.appointm_item, parent, false)
        return ImageItem(view)
    }


    override fun getItemCount(): Int {
        return images.size

    }

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        holder.appointmentseller.text = images.get(position)?.sellername
        holder.appointmentstatus.text = images.get(position)?.appointmentstatus

        holder.appointmentorder.setOnClickListener {
            animal(images.get(position)!!)
        }
       // Glide.with(context).load(images.get(position)?.animalid?.images).into(holder.imageproduct)


    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: java.util.ArrayList<Appointments?>) {
        // below line is to add our filtered
        // list in our course array list.
        images = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

}