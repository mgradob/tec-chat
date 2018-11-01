package com.test.mgb.tecchat

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView

class MyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    fun bind(mensaje: String, autor: String) {
        with(itemView) {
            val mensajeTv = this.findViewById<TextView>(R.id.mensaje)
            mensajeTv.text = mensaje

            val autorTv = this.findViewById<TextView>(R.id.autor)
            autorTv.text = autor
        }
    }
}