package com.example.karen.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.karen.R
import com.karen.util.common.ToastUtils

class MyAdapter : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    val dataList = ArrayList<com.example.karen.bean.ImageFileBean>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            ToastUtils.showShort("click gua gua gua")
        }
        holder.image.setImageResource(R.mipmap.ic_launcher)
        holder.title.text = dataList[position].name
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)
    }
}
