package com.example.karen.activity

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.karen.R
import com.example.karen.adapter.MyAdapter
import com.example.karen.bean.ImageFileBean
import com.example.karen.view.ExpandableTextView
import com.example.karen.view.MyConstraintLayout
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
        if (v1 is ExpandableTextView) {
            v1.setExpandListener(object : ExpandableTextView.OnExpandListener {
                override fun onExpand(view: ExpandableTextView?) {
//                    view?.setText("在卡牌游戏中，光环效果和叠甲（也称为护甲、护盾等）的优先级和相互作用是设计中重要的机制。这些机制的优先级和叠加规则通常根据游戏的具体设计而定，但以下是一些常见的处理方式：1. 光环效果优先级光环效果通常指影响特定区域内单位的持续性效果，这些效果可能会增加或减少属性，赋予特定能力等。其优先级规则通常如下：")
//                    testText(view)
                }

                override fun onShrink(view: ExpandableTextView?) {
//                    view?.setText("在卡牌游戏中，光环效果和叠甲（也称为护甲、护盾等）的优先级和相互作用是设计中重要的机制。这些机制的优先级和叠加规则通常根据游戏的具体设计而定，但以下是一些常见的处理方式：1. 光环效果优先级光环效果通常指影响特定区域内单位的持续性效果，这些效果可能会增加或减少属性，赋予特定能力等。其优先级规则通常如下：")
//                    testText(view)
                }

            })
        }

        findViewById<FloatingActionButton>(R.id.fab)
            .setOnClickListener {


            }
        x1()
        findViewById<ViewGroup>(R.id.myView3).addView(createMyView3(this))
        findViewById<ViewGroup>(R.id.myView4).addView(createMyView4(this))
    }

    fun createMyView3(context: Context): ConstraintLayout {
        // 创建 ConstraintLayout 容器
        val constraintLayout = ConstraintLayout(context).apply {
            id = ConstraintLayout.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }
        // 创建 content TextView
        val contentTextView = TextView(context).apply {
            id = TextView.generateViewId()
            text = "asdadaretarettreterta\nsdaadfsdfsdgfdgdfgdaergs"
            textSize = 15f
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            maxLines = 2
            ellipsize = android.text.TextUtils.TruncateAt.END
            setLineSpacing(4f, 1f)
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6)
            )
        }

        // 创建 more TextView
        val moreTextView = TextView(context).apply {
            id = TextView.generateViewId()
            text = "… 展开"
            textSize = 16f
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(
                resources.getDimensionPixelSize(R.dimen.dp_2),
                0,
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6)
            )
            var isExpanded = false
            setOnClickListener {
                isExpanded = !isExpanded
                if (isExpanded) {
                    contentTextView.maxLines = Int.MAX_VALUE
                    text = "收起"
                } else {
                    contentTextView.maxLines = 2
                    text = "… 展开"
                }
            }
        }

        // 将两个 TextView 添加到 ConstraintLayout 中
        constraintLayout.addView(contentTextView)
        constraintLayout.addView(moreTextView)
        // 使用 ConstraintSet 设置约束
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        // 设置 contentTextView 的约束
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )

        // 设置 moreTextView 的约束
        constraintSet.connect(
            moreTextView.id,
            ConstraintSet.BOTTOM,
            contentTextView.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            moreTextView.id,
            ConstraintSet.END,
            contentTextView.id,
            ConstraintSet.END
        )
        constraintSet.applyTo(constraintLayout)

        testText(contentTextView)
        constraintLayout.post {
            val layout = contentTextView.layout
            if (layout != null) {
                val isTruncated = layout.lineCount > 2 || layout.getEllipsisCount(1) > 0
                if (isTruncated) {
                    moreTextView.visibility = View.VISIBLE
                } else {
                    moreTextView.visibility = View.GONE
                }
            }
        }
        return constraintLayout
    }

    fun createMyView4(context: Context): ConstraintLayout {
        // 创建 ConstraintLayout 容器
        val constraintLayout = MyConstraintLayout(context).apply {
            id = ConstraintLayout.generateViewId()
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
        }
        // 创建 content TextView
        val contentTextView = TextView(context).apply {
            id = TextView.generateViewId()
            text = "asdadaretarettreterta\nsdaadfsdfsdgfdgdfgdaergs"
            textSize = 15f
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
            maxLines = 2
            ellipsize = android.text.TextUtils.TruncateAt.END
            setLineSpacing(4f, 1f)
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6)
            )
        }

        // 创建 more TextView
        val moreTextView = TextView(context).apply {
            id = TextView.generateViewId()
            text = "… 展开"
            textSize = 16f
            layoutParams = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT
            )
            setPadding(
                resources.getDimensionPixelSize(R.dimen.dp_2),
                0,
                resources.getDimensionPixelSize(R.dimen.dp_6),
                resources.getDimensionPixelSize(R.dimen.dp_6)
            )
            var isExpanded = false
            setOnClickListener {
                isExpanded = !isExpanded
                if (isExpanded) {
                    contentTextView.maxLines = Int.MAX_VALUE
                    text = "收起"
                } else {
                    contentTextView.maxLines = 2
                    text = "… 展开"
                }
            }
        }

        // 将两个 TextView 添加到 ConstraintLayout 中
        constraintLayout.addView(contentTextView)
        constraintLayout.addView(moreTextView)
        // 使用 ConstraintSet 设置约束
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        // 设置 contentTextView 的约束
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP
        )
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START
        )
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.END,
            ConstraintSet.PARENT_ID,
            ConstraintSet.END
        )
        constraintSet.connect(
            contentTextView.id,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM
        )

        // 设置 moreTextView 的约束
        constraintSet.connect(
            moreTextView.id,
            ConstraintSet.BOTTOM,
            contentTextView.id,
            ConstraintSet.BOTTOM
        )
        constraintSet.connect(
            moreTextView.id,
            ConstraintSet.END,
            contentTextView.id,
            ConstraintSet.END
        )
        constraintSet.applyTo(constraintLayout)

        testText(contentTextView)
        constraintLayout.post {
            val layout = contentTextView.layout
            if (layout != null) {
                val isTruncated = layout.lineCount > 2 || layout.getEllipsisCount(1) > 0
                if (isTruncated) {
                    moreTextView.visibility = View.VISIBLE
                } else {
                    moreTextView.visibility = View.GONE
                }
            }
        }
        return constraintLayout
    }

    fun x1() {
        val contentTextView = findViewById<TextView>(R.id.content)
        val moreTextView = findViewById<TextView>(R.id.more)

        contentTextView.post {
            val layout = contentTextView.layout
            if (layout != null) {
                val isTruncated = layout.lineCount > 2 || layout.getEllipsisCount(1) > 0
                if (isTruncated) {
                    moreTextView.visibility = View.VISIBLE
                } else {
                    moreTextView.visibility = View.GONE
                }
            }
        }

        var isExpanded = false

        moreTextView.setOnClickListener {
            isExpanded = !isExpanded
            if (isExpanded) {
                contentTextView.maxLines = Int.MAX_VALUE
                moreTextView.text = "收起"
            } else {
                contentTextView.maxLines = 2
                moreTextView.text = "… 展开"
            }
        }
        testText(contentTextView)
    }

    fun testText(tv: TextView?) {
        tv?.apply {
            setText(
                "\n\n实现步骤\n" +
                        "    创建自定义 TextView\n" +
                        "\n" +
                        "使用 Layout 和 StaticLayout 判断文本是否超过两行。\n" +
                        "如果文本超过两行，手动处理显示内容，裁剪到两行并在末尾添加“展开”。\n" +
                        "通过 SpannableString 实现“展开”的点击效果。\n" +
                        "完整代码实现"
            )
            setText(
                "实现步骤\n" +
                        "    创建自定义 TextView\n" +
                        "\n" +
                        "使用 Layout 和 StaticLayout 判断文本是否超过两行。\n" +
                        "如果文本超过两行，手动处理显示内容，裁剪到两行并在末尾添加“展开”。\n" +
                        "通过 SpannableString 实现“展开”的点击效果。\n" +
                        "完整代码实现"
            )
            setText("在卡牌游戏中，光环效果和叠甲（也称为护甲、护盾等）的优先级和相互作用是设计中重要的机制。这些机制的优先级和叠加规则通常根据游戏的具体设计而定，但以下是一些常见的处理方式：1. 光环效果优先级光环效果通常指影响特定区域内单位的持续性效果，这些效果可能会增加或减少属性，赋予特定能力等。其优先级规则通常如下：")
//            setText("\n\n\n\n\n")
//            setText("在全球，随着Flutter被越来越多的知名公司应用在自己的商业APP中，" +
//                    "Flutter这门新技术也逐渐进入了移动开发者的视野，尤其是当Google在2018年IO大会上发布了第一个" +
//                    "Preview版本后，国内刮起来一股学习Flutter的热潮。\n\n为了更好的方便帮助中国开发者了解这门新技术" +
//                    "，我们，Flutter中文网，前后发起了Flutter翻译计划、Flutter开源计划，前者主要的任务是翻译" +
//                    "Flutter官方文档，后者则主要是开发一些常用的包来丰富Flutter生态，帮助开发者提高开发效率。而时" +
//                    "至今日，这两件事取得的效果还都不错！")
//            setText("\n整代\n整代\n整代\n整代")
//            setText("BIGBANG復活は歓喜すぎん？\uD83E\uDD73\n" +
//                    "#BIGBANG #haruharu #カラオケ #歌ってみた動画\n" +
//                    "#mama2024 #推しFANTASYスペシャル ")
//            setText("あけましておめでとうございます\uD83C\uDF8D❤\uFE0F\n" +
//                    "2025年もヨロシク\uD83D\uDE1A✌\uD83C\uDFFB#推しFANTASY#推しFANTASYスペシャル#年越し#初詣#お正月#wasai")
            setText("asdadaretaretadfgadfgdfgtreterta\nadsDSFSDFDSFAFAFSDFDSFSDFDSFAFAFSDFDSFSDFDS\nSDFDSFSDFDSFAFFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAAFdfgdfsgerta\ndfsgdfgdghshjytrhyrujtyukmtuiy")
            setText("asdadaretaretadfgadfgdfgtreterta\nadsgffgdfagdfsdaadfsdfsdgfdgdfgdaerFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAgserta\nsadsFSDFDSFSDFDSFAFFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAFSDFDSFSDFDSFAFAAFdfgdfsgerta\ndfsgdfgdghshjytrhyrujtyukmtuiy")

        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dp2px(context: Context, dpValue: Float): Int {
        var res = 0
        val scale = context.resources.displayMetrics.density
        res = if (dpValue < 0) -(-dpValue * scale + 0.5f).toInt()
        else (dpValue * scale + 0.5f).toInt()
        return res
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