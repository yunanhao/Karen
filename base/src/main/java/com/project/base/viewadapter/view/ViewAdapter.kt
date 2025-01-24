package com.project.base.viewadapter.view

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter(
    value = ["shape", "solid", "strokeWidth", "strokeColor", "radius", "radiusLeftTop", "radiusLeftBottom", "radiusRightTop", "radiusRightBottom"],
    requireAll = false
)
fun setGradientDrawable(
    view: View,
    shape: Int,
    solid: Int,
    strokeWidth: Int,
    strokeColor: Int,
    radius: Int,
    radiusLeftTop: Int,
    radiusLeftBottom: Int,
    radiusRightTop: Int,
    radiusRightBottom: Int
) {
    val floats: FloatArray = if (radius > 0) {
        floatArrayOf(
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat(),
            radius.toFloat()
        )
    } else {
        floatArrayOf(
            radiusLeftTop.toFloat(),
            radiusLeftTop.toFloat(),
            radiusRightTop.toFloat(),
            radiusRightTop.toFloat(),
            radiusRightBottom.toFloat(),
            radiusRightBottom.toFloat(),
            radiusLeftBottom.toFloat(),
            radiusLeftBottom.toFloat()
        )
    }
    val gd = GradientDrawable()
    gd.setColor(solid)
    gd.shape = shape
    gd.setStroke(dp2px(strokeWidth.toFloat(), view.context).toInt(), strokeColor)
    gd.cornerRadii = floats.map {
        dp2px(it, view.context)
    }.toFloatArray()
    view.background = gd
}

fun dp2px(dp: Float, context: Context): Float {
    return dp * context.resources.displayMetrics.density + 0.5f
}