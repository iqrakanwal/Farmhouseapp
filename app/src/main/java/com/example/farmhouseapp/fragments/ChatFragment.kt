package com.example.farmhouseapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmhouseapp.R
import com.example.farmhouseapp.SharedPreferencesUtils
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView


class ChatFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var chatRef: DatabaseReference? = null
    private  var userRef:DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
      var   view = inflater.inflate(R.layout.fragment_chat_list, container, false)
        chatRef = FirebaseDatabase.getInstance().reference.child("Contacts").child("${SharedPreferencesUtils.getUserUid(requireContext())}")
        userRef = FirebaseDatabase.getInstance().reference.child("Users")
        recyclerView = view?.findViewById(R.id.chats_list)
        recyclerView?.setLayoutManager(LinearLayoutManager(context))
        return view
    }

    override fun onStart() {
        super.onStart()
        val options = FirebaseRecyclerOptions.Builder<Contacts>()
            .setQuery(chatRef!!, Contacts::class.java).build()
        val adapter: FirebaseRecyclerAdapter<Contacts, ChatViewHolder> =
            object : FirebaseRecyclerAdapter<Contacts, ChatViewHolder>(options) {
                override fun onBindViewHolder(
                    holder: ChatViewHolder,
                    position: Int,
                    model: Contacts
                ) {
                    val userid = getRef(position).key
                    val image = arrayOf("default_image")
                    userRef?.child(userid!!)?.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                if (dataSnapshot.hasChild("image")) {
                                    image[0] = dataSnapshot.child("image").value.toString()
                                    Picasso.get().load(image[0])
                                        .placeholder(R.drawable.profile_image)
                                        .into(holder.profile_image)
                                }
                                val name = dataSnapshot.child("name").value.toString()
                                val status = dataSnapshot.child("status").value.toString()
                                holder.username.text = name
                                holder.userstatus.text = """
                            Last seen: 
                            Date  Time
                            """.trimIndent()
                                if (dataSnapshot.child("userState").hasChild("state")) {
                                    val state = dataSnapshot.child("userState")
                                        .child("state").value.toString()
                                    val date = dataSnapshot.child("userState")
                                        .child("date").value.toString()
                                    val time = dataSnapshot.child("userState")
                                        .child("time").value.toString()
                                    if (state == "online") {
                                        holder.userstatus.text = "online"
                                    } else if (state == "offline") {
                                        holder.userstatus.text = "Last seen: \n$date $time"
                                    }
                                } else {
                                    holder.userstatus.text = "offline"
                                }
                                holder.itemView.setOnClickListener {
                                    SharedPreferencesUtils.setOwner(requireContext(),name )
                                    /*val chatIntent = Intent(context, ChatActivity::class.java)
                                    chatIntent.putExtra("visit_user_id", userid)
                                    chatIntent.putExtra("visit_user_name", name)
                                    chatIntent.putExtra("visit_image", image[0])
                                    startActivity(chatIntent)*/
                                    findNavController().navigate(R.id.action_chatList_to_chatWithSellerForSeller)


                                }
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {}
                    })
                }

                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
                    val view =
                        LayoutInflater.from(context)
                            .inflate(R.layout.users_display_layout, parent, false)
                    return ChatViewHolder(view)
                }
            }
        recyclerView!!.adapter = adapter
        adapter.startListening()
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var profile_image: CircleImageView
        var username: TextView
        var userstatus: TextView

        init {
            profile_image = itemView.findViewById(R.id.users_profile_image)
            username = itemView.findViewById(R.id.users_profile_name)
            userstatus = itemView.findViewById(R.id.users_status)
        }
    }

}