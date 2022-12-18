package com.example.farmhouseapp.fragments

import android.app.Activity
import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.farmhouseapp.MessageAdapter
import com.example.farmhouseapp.Messages
import com.example.farmhouseapp.R
import com.google.android.gms.tasks.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageTask
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.*


class ChatWithSeller : Fragment() {
    private var messageRecieverId: String? = null
    private  var getMessageRecievername:String? = null
    private  var messagereceiverimage:String? = null
    private  var messageSenderId:String? = null
    private var username: TextView? = null
    private  var userlastseen:TextView? = null
    private var userprofile: CircleImageView? = null
    private var chattoolbar: Toolbar? = null
    private var sendMessageButton: ImageButton? = null
    private  var sendFileButton:ImageButton? = null
    private var messagesentinput: EditText? = null
    private var mauth: FirebaseAuth? = null
    private var RootRef: DatabaseReference? = null
    private val messagesList: List<Messages> = ArrayList<Messages>()
    private var linearLayoutManager: LinearLayoutManager? = null
    private var messageAdapter: MessageAdapter? = null
    private var usermessagerecyclerview: RecyclerView? = null

    private var savecurrentTime: String? = null
    private  var savecurrentDate:String? = null
    private var checker = ""
    private  var myUrl:String? = ""
    private var uploadTask: StorageTask<*>? = null
    private var fileuri: Uri? = null
    private var loadingBar: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat_with_seller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadingBar = ProgressDialog(requireContext())
        mauth = FirebaseAuth.getInstance()
        messageSenderId = mauth?.getCurrentUser()!!.uid
        RootRef = FirebaseDatabase.getInstance().reference

        /*messageRecieverId = getIntent().getExtras().get("visit_user_id").toString()
        getMessageRecievername = getIntent().getExtras().get("visit_user_name").toString()
        messagereceiverimage = getIntent().getExtras().get("visit_image").toString()
*/




        val layoutInflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val actionbarview: View = layoutInflater.inflate(R.layout.custom_chat_bar, null)

        username = view.findViewById<TextView>(R.id.custom_profile_name)
        userlastseen = view.findViewById<TextView>(R.id.custom_user_last_seen)
        userprofile = view.findViewById(R.id.custom_profile_image)
        sendMessageButton = view.findViewById<ImageButton>(R.id.send_message_btn)
        sendFileButton = view.findViewById<ImageButton>(R.id.send_files_btn)
        messagesentinput = view.findViewById<EditText>(R.id.input_messages)
        messageAdapter = MessageAdapter(messagesList)
        usermessagerecyclerview = view.findViewById<RecyclerView>(R.id.private_message_list_of_users)
        linearLayoutManager = LinearLayoutManager(requireContext())
        usermessagerecyclerview!!.layoutManager = linearLayoutManager
        usermessagerecyclerview!!.adapter = messageAdapter
        val calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat("dd/MM/yyyy")
        savecurrentDate = currentDate.format(calendar.time)
        val currentTime = SimpleDateFormat("hh:mm a")
        savecurrentTime = currentTime.format(calendar.time)
        username!!.text = getMessageRecievername
        Picasso.get().load(messagereceiverimage).placeholder(R.drawable.profile_image)
            .into(userprofile)
       /* Displaylastseen()
        sendMessageButton!!.setOnClickListener { SendMessage() }
*/
        sendFileButton!!.setOnClickListener {
            val options = arrayOf<CharSequence>(
                "Images", "PDF Files", "Ms Word Files"
            )
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Select File")
            builder.setItems(options) { dialog, which ->
                if (which == 0) {
                    checker = "image"
                    val intent = Intent()
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.type = "image/*"
                    startActivityForResult(Intent.createChooser(intent, "Select Image"), 555)
                } else if (which == 1) {
                    checker = "pdf"
                    val intent = Intent()
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.type = "application/pdf"
                    startActivityForResult(Intent.createChooser(intent, "Select PDF File"), 555)
                } else if (which == 2) {
                    checker = "docx"
                    val intent = Intent()
                    intent.action = Intent.ACTION_GET_CONTENT
                    intent.type = "application/msword"
                    startActivityForResult(Intent.createChooser(intent, "Select Ms Word File"), 555)
                }
            }
            builder.show()
        }

       /* RootRef!!.child("Messages").child(messageSenderId!!).child(messageRecieverId!!)
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(dataSnapshot: DataSnapshot, s: String?) {
                    val messages = dataSnapshot.getValue(Messages::class.java)
                    messagesList.add(messages)
                    messageAdapter!!.notifyDataSetChanged()
                    usermessagerecyclerview!!.smoothScrollToPosition(usermessagerecyclerview!!.adapter!!.itemCount)
                }

                override fun onChildChanged(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onChildRemoved(dataSnapshot: DataSnapshot) {}
                override fun onChildMoved(dataSnapshot: DataSnapshot, s: String?) {}
                override fun onCancelled(databaseError: DatabaseError) {}
            })*/
    }
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
     /*   if (requestCode == 555 && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            loadingBar!!.setTitle("Sending File")
            loadingBar!!.setMessage("please wait, we are sending that file...")
            loadingBar!!.setCanceledOnTouchOutside(false)
            loadingBar!!.show()
            fileuri = data.data
            if (checker != "image") {
                val storageReference =
                    FirebaseStorage.getInstance().reference.child("Document Files")
                val messageSenderRef = "Messages/$messageSenderId/$messageRecieverId"
                val messageReceiverRef = "Messages/$messageRecieverId/$messageSenderId"
                val Usermessagekeyref = RootRef!!.child("Messages").child(messageSenderId!!).child(
                    messageRecieverId!!
                ).push()
                val messagePushID = Usermessagekeyref.key
                val filepath = storageReference.child("$messagePushID.$checker")
                filepath.putFile(fileuri!!).addOnSuccessListener {
                    filepath.downloadUrl.addOnSuccessListener { uri ->
                        val downloadUrl = uri.toString()
                        val messageDocsBody: MutableMap<String, Object> =
                            HashMap()
                        messageDocsBody["message"] = downloadUrl
                        messageDocsBody["name"] = fileuri!!.lastPathSegment
                        messageDocsBody["type"] = checker
                        messageDocsBody["from"] = messageSenderId
                        messageDocsBody["to"] = messageRecieverId
                        messageDocsBody["messageID"] = messagePushID
                        messageDocsBody["time"] = savecurrentTime
                        messageDocsBody["date"] = savecurrentDate
                        val messageBodyDDetail: MutableMap<*, *> =
                            HashMap<Any?, Any?>()
                        messageBodyDDetail["$messageSenderRef/$messagePushID"] =
                            messageDocsBody
                        messageBodyDDetail["$messageReceiverRef/$messagePushID"] =
                            messageDocsBody
                        RootRef!!.updateChildren(messageBodyDDetail)
                        loadingBar!!.dismiss()
                    }.addOnFailureListener { e ->
                        loadingBar!!.dismiss()
                        Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnProgressListener { taskSnapshot ->
                    val p = 100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                    loadingBar!!.setMessage(p.toInt().toString() + " % Uploading...")
                }
            } else if (checker == "image") {
                val storageReference = FirebaseStorage.getInstance().reference.child("Image Files")
                val messageSenderRef = "Messages/$messageSenderId/$messageRecieverId"
                val messageReceiverRef = "Messages/$messageRecieverId/$messageSenderId"
                val Usermessagekeyref = RootRef!!.child("Messages").child(messageSenderId!!).child(
                    messageRecieverId!!
                ).push()
                val messagePushID = Usermessagekeyref.key
                val filepath = storageReference.child("$messagePushID.jpg")
                uploadTask = filepath.putFile(fileuri!!)
                uploadTask.continueWithTask(object : Continuation<Any?, Any?> {
                    @Throws(Exception::class)
                    fun then(task: Task<*>): Any? {
                        if (!task.isSuccessful) {
                            throw task.exception!!
                        }
                        return filepath.downloadUrl
                    }
                }).addOnCompleteListener(OnCompleteListener<Uri> { task ->
                    if (task.isSuccessful) {
                        val downloadUrl = task.result
                        myUrl = downloadUrl.toString()
                        val messageTextBody: MutableMap<*, *> = HashMap<Any?, Any?>()
                        messageTextBody["message"] = myUrl
                        messageTextBody["name"] = fileuri!!.lastPathSegment
                        messageTextBody["type"] = checker
                        messageTextBody["from"] = messageSenderId
                        messageTextBody["to"] = messageRecieverId
                        messageTextBody["messageID"] = messagePushID
                        messageTextBody["time"] = savecurrentTime
                        messageTextBody["date"] = savecurrentDate
                        val messageBodyDetails: MutableMap<*, *> = HashMap<Any?, Any?>()
                        messageBodyDetails["$messageSenderRef/$messagePushID"] = messageTextBody
                        messageBodyDetails["$messageReceiverRef/$messagePushID"] = messageTextBody
                        RootRef!!.updateChildren(messageBodyDetails)
                            .addOnCompleteListener(object : OnCompleteListener<Any?> {
                                fun onComplete(task: Task<*>) {
                                    if (task.isSuccessful) {
                                        loadingBar!!.dismiss()
                                        //Toast.makeText(ChatActivity.this,"Message sent Successfully...",Toast.LENGTH_SHORT).show();
                                    } else {
                                        loadingBar!!.dismiss()
                                        Toast.makeText(
                                            this@ChatActivity,
                                            "Error:",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                    messagesentinput!!.setText("")
                                }
                            })
                    }
                })
            } else {
                loadingBar!!.dismiss()
                Toast.makeText(this, "please select file", Toast.LENGTH_SHORT).show()
            }
        }
*/    }

  /*  fun Displaylastseen() {
        RootRef!!.child("Users").child(messageRecieverId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.child("userState").hasChild("state")) {
                        val state = dataSnapshot.child("userState").child("state").value.toString()
                        val date = dataSnapshot.child("userState").child("date").value.toString()
                        val time = dataSnapshot.child("userState").child("time").value.toString()
                        if (state == "online") {
                            userlastseen!!.text = "online"
                        } else if (state == "offline") {
                            userlastseen!!.text = "Last seen: \n$date $time"
                        }
                    } else {
                        userlastseen!!.text = "offline"
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {}
            })
    }
    private fun SendMessage() {
        val messagetext = messagesentinput!!.text.toString()
        if (TextUtils.isEmpty(messagetext)) {
            Toast.makeText(requireContext(), "Please enter message first..", Toast.LENGTH_SHORT).show()
        } else {
            val messageSenderRef = "Messages/$messageSenderId/$messageRecieverId"
            val messageReceiverRef = "Messages/$messageRecieverId/$messageSenderId"
            val Usermessagekeyref = RootRef!!.child("Messages").child(messageSenderId!!).child(
                messageRecieverId!!
            ).push()
            val messagePushID = Usermessagekeyref.key
            val messageTextBody: MutableMap<String, Object> = HashMap<String, Object>()
            messageTextBody["message"] = messagetext
            messageTextBody["type"] = "text"
            messageTextBody["from"] = messageSenderId
            messageTextBody["to"] = messageRecieverId
            messageTextBody["messageID"] = messagePushID
            messageTextBody["time"] = savecurrentTime
            messageTextBody["date"] = savecurrentDate
            val messageBodyDetails: MutableMap<*, *> = HashMap<Any?, Any?>()
            messageBodyDetails["$messageSenderRef/$messagePushID"] = messageTextBody
            messageBodyDetails["$messageReceiverRef/$messagePushID"] = messageTextBody
            RootRef!!.updateChildren(messageBodyDetails)
                .addOnCompleteListener(object : OnCompleteListener<Any?> {
                    fun onComplete(task: Task<*>) {
                        if (task.isSuccessful) {
                            // Toast.makeText(ChatActivity.this,"Message sent Successfully...",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(requireContext(), "Error:", Toast.LENGTH_SHORT).show()
                        }
                        messagesentinput!!.setText("")
                    }
                })
        }
    }*/


}


