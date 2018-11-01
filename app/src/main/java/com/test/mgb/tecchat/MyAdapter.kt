package com.test.mgb.tecchat

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class MyAdapter: RecyclerView.Adapter<MyViewHolder>() {

    val data: ArrayList<MessageEntry> = arrayListOf(
            MessageEntry("Test", "AUTHOR")
    )

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
                LayoutInflater.from(viewGroup.context).inflate(R.layout.message_view_holder, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(myViewHolder: MyViewHolder, position: Int) {
        val messageEntry = data[position]

        myViewHolder.bind(messageEntry.message, messageEntry.author)
    }
}