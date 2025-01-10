package com.example.karen.activity

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.karen.R
import com.example.karen.adapter.MyAdapter
import com.example.karen.bean.ImageFileBean
import com.example.karen.view.CustomEllipsizeTextView
import com.example.karen.view.ExpandableTextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        val v1 = findViewById<TextView>(R.id.mytext)
        testText(v1)

        findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {


            }
    }

    fun testText(tv: TextView) {
        tv.apply {
            setText(
                "\n\n实现步骤\n" +
                        "    创建自定义 TextView\n" +
                        "\n" +
                        "使用 Layout 和 StaticLayout 判断文本是否超过两行。\n" +
                        "如果文本超过两行，手动处理显示内容，裁剪到两行并在末尾添加“展开”。\n" +
                        "通过 SpannableString 实现“展开”的点击效果。\n" +
                        "完整代码实现"
            )
//            setText(
//                "实现步骤\n" +
//                        "    创建自定义 TextView\n" +
//                        "\n" +
//                        "使用 Layout 和 StaticLayout 判断文本是否超过两行。\n" +
//                        "如果文本超过两行，手动处理显示内容，裁剪到两行并在末尾添加“展开”。\n" +
//                        "通过 SpannableString 实现“展开”的点击效果。\n" +
//                        "完整代码实现"
//            )
//            setText("在卡牌游戏中，光环效果和叠甲（也称为护甲、护盾等）的优先级和相互作用是设计中重要的机制。这些机制的优先级和叠加规则通常根据游戏的具体设计而定，但以下是一些常见的处理方式：1. 光环效果优先级光环效果通常指影响特定区域内单位的持续性效果，这些效果可能会增加或减少属性，赋予特定能力等。其优先级规则通常如下：")
//            setText("\n\n\n\n\n")
            setText("\n整代\n整代\n整代\n整代")
//            setText("あけましておめでとうございます\uD83C\uDF8D❤\uFE0F\n" +
//                    "2025年もヨロシク\uD83D\uDE1A✌\uD83C\uDFFB#推しFANTASY#推しFANTASYスペシャル#年越し#初詣#お正月#wasai")
        }
    }

    fun testPerMission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {  // Android 14+ 检查
            val photoPickerIntent = Intent(MediaStore.ACTION_PICK_IMAGES)
            photoPickerIntent.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX, 10)  // 可选参数，限制用户选择的数量
            startActivityForResult(photoPickerIntent, 100)
        } else {
            // 旧版本仍然使用完整的存储权限请求
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }
    }

    private val STORAGE_PERMISSION_CODE = 1001


    private fun isStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Android 6.0 及以上版本
            checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        } else {
            // Android 6.0 以下版本，权限默认授予
            true
        }
    }

    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            STORAGE_PERMISSION_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // 权限被授予
                    accessGallery()
                } else {
                    // 权限被拒绝
                    Toast.makeText(this, "权限被拒绝，无法访问相册", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun accessGallery() {
        // 访问相册的代码，例如打开图片选择器
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == Activity.RESULT_OK && data != null) {
            val clipData = data.clipData
            if (clipData != null) {
                for (i in 0 until clipData.itemCount) {
                    val imageUri = clipData.getItemAt(i).uri
                    // 处理选中的图片
                }
            } else {
                val imageUri = data.data
                // 处理单个选中的图片
            }
        }
    }

}