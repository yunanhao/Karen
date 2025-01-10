package com.example.karen.adapter

import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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
            val permissionStatus = ContextCompat.checkSelfPermission(holder.image.context, Manifest.permission.READ_EXTERNAL_STORAGE)
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                // 权限已被授予
                ToastUtils.showShort("权限已被授予")
            } else {
                // 权限未被授予
                ToastUtils.showShort("权限未被授予")
            }

        }
        holder.image.setImageResource(R.mipmap.ic_launcher)
        holder.title.text = dataList[position].name
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.image)
        val title: TextView = view.findViewById(R.id.title)
    }
}
