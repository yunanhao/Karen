package com.example.karen.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.karen.R
import com.example.karen.adapter.MyAdapter
import com.example.karen.bean.ImageFileBean

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<RecyclerView>(R.id.recyclerView).apply {
            adapter = MyAdapter().apply {
                for (i in 0..10) {
                    dataList.add(ImageFileBean().apply {
                        name = "name$i"
                    })
                }
            }
        }

    }
}