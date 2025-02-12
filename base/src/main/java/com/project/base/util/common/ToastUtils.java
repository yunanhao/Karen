package com.project.base.util.common;

import static androidx.core.app.NotificationManagerCompat.*;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.CallSuper;
import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.core.app.NotificationManagerCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.project.base.R;

/**
 * <pre>
 *     author: Blankj
 *     blog  : <a href="http://blankj.com">...</a>
 *     time  : 2016/09/29
 *     desc  : utils about toast
 * </pre>
 */
public final class ToastUtils {

    private static final int COLOR_DEFAULT = 0xFEFFFFFF;
    private static final String NULL = "null";

    private static IToast iToast;
    private static int sGravity = -1;
    private static int sXOffset = -1;
    private static int sYOffset = -1;
    private static int sBgColor = COLOR_DEFAULT;
    private static int sBgResource = -1;
    private static int sMsgColor = COLOR_DEFAULT;
    private static int sMsgTextSize = -1;

    private ToastUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Set the gravity.
     *
     * @param gravity The gravity.
     * @param xOffset X-axis offset, in pixel.
     * @param yOffset Y-axis offset, in pixel.
     */
    public static void setGravity(final int gravity, final int xOffset, final int yOffset) {
        sGravity = gravity;
        sXOffset = xOffset;
        sYOffset = yOffset;
    }

    /**
     * Set the color of background.
     *
     * @param backgroundColor The color of background.
     */
    public static void setBgColor(@ColorInt final int backgroundColor) {
        sBgColor = backgroundColor;
    }

    /**
     * Set the resource of background.
     *
     * @param bgResource The resource of background.
     */
    public static void setBgResource(@DrawableRes final int bgResource) {
        sBgResource = bgResource;
    }

    /**
     * Set the color of message.
     *
     * @param msgColor The color of message.
     */
    public static void setMsgColor(@ColorInt final int msgColor) {
        sMsgColor = msgColor;
    }

    /**
     * Set the text size of message.
     *
     * @param textSize The text size of message.
     */
    public static void setMsgTextSize(final int textSize) {
        sMsgTextSize = textSize;
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param text The text.
     */
    public static void showShort(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showShort(@StringRes final int resId) {
        show(resId, Toast.LENGTH_SHORT);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showShort(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the toast for a short period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showShort(final String format, final Object... args) {
        show(format, Toast.LENGTH_SHORT, args);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param text The text.
     */
    public static void showLong(final CharSequence text) {
        show(text == null ? NULL : text, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     */
    public static void showLong(@StringRes final int resId) {
        show(resId, Toast.LENGTH_LONG);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param resId The resource id for text.
     * @param args  The args.
     */
    public static void showLong(@StringRes final int resId, final Object... args) {
        show(resId, Toast.LENGTH_LONG, args);
    }

    /**
     * Show the toast for a long period of time.
     *
     * @param format The format.
     * @param args   The args.
     */
    public static void showLong(final String format, final Object... args) {
        show(format, Toast.LENGTH_LONG, args);
    }

    /**
     * Show custom toast for a short period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomShort(@LayoutRes final int layoutId) {
        return showCustomShort(getView(layoutId));
    }

    /**
     * Show custom toast for a short period of time.
     *
     * @param view The view of toast.
     */
    public static View showCustomShort(final View view) {
        show(view, Toast.LENGTH_SHORT);
        return view;
    }

    /**
     * Show custom toast for a long period of time.
     *
     * @param layoutId ID for an XML layout resource to load.
     */
    public static View showCustomLong(@LayoutRes final int layoutId) {
        return showCustomLong(getView(layoutId));
    }

    /**
     * Show custom toast for a long period of time.
     *
     * @param view The view of toast.
     */
    public static View showCustomLong(final View view) {
        show(view, Toast.LENGTH_LONG);
        return view;
    }

    /**
     * Cancel the toast.
     */
    public static void cancel() {
        if (iToast != null) {
            iToast.cancel();
        }
    }

    private static void show(final int resId, final int duration) {
        show(resId, duration, (Object) null);
    }

    private static void show(final int resId, final int duration, final Object... args) {
        try {
            CharSequence text = Utils.getApp().getResources().getText(resId);
            if (args != null && args.length > 0) {
                text = String.format(text.toString(), args);
            }
            show(text, duration);
        } catch (Exception ignore) {
            show(String.valueOf(resId), duration);
        }
    }

    private static void show(final String format, final int duration, final Object... args) {
        String text = format;
        if (text == null) {
            text = NULL;
        } else {
            if (args != null && args.length > 0) {
                text = String.format(format, args);
            }
        }
        show(text, duration);
    }

    private static void show(final CharSequence text, final int duration) {
        show(null, text, duration);
    }

    private static void show(final View view, final int duration) {
        show(view, null, duration);
    }

    private static void show(@Nullable final View view, final CharSequence text,
                             final int duration) {
        UtilsBridge.runOnUiThread(() -> {
            cancel();
            iToast = newToast();
            if (view != null) {
                iToast.setView(view);
            } else {
                iToast.setMsgView(text);
            }
            iToast.setDuration(duration);
            if (sGravity != -1 || sXOffset != -1 || sYOffset != -1) {
                iToast.setGravity(sGravity, sXOffset, sYOffset);
            }
            iToast.show();
        });
    }

    private static IToast newToast() {
        if (!UtilsBridge.isGrantedDrawOverlays()) {
            return new SystemToast(new Toast(Utils.getApp()));
        }
        return new ToastWithoutNotification(new Toast(Utils.getApp()));
    }

    private static View getView(@LayoutRes final int layoutId) {
        LayoutInflater inflate =
                (LayoutInflater) Utils.getApp().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return inflate.inflate(layoutId, null);
    }

    static class SystemToast extends AbsToast {

        SystemToast(Toast toast) {
            super(toast);
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.N_MR1) {
                try {
                    //noinspection JavaReflectionMemberAccess
                    Field mTNField = Toast.class.getDeclaredField("mTN");
                    mTNField.setAccessible(true);
                    Object mTN = mTNField.get(toast);
                    Field mTNmHandlerField = mTNField.getType().getDeclaredField("mHandler");
                    mTNmHandlerField.setAccessible(true);
                    Handler tnHandler = (Handler) mTNmHandlerField.get(mTN);
                    mTNmHandlerField.set(mTN, new SafeHandler(tnHandler));
                } catch (Exception ignored) {/**/}
            }
        }

        @Override
        public void show() {
            mToast.show();
        }

        @Override
        public void cancel() {
            mToast.cancel();
            super.cancel();
        }

        static class SafeHandler extends Handler {
            private final Handler impl;

            SafeHandler(Handler impl) {
                this.impl = impl;
            }

            @Override
            public void handleMessage(Message msg) {
                impl.handleMessage(msg);
            }

            @Override
            public void dispatchMessage(Message msg) {
                try {
                    impl.dispatchMessage(msg);
                } catch (Exception e) {
                    Log.e("ToastUtils", e.toString());
                }
            }
        }
    }

    static class ToastWithoutNotification extends AbsToast {

        private WindowManager mWM;

        private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();

        ToastWithoutNotification(Toast toast) {
            super(toast);
        }

        @SuppressLint("WrongConstant")
        @Override
        public void show() {
            if (mToast == null) {
                return;
            }
            boolean isActivityContext = false;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) {
                mWM = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
                mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
            } else if (UtilsBridge.isGrantedDrawOverlays()) {
                mWM = (WindowManager) Utils.getApp().getSystemService(Context.WINDOW_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    mParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                } else {
                    mParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
            } else {
                Context topActivityOrApp = UtilsBridge.getTopActivityOrApp();
                if (!(topActivityOrApp instanceof Activity)) {
                    Log.w("ToastUtils", "Couldn't get top Activity.");
                    // try to use system toast
                    new SystemToast(mToast).show();
                    return;
                }
                Activity topActivity = (Activity) topActivityOrApp;
                if (topActivity.isFinishing() || topActivity.isDestroyed()) {
                    Log.w("ToastUtils", topActivity + " is useless");
                    // try to use system toast
                    new SystemToast(mToast).show();
                    return;
                }
                isActivityContext = true;
                mWM = topActivity.getWindowManager();
                mParams.type = WindowManager.LayoutParams.LAST_APPLICATION_WINDOW;
                UtilsBridge.addActivityLifecycleCallbacks(topActivity,
                        getActivityLifecycleCallbacks());
            }

            setToastParams();

            final long duration = mToast.getDuration() == Toast.LENGTH_SHORT ? 2000 : 3500;
            if (isActivityContext) {
                UtilsBridge.runOnUiThreadDelayed(() -> setToast(duration), 300);
            } else {
                setToast(duration);
            }
        }

        private void setToastParams() {
            mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
            mParams.format = PixelFormat.TRANSLUCENT;
            mParams.windowAnimations = android.R.style.Animation_Toast;
            mParams.setTitle("ToastWithoutNotification");
            mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
            mParams.packageName = Utils.getApp().getPackageName();

            mParams.gravity = mToast.getGravity();
            if ((mParams.gravity & Gravity.HORIZONTAL_GRAVITY_MASK) == Gravity.FILL_HORIZONTAL) {
                mParams.horizontalWeight = 1.0f;
            }
            if ((mParams.gravity & Gravity.VERTICAL_GRAVITY_MASK) == Gravity.FILL_VERTICAL) {
                mParams.verticalWeight = 1.0f;
            }

            mParams.x = mToast.getXOffset();
            mParams.y = mToast.getYOffset();
            mParams.horizontalMargin = mToast.getHorizontalMargin();
            mParams.verticalMargin = mToast.getVerticalMargin();
        }

        private void setToast(long duration) {
            try {
                if (mWM != null) {
                    mWM.addView(mToastView, mParams);
                }
            } catch (Exception ignored) {/**/}

            UtilsBridge.runOnUiThreadDelayed(this::cancel, duration);
        }

        private Utils.ActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
            return new Utils.ActivityLifecycleCallbacks() {
                @Override
                public void onActivityDestroyed(@NonNull Activity activity) {
                    if (iToast == null) {
                        return;
                    }
                    activity.getWindow().getDecorView().setVisibility(View.GONE);
                    iToast.cancel();
                }
            };
        }

        @Override
        public void cancel() {
            try {
                if (mWM != null) {
                    mWM.removeViewImmediate(mToastView);
                }
            } catch (Exception ignored) {/**/}
            mWM = null;
            super.cancel();
        }
    }

    static abstract class AbsToast implements IToast {

        protected Toast mToast;
        protected View mToastView;

        AbsToast(Toast toast) {
            mToast = toast;
        }

        @Override
        public void setView(View view) {
            mToastView = view;
            mToast.setView(mToastView);
        }

        @Override
        public void setMsgView(CharSequence text) {
            mToastView = mToast.getView();
            if (mToastView == null || mToastView.findViewById(android.R.id.message) == null) {
                mToastView = ToastUtils.getView(R.layout.base_toast);
                mToast.setView(mToastView);
            }

            TextView tvMessage = mToastView.findViewById(android.R.id.message);
            tvMessage.setText(text);
            if (sMsgColor != COLOR_DEFAULT) {
                tvMessage.setTextColor(sMsgColor);
            }
            if (sMsgTextSize != -1) {
                tvMessage.setTextSize(sMsgTextSize);
            }
            setBg(tvMessage);
        }

        private void setBg(final TextView tvMsg) {
            if (sBgResource != -1) {
                mToastView.setBackgroundResource(sBgResource);
                tvMsg.setBackgroundColor(Color.TRANSPARENT);
            } else if (sBgColor != COLOR_DEFAULT) {
                Drawable tvBg = mToastView.getBackground();
                Drawable msgBg = tvMsg.getBackground();
                if (tvBg != null && msgBg != null) {
                    tvBg.setColorFilter(
                            new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                    tvMsg.setBackgroundColor(Color.TRANSPARENT);
                } else if (tvBg != null) {
                    tvBg.setColorFilter(
                            new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                } else if (msgBg != null) {
                    msgBg.setColorFilter(
                            new PorterDuffColorFilter(sBgColor, PorterDuff.Mode.SRC_IN));
                } else {
                    mToastView.setBackgroundColor(sBgColor);
                }
            }
        }

        @Override
        public View getView() {
            return mToastView;
        }

        @Override
        public void setDuration(int duration) {
            mToast.setDuration(duration);
        }

        @Override
        public void setGravity(int gravity, int xOffset, int yOffset) {
            mToast.setGravity(gravity, xOffset, yOffset);
        }

        @Override
        public void setText(int resId) {
            mToast.setText(resId);
        }

        @Override
        public void setText(CharSequence s) {
            mToast.setText(s);
        }

        @Override
        @CallSuper
        public void cancel() {
            mToast = null;
            mToastView = null;
        }
    }

    interface IToast {

        void show();

        void cancel();

        void setView(View view);

        void setMsgView(CharSequence text);

        View getView();

        void setDuration(int duration);

        void setGravity(int gravity, int xOffset, int yOffset);

        void setText(@StringRes int resId);

        void setText(CharSequence s);
    }
}