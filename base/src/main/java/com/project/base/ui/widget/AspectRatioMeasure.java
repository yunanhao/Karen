package com.project.base.ui.widget;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

public class
AspectRatioMeasure {
    /**
     * Holder for width and height measure specs.
     */
    public static class Spec {
        public int width;
        public int height;
    }

    /**
     * Updates the given measure spec with respect to the aspect ratio.
     *
     * @param spec          in/out measure spec to be updated
     * @param aspectRatio   desired aspect ratio
     * @param layoutParams  view's layout params
     * @param widthPadding  view's left + right padding
     * @param heightPadding view's top + bottom padding
     */
    public static void updateMeasureSpec(
            Spec spec,
            float aspectRatio,
            @Nullable ViewGroup.LayoutParams layoutParams,
            int widthPadding,
            int heightPadding) {
        if (aspectRatio <= 0 || layoutParams == null) {
            return;
        }
        if (shouldAdjust(layoutParams.height)) {
            int widthSpecSize = View.MeasureSpec.getSize(spec.width);
            int desiredHeight = (int) ((widthSpecSize - widthPadding) / aspectRatio + heightPadding);
            int resolvedHeight = View.resolveSize(desiredHeight, spec.height);
            spec.height = View.MeasureSpec.makeMeasureSpec(resolvedHeight, View.MeasureSpec.EXACTLY);
        } else if (shouldAdjust(layoutParams.width)) {
            int heightSpecSize = View.MeasureSpec.getSize(spec.height);
            int desiredWidth = (int) ((heightSpecSize - heightPadding) * aspectRatio + widthPadding);
            int resolvedWidth = View.resolveSize(desiredWidth, spec.width);
            spec.width = View.MeasureSpec.makeMeasureSpec(resolvedWidth, View.MeasureSpec.EXACTLY);
        }
    }

    private static boolean shouldAdjust(int layoutDimension) {
        // Note: wrap_content is supported for backwards compatibility, but should not be used.
        return layoutDimension == 0 || layoutDimension == ViewGroup.LayoutParams.WRAP_CONTENT;
    }
}
