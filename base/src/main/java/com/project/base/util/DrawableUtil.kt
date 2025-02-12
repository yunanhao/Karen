package com.project.base.util

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.PaintDrawable
import android.graphics.drawable.ShapeDrawable

object DrawableUtil {

    class Shape {
        //左上角的半径
        private var topLeftRadius: Int = 0

        //右上角的半径
        private var topRightRadius: Int = 0

        //左下角的半径
        private var bottomLeftRadius: Int = 0

        //右下角的半径
        private var bottomRightRadius: Int = 0

        fun setRadius(radius: Int): Shape {
            topLeftRadius = radius
            topRightRadius = radius
            bottomLeftRadius = radius
            bottomRightRadius = radius
            return this
        }

        fun setRadius(
            topLeftRadius: Int = this.topLeftRadius,
            topRightRadius: Int = this.topRightRadius,
            bottomLeftRadius: Int = this.bottomLeftRadius,
            bottomRightRadius: Int = this.bottomRightRadius,
        ): Shape {
            this.topLeftRadius = topLeftRadius
            this.topRightRadius = topRightRadius
            this.bottomLeftRadius = bottomLeftRadius
            this.bottomRightRadius = bottomRightRadius
            return this
        }

        fun setRadiusTop(radius: Int): Shape {
            topLeftRadius = radius
            topRightRadius = radius
            return this
        }

        fun setRadiusBottom(radius: Int): Shape {
            bottomLeftRadius = radius
            bottomRightRadius = radius
            return this
        }

        fun setRadiusLeft(radius: Int): Shape {
            topLeftRadius = radius
            bottomLeftRadius = radius
            return this
        }

        fun setRadiusRight(radius: Int): Shape {
            topRightRadius = radius
            bottomRightRadius = radius
            return this
        }


        fun build(): GradientDrawable {
            val drawable = GradientDrawable()
            drawable.cornerRadius
            return drawable
        }
    }

    fun createColor(): ColorDrawable {
        return ColorDrawable()
    }

    private fun createShape(): Shape {
        return Shape()
    }

    fun test() {
        createShape().build()
    }
}