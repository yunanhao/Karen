package com.project.base.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.FragmentManager
import com.project.base.R

open class BaseDialogFragment(
    private val layoutId: Int? = null,
    private val layoutView: View? = null,
    private val convertView: ((holder: ViewHolder) -> Unit)?
) : AppCompatDialogFragment() {
    class ViewHolder(val root: View, private val dialogFragment: BaseDialogFragment) {
        val childViews by lazy {
            SparseArray<View>()
        }

        fun close() {
            dialogFragment.dismissAllowingStateLoss()
        }

        inline fun <reified T : View> findView(viewId: Int): T? {
            val view: View? = childViews.get(viewId) ?: root.findViewById(viewId)
            childViews.put(viewId, view)
            return view as? T?
        }

        fun setText(viewId: Int, text: String) {
            val textView = findView<TextView>(viewId) ?: return
            textView.text = text
        }

        fun setText(viewId: Int, textId: Int) {
            val textView = findView<TextView>(viewId) ?: return
            textView.setText(textId)
        }

        fun setTextColor(viewId: Int, colorId: Int) {
            val textView = findView<TextView>(viewId) ?: return
            textView.setTextColor(colorId)
        }

        fun setOnClickListener(viewId: Int, clickListener: View.OnClickListener) {
            val view: View = findView(viewId) ?: return
            view.setOnClickListener(clickListener)
        }

        fun setBackgroundResource(viewId: Int, resId: Int) {
            val view = findView<View>(viewId) ?: return
            view.setBackgroundResource(resId)
        }

        fun setBackgroundColor(viewId: Int, colorId: Int) {
            val view = findView<View>(viewId) ?: return
            view.setBackgroundColor(colorId)
        }
    }

    companion object {
        const val MARGIN_HORIZONTAL = "margin_horizontal"
        const val MARGIN_VERTICAL = "margin_vertical"
        const val WIDTH = "width"
        const val HEIGHT = "height"
        const val DIM = "dim_amount"
        const val GRAVITY = "gravity"
        const val CANCEL = "out_cancel"
        const val ANIM = "anim_style"

        fun Float.dp2px(context: Context?): Int {
            context ?: return this.toInt()
            val scale: Float = context.resources.displayMetrics.density
            return (this * scale + 0.5f).toInt()
        }

        fun Int.dp2px(context: Context?): Int {
            context ?: return this
            val scale: Float = context.resources.displayMetrics.density
            return (this * scale + 0.5f).toInt()
        }

        fun Context?.getScreenWidth(): Int {
            this ?: return 0
            val displayMetrics: DisplayMetrics = resources.displayMetrics
            return displayMetrics.widthPixels
        }

        fun Context?.getScreenHeight(): Int {
            this ?: return 0
            val displayMetrics: DisplayMetrics = resources.displayMetrics
            return displayMetrics.heightPixels
        }
    }

    private var marginHorizontal: Int = 0 //左右边距
    private var marginVertical: Int = 0 //上下边距
    private var width: Int = 0 //宽度
    private var height: Int = 0 //高度
    private var dimAmount: Float = 0.5f //灰度深浅
    private var gravity = Gravity.CENTER //显示的位置
    private var outCancelable = true//是否点击外部取消

    @StyleRes
    private var animStyle: Int = 0

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen)

        //恢复保存的数据
        if (savedInstanceState != null) {
            marginHorizontal = savedInstanceState.getInt(MARGIN_HORIZONTAL)
            marginVertical = savedInstanceState.getInt(MARGIN_VERTICAL)
            width = savedInstanceState.getInt(WIDTH)
            height = savedInstanceState.getInt(HEIGHT)
            dimAmount = savedInstanceState.getFloat(DIM)
            gravity = savedInstanceState.getInt(GRAVITY)
            outCancelable = savedInstanceState.getBoolean(CANCEL)
            animStyle = savedInstanceState.getInt(ANIM)
        }
    }

    override fun onCreateView(
        @NonNull inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View {
        val view: View? = if (layoutId != null) {
            inflater.inflate(layoutId, container, false)
        } else {
            layoutView
        }
        if (view == null) throw NullPointerException("layoutId and layoutView is null")
        convertView?.invoke(ViewHolder(view, this))
        return view
    }

    override fun onStart() {
        super.onStart()
        initParams()
    }

    /**
     * 屏幕旋转等导致DialogFragment销毁后重建时保存数据
     *
     * @param outState
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(MARGIN_HORIZONTAL, marginHorizontal)
        outState.putInt(MARGIN_VERTICAL, marginVertical)
        outState.putInt(WIDTH, width)
        outState.putInt(HEIGHT, height)
        outState.putFloat(DIM, dimAmount)
        outState.putInt(GRAVITY, gravity)
        outState.putBoolean(CANCEL, outCancelable)
        outState.putInt(ANIM, animStyle)
    }

    private fun initParams() {
        val window = dialog?.window ?: return
        val lp = window.attributes
        //调节灰色背景透明度[0-1]，默认0.5f
        lp.dimAmount = dimAmount
        if (gravity != 0) {
            lp.gravity = gravity
        }
        if (animStyle == 0)
            when (gravity) {
                Gravity.START, (Gravity.START and Gravity.BOTTOM), (Gravity.START and Gravity.TOP) -> animStyle =
                    R.style.base_LeftAnimation

                Gravity.TOP -> animStyle = R.style.base_TopAnimation
                Gravity.END, (Gravity.END and Gravity.BOTTOM), (Gravity.END and Gravity.TOP) -> animStyle =
                    R.style.base_RightAnimation

                Gravity.BOTTOM -> animStyle = R.style.base_BottomAnimation
            }
        //设置dialog宽度
        when (width) {
            0 -> lp.width = context.getScreenWidth() - 2 * marginHorizontal.dp2px(context)
            -1 -> lp.width = WindowManager.LayoutParams.MATCH_PARENT
            -2 -> lp.width = WindowManager.LayoutParams.WRAP_CONTENT
            else -> lp.width = width.dp2px(context)
        }
        //设置dialog高度
        when (height) {
            0 -> lp.height = context.getScreenHeight() - 2 * marginVertical.dp2px(context)
            -1 -> lp.height = WindowManager.LayoutParams.MATCH_PARENT
            -2 -> lp.height = WindowManager.LayoutParams.WRAP_CONTENT
            else -> lp.height = height.dp2px(context)
        }
        // 设置透明状态栏
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        //设置dialog进入、退出的动画
        window.setWindowAnimations(animStyle)
        window.attributes = lp
        isCancelable = outCancelable
    }

    fun setMarginHorizontal(marginHorizontal: Int): BaseDialogFragment {
        this.marginHorizontal = marginHorizontal
        return this
    }

    fun setMarginVertical(marginVertical: Int): BaseDialogFragment {
        this.marginVertical = marginVertical
        return this
    }

    fun setWidth(width: Int): BaseDialogFragment {
        this.width = width
        return this
    }

    fun setHeight(height: Int): BaseDialogFragment {
        this.height = height
        return this
    }

    fun setDimAmount(dimAmount: Float): BaseDialogFragment {
        this.dimAmount = dimAmount
        return this
    }

    fun setGravity(gravity: Int): BaseDialogFragment {
        this.gravity = gravity
        return this
    }

    fun setOutCancel(outCancel: Boolean): BaseDialogFragment {
        this.outCancelable = outCancel
        return this
    }

    fun setAnimStyle(@StyleRes animStyle: Int): BaseDialogFragment {
        this.animStyle = animStyle
        return this
    }

    fun show(manager: FragmentManager): BaseDialogFragment {
        val ft = manager.beginTransaction()
        if (this.isAdded) {
            ft.remove(this).commit()
        }
        ft.add(this, System.currentTimeMillis().toString())
        ft.commitAllowingStateLoss()
        return this
    }

}