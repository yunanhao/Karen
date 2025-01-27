package com.project.base.ui.widget

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/*
 val content1 = OnClickSpannableString(
            this,
            "1.我们在《用户服务协议》和《用户隐私政策》描述了我们如何收集、使用、保存、共享和转让这些信息，这些内容包括但不限于：我们收集信息范围、使用用途；我们如何保护用户的个人信息安全；您对您的个人信息享有的权利等。\n" +
                    "2.如果您继续使用我们的服务，表明您理解并接受我们《用户服务协议》和《用户隐私政策》的全部内容。"
        )
            .all("《用户服务协议》").size(16)
            .onClick(protocol_tv_text) {
                ARouter.getInstance()
                    .build("/module_mine/ProtocolActivity")
                    .withString("title", "用户服务协议")
                    .withString("type", "2")
                    .navigation()
            }
            .textColor(R.color.find_color_71C7AE)
            .all("《用户隐私政策》").size(16)
            .onClick(protocol_tv_text) {
                ARouter.getInstance()
                    .build("/module_mine/ProtocolActivity")
                    .withString("title", "隐私政策")
                    .withString("type", "1")
                    .navigation()
            }
            .textColor(R.color.find_color_71C7AE)

        protocol_tv_text.text = content1
 */
/**
 * @Author
 * @Time
 * @Description TextView 实现部分文字改变多种颜色并为单段或多段不同颜色的文字段添加不同点击事件
 */
class OnClickSpannableString(private val context: Context, text: CharSequence) :
    SpannableString(text) {

    private val spanMode = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE

    // 初始时，待处理的索引范围为全部字符串
    private val rangeList = mutableListOf(Pair(0, text.length))
    private val allRangeList = mutableListOf<Pair<Int, Int>>()
    private var textColor: Int = 0


    /**
     *  匹配出现的第一个目标子串[target]，并记录开始和结束的index
     */
    fun first(target: String): OnClickSpannableString {
        rangeList.clear()
        val index = toString().indexOf(target)
        val range = Pair(index, index + target.length)
        rangeList.add(range)
        return this
    }

    /**
     *  匹配出现的最后一个目标子串[target]，并记录开始和结束的index
     */
    fun last(target: String): OnClickSpannableString {
        rangeList.clear()
        val index = toString().lastIndexOf(target)
        val range = Pair(index, index + target.length)
        rangeList.add(range)
        return this
    }

    /**
     *   匹配出现的所有目标子串[target]，并记录开始和结束的index
     */
    fun all(target: String): OnClickSpannableString {
        rangeList.clear()
        val indexes = indexOf(toString(), target)
        for (index in indexes) {
            val range = Pair(index, index + target.length)
            rangeList.add(range)
            allRangeList.add(range)
        }
        return this
    }

    /**
     *  记录源字符串[src]中目标子串 [target]出现的索引位置
     */
    fun indexOf(src: String, target: String): MutableList<Int> {
        val positions = mutableListOf<Int>()
        var index = src.indexOf(target)
        while (index >= 0) {
            positions.add(index)
            index = src.indexOf(target, index + 1)
        }
        return positions
    }

    /**
     * 手动输入一个起点索引[from]和终点索引[to]
     */
    fun range(from: Int, to: Int): OnClickSpannableString {
        rangeList.clear()
        val range = Pair(from, to + 1)
        rangeList.add(range)
        return this
    }

    /**
     * 手动输入所有起点和终点的索引范围[ranges]
     */
    fun ranges(ranges: MutableList<Pair<Int, Int>>): OnClickSpannableString {
        rangeList.clear()
        rangeList.addAll(ranges)
        return this
    }

    /**
     * 计算两个字符串[startText] 和 [endText]之间的字符串的索引，加入到待处理的集合中，后续的Span设置都是对该索引范围内的字串进行的
     */
    fun between(startText: String, endText: String): OnClickSpannableString {
        rangeList.clear()
        val startIndex = toString().indexOf(startText) + startText.length + 1
        val endIndex = toString().lastIndexOf(endText) - 1
        val range = Pair(startIndex, endIndex)
        rangeList.add(range)
        return this
    }

    /**
     * 给target字串设置文字绝对大小为[dp]
     */
    fun size(dp: Int): OnClickSpannableString {
        for (range in rangeList) {
            if (range.first >= 0) {
                setSpan(AbsoluteSizeSpan(dp, true), range.first, range.second, spanMode)
            }
        }
        return this
    }

    /**
     * 给target字串设置文字相对大小，指相对于文本设定的大小的相对比例为[proportion]
     */
    fun scaleSize(proportion: Int): OnClickSpannableString {
        for (range in rangeList) {
            if (range.first >= 0) {
                setSpan(RelativeSizeSpan(proportion.toFloat()), range.first, range.second, spanMode)
            }
        }
        return this
    }

    /**
     * 给target字串设置样式（粗体）
     */
    fun bold(): OnClickSpannableString {
        for (range in rangeList) {
            if (range.first >= 0) {
                setSpan(StyleSpan(Typeface.BOLD), range.first, range.second, spanMode)
            }
        }
        return this
    }

    /**
     * 给target字串设置样式（斜体）
     */
    fun italic(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.ITALIC), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 给target字串设置样式（正常）
     */
    fun normal(): OnClickSpannableString {
        for (range in rangeList) {
            if (range.first >= 0) {
                setSpan(StyleSpan(Typeface.NORMAL), range.first, range.second, spanMode)
            }
        }
        return this
    }

    /**
     * 给target字串设置样式（粗斜体）
     */
    fun boldAndItalic(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.BOLD_ITALIC), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 给target字串设置样式（粗斜体）
     */
    fun italicAndBold(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(StyleSpan(Typeface.BOLD_ITALIC), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 字体样式，可以设置不同的字体，比如系统自带的SANS_SERIF、MONOSPACE和SERIF
     */
    fun font(font: String): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(TypefaceSpan(font), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 给target字串添加删除线
     */
    fun strikethrough(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(StrikethroughSpan(), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 给target字串添加下划线
     */
    fun underline(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(UnderlineSpan(), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 类似于HTML中的<li>标签的圆点效果,[dp]表示圆点和字体的间距，[colorRes]表示圆点的颜色
     */
    fun bullet(dp: Int, @ColorRes colorRes: Int?): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(
                BulletSpan(
                    dp,
                    if (colorRes == null || colorRes == 0) textColor else context.resources.getColor(
                        colorRes
                    )
                ), range.first, range.second, spanMode
            )
        }
        return this
    }

    /**
     * 字体颜色 [colorRes]表示target字串的字体颜色
     */
    fun textColor(@ColorRes colorRes: Int): OnClickSpannableString {
        textColor = ContextCompat.getColor(context, colorRes)
        for (range in rangeList) {
            if (range.first >= 0) {
                setSpan(ForegroundColorSpan(textColor), range.first, range.second, spanMode)
            }
        }
        return this
    }

    fun textColor(colorString: String): OnClickSpannableString {
        textColor = Color.parseColor(colorString)
        for (range in rangeList) {
            if (range.first >= 0) {
                setSpan(ForegroundColorSpan(textColor), range.first, range.second, spanMode)
            }
        }
        return this
    }

    fun getRangeList(): MutableList<Pair<Int, Int>> {
        return allRangeList
    }

    /**
     * 将target字串作为下标
     */
    fun subscript(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(SubscriptSpan(), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 将target字串作为上标
     */
    fun superscript(): OnClickSpannableString {
        for (range in rangeList) {
            setSpan(SuperscriptSpan(), range.first, range.second, spanMode)
        }
        return this
    }

    /**
     * 给[textView]设置一个点击事件[onTextClickListener]
     */
    fun onClick(
        textView: TextView,
        hasUnderline: Boolean,
        onTextClickListener: () -> Unit
    ): OnClickSpannableString {
        for (range in rangeList) {
            val span = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    onTextClickListener.invoke()
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = hasUnderline
                    ds.color = ds.linkColor
                }
            }
            if (range.first >= 0) {
                setSpan(span, range.first, range.second, spanMode)
            }
        }

        textView.highlightColor = Color.TRANSPARENT
        textView.movementMethod = LinkMovementMethod.getInstance()
        return this
    }

}