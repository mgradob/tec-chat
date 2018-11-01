package com.test.mgb.tecchat

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = MyAdapter()

        val editText = findViewById<EditText>(R.id.editText)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            Toast.makeText(this, editText.text.toString(), Toast.LENGTH_LONG).show()
        }
    }
}
