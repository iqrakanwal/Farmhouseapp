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
import com.example.farmhouseapp.models.Orders


class OrdersAdaptor(
    var context: Context,
    var images: java.util.ArrayList<Orders?>,
    var animal: (Orders) -> Unit

) : RecyclerView.Adapter<OrdersAdaptor.ImageItem>() {

    inner class ImageItem(view: View) : RecyclerView.ViewHolder(view) {
        var orderProduct: TextView = view.findViewById(R.id.orderProduct)
        var status: TextView = view.findViewById(R.id.status)
        var imageproduct: ImageView = view.findViewById(R.id.imageproduct)
        var itemordr: ConstraintLayout = view.findViewById(R.id.itemordr)
        /* override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
             onclickListener.onClick()
         }*/
        //   var name: TextView = view.findViewById(R.id.size)
        //   var size: TextView = view.findViewById(R.id.duration)
        //   var createdtime: ImageView = view.findViewById(R.id.thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageItem {


        var view: View = LayoutInflater.from(context).inflate(R.layout.order_item, parent, false)
        return ImageItem(view)
    }


    override fun getItemCount(): Int {
        return images.size

    }

    override fun onBindViewHolder(holder: ImageItem, position: Int) {
        holder.orderProduct.text = images.get(position)?.animalid?.name
        holder.status.text = images.get(position)?.orderstatus

        holder.itemordr.setOnClickListener {
            animal(images.get(position)!!)
        }
        Glide.with(context).load(images.get(position)?.animalid?.images).into(holder.imageproduct)


    }

    // method for filtering our recyclerview items.
    fun filterList(filterlist: java.util.ArrayList<Orders?>) {
        // below line is to add our filtered
        // list in our course array list.
        images = filterlist
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

}