package com.project.base.ui.widget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.Size;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @description 适配,appBarLayout加载ScrollView嵌套ViewPager冲突
 */
public class NestedScrollCoordinatorLayout extends CoordinatorLayout implements NestedScrollingChild {
    private static final String TAG = NestedScrollCoordinatorLayout.class.getSimpleName();

    /**
     * {@link #setPassMode(int)}常量。选择此选项后，滚动事件将传递到父流，同时传递到此Coordinator子级。
     */
    public static final int PASS_MODE_BOTH = 0;

    /**
     * {@link #setPassMode(int)}常量。选择此选项后，滚动事件将传递到父流，如果未使用，则滚动事件将继续传递到此Coordinator子级。
     */
    public static final int PASS_MODE_PARENT_FIRST = 1;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({PASS_MODE_BOTH, PASS_MODE_PARENT_FIRST})
    public @interface PassMode {}

    private NestedScrollingChildHelper helper;
    private DummyBehavior dummyBehavior;

    public NestedScrollCoordinatorLayout(Context context) {
        super(context); i();
    }

    public NestedScrollCoordinatorLayout(Context context, AttributeSet attrs) {
        super(context, attrs); i();
    }

    public NestedScrollCoordinatorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr); i();
    }

    private void i() {
        helper = new NestedScrollingChildHelper(this);
        setNestedScrollingEnabled(true);
        //添加一个将接收内部触摸事件的虚拟视图。
        View dummyView = new View(getContext());
        dummyBehavior = new DummyBehavior();
        //dummyView识别为“最高”并接收事件
        //在任何其他视图之前
        ViewCompat.setElevation(dummyView, ViewCompat.getElevation(this));
        //确保它不适合窗口，否则它将消耗AppBarLayout之前的插图。
        dummyView.setFitsSystemWindows(false);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setBehavior(dummyBehavior);
        addView(dummyView, params);
    }

    /**
     * 设置此协调器的通过模式。
     * @see #PASS_MODE_BOTH
     * @see #PASS_MODE_PARENT_FIRST
     *
     * @param mode 滚动事件所需的通过模式。
     */
    public void setPassMode(@PassMode int mode) {
        if (dummyBehavior != null) dummyBehavior.setPassMode(mode);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        helper.setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return helper.isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return helper.startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        helper.stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return helper.hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable @Size(value = 2) int[] consumed, @Nullable @Size(value = 2) int[] offsetInWindow) {
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return helper.dispatchNestedPreFling(velocityX, velocityY);
    }

    /**
     * 此行为已分配给该底部工作表布局内的虚拟MATCH_PARENT视图。通过此行为，虚拟视图可以侦听触摸滚动事件。我们的目标是将它们传播到父流
     *
     * 必须手动完成此操作，因为默认情况下，CoordinatorLayouts不会将滚动事件传播到其父级。
     * 这对其他CoordinatorLayouts中的CoordinatorLayouts不利，因为协调工作在很大程度上依赖于滚动事件。
     *
     * @param <DummyView> 确保它不是启用嵌套滚动的视图，否则将中断
     */
    private static class DummyBehavior<DummyView extends View> extends Behavior<DummyView> {

        @PassMode private int mode = PASS_MODE_PARENT_FIRST;
        private final int[] cache = new int[2];

        DummyBehavior() {}

        void setPassMode(@PassMode int mode) {
            this.mode = mode;
        }

        @Override
        public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DummyView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
            NestedScrollCoordinatorLayout sheet = (NestedScrollCoordinatorLayout) coordinatorLayout;
            return sheet.startNestedScroll(axes);
        }


        @Override
        public void onStopNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DummyView child, @NonNull View target, int type) {
            NestedScrollCoordinatorLayout sheet = (NestedScrollCoordinatorLayout) coordinatorLayout;
            sheet.stopNestedScroll();
        }

        @Override
        // 向上移动手指时，dy> 0
        public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DummyView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
            NestedScrollCoordinatorLayout sheet = (NestedScrollCoordinatorLayout) coordinatorLayout;
            if (mode == PASS_MODE_PARENT_FIRST) {
                sheet.dispatchNestedPreScroll(dx, dy, consumed, null);
            } else if (mode == PASS_MODE_BOTH) {
                cache[0] = consumed[0];
                cache[1] = consumed[1];
                sheet.dispatchNestedPreScroll(dx, dy, cache, null);
            }
        }


        @Override
        public boolean onNestedPreFling(@NonNull CoordinatorLayout coordinatorLayout, @NonNull DummyView child, @NonNull View target, float velocityX, float velocityY) {
            NestedScrollCoordinatorLayout sheet = (NestedScrollCoordinatorLayout) coordinatorLayout;
            boolean s = sheet.dispatchNestedPreFling(velocityX, velocityY);
            if (mode == PASS_MODE_PARENT_FIRST) {
                return s;
            }
            return false;
        }

        //不需要onNestedScroll和onNestedFling
    }
}
