package com.gwm.libbase.binding.viewadapter.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.project.base.ui.viewadapter.BindingConsumer
import com.project.base.ui.viewadapter.BindingFunction

@BindingAdapter("lineManager")
fun setLineManager(
    recyclerView: RecyclerView,
    function: BindingFunction<RecyclerView, RecyclerView.ItemDecoration>?
) {
    function?.apply(recyclerView)?.let { recyclerView.addItemDecoration(it) }
}

@BindingAdapter("layoutManager")
fun setLayoutManager(
    recyclerView: RecyclerView,
    function: BindingFunction<RecyclerView, RecyclerView.LayoutManager>?
) {
    recyclerView.layoutManager = function?.apply(recyclerView)
}

@BindingAdapter(
    value = ["onScrollChangeCommand", "onScrollStateChangedCommand"],
    requireAll = false
)
fun onScrollChangeCommand(
    recyclerView: RecyclerView,
    onScrollChangeCommand: BindingConsumer<ScrollDataWrapper?>?,
    onScrollStateChangedCommand: BindingConsumer<Int?>?
) {
    recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        private var state = 0
        private lateinit var wrapper: ScrollDataWrapper
        override fun onScrolled(
            recyclerView: RecyclerView,
            dx: Int,
            dy: Int
        ) {
            super.onScrolled(recyclerView, dx, dy)
            if (onScrollChangeCommand != null) {
                if (!this::wrapper.isInitialized) {
                    wrapper = ScrollDataWrapper()
                }
                wrapper.scrollX = dx.toFloat()
                wrapper.scrollY = dy.toFloat()
                wrapper.state = state
                onScrollChangeCommand.call(wrapper)
            }
        }

        override fun onScrollStateChanged(
            recyclerView: RecyclerView,
            newState: Int
        ) {
            super.onScrollStateChanged(recyclerView, newState)
            state = newState
            onScrollStateChangedCommand?.call(newState)
        }
    })
}

class ScrollDataWrapper {
    var scrollX = 0f
    var scrollY = 0f
    var state = 0
    override fun toString(): String {
        return "ScrollDataWrapper{" +
                "scrollX=" + scrollX +
                ", scrollY=" + scrollY +
                ", state=" + state +
                '}'
    }
}