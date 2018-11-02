package com.test.mgb.tecchat

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val messages: ArrayList<Message> = arrayListOf()

    val adapter: MessagesAdapter = MessagesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("chats/NewChat/messages")
        myRef.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                Log.e("MainActivity", error.message)
            }

            override fun onDataChange(data: DataSnapshot) {
                Log.i("MainActivity", data.value.toString())

                messages.clear()

                data.children.forEach {
                    messages.add(it.getValue(Message::class.java) ?: Message())
                }

                adapter.update(messages)
            }
        })

        val list = findViewById<RecyclerView>(R.id.list)
        list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        list.adapter = adapter

        val input = findViewById<EditText>(R.id.input)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val newMessage = Message(
                    "Miguel",
                    input.text.toString(),
                    Calendar.getInstance().toString()
            )

            val tz = TimeZone.getTimeZone("UTC")
            val df = SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ") // Quoted "Z" to indicate UTC, no timezone offset
            df.timeZone = tz
            val nowAsISO = df.format(Date())

            val newMessageMap = mapOf<String, Any>(
                    "author" to newMessage.author,
                    "message" to newMessage.message,
                    "timestamp" to nowAsISO.toString()
            )

            val key = myRef.push().key ?: UUID.randomUUID().toString()

            val childUpdates = HashMap<String, Any>()
            childUpdates[key] = newMessageMap

            myRef.updateChildren(childUpdates)

            input.text.clear()
        }
    }

    data class Message(val author: String = "", val message: String = "", val timestamp: String = "")

    class MessagesAdapter : RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder>() {

        val messages: ArrayList<Message> = arrayListOf()

        override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MessagesViewHolder =
                MessagesViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.message_view_holder, viewGroup, false))

        override fun getItemCount(): Int = messages.size

        override fun onBindViewHolder(holder: MessagesViewHolder, position: Int) {
            holder.bind(messages[position])
        }

        fun update(newMessages: ArrayList<Message>) {
            messages.clear()
            messages.addAll(newMessages)
            notifyDataSetChanged()
        }

        class MessagesViewHolder(view: View) : RecyclerView.ViewHolder(view) {

            fun bind(message: Message) {
                val from = itemView.findViewById<TextView>(R.id.from)
                from.text = message.author

                val messageText = itemView.findViewById<TextView>(R.id.message)
                messageText.text = message.message

                val timestamp = itemView.findViewById<TextView>(R.id.timestamp)
                timestamp.text = message.timestamp
            }
        }
    }
}
