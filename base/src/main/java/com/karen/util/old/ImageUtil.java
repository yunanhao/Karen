package com.karen.util.old;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

public class ImageUtil {

    //放大缩小图片
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidht = ((float)w / width);
        float scaleHeight = ((float)h / height);
        matrix.postScale(scaleWidht, scaleHeight);
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newbmp;
    }

    //将Drawable转化为Bitmap
    public static Bitmap drawableToBitmap(Drawable drawable) {
        int width = drawable.getIntrinsicWidth();
        int height = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height,
            drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, width, height);
        drawable.draw(canvas);
        return bitmap;

    }

    //获得圆角图片的方法
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, float roundPx) {

        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
            .getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        /**
         * 画一个圆角矩形
         * rectF: 矩形
         * roundPx 圆角在x轴上或y轴上的半径
         */
        canvas.drawRoundRect(rectF, roundPx, roundPx + 10, paint);
        //设置两张图片相交时的模式
        //setXfermode前的是 dst 之后的是src
        //在正常的情况下，在已有的图像上绘图将会在其上面添加一层新的形状。
        //如果新的Paint是完全不透明的，那么它将完全遮挡住下面的Paint；
        //PorterDuffXfermode就可以来解决这个问题
        //canvas原有的图片 可以理解为背景 就是dst
        //新画上去的图片 可以理解为前景 就是src
        //      paint.setXfermode(new PorterDuffXfermode(Mode.SRC_OUT));
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }

    //获得带倒影的图片方法
    public static Bitmap createReflectionImageWithOrigin(Bitmap bitmap) {
        // 图片与倒影间隔距离
        final int reflectionGap = 4;
        // 图片的宽度
        int width = bitmap.getWidth();
        // 图片的高度
        int height = bitmap.getHeight();

        Matrix matrix = new Matrix();
        // 图片缩放，x轴变为原来的1倍，y轴为-1倍,实现图片的反转
        matrix.preScale(1, -1);
        // 创建反转后的图片Bitmap对象，图片高是原图的一半。
        Bitmap reflectionImage = Bitmap.createBitmap(bitmap,
            0, height / 2, width, height / 2, matrix, false);
        // 创建标准的Bitmap对象，宽和原图一致，高是原图的1.5倍。 可以理解为这张图将会在屏幕上显示 是原图和倒影的合体
        Bitmap bitmapWithReflection = Bitmap.createBitmap(width, (height + height / 2),
            Config.ARGB_8888);
        // 构造函数传入Bitmap对象，为了在图片上画图
        Canvas canvas = new Canvas(bitmapWithReflection);
        // 画原始图片
        canvas.drawBitmap(bitmap, 0, 0, null);
        // 画间隔矩形
        Paint deafalutPaint = new Paint();
        canvas.drawRect(0, height, width, height + reflectionGap,
            deafalutPaint);
        // 画倒影图片
        canvas.drawBitmap(reflectionImage, 0, height + reflectionGap, null);
        // 实现倒影渐变效果
        Paint paint = new Paint();
        LinearGradient shader = new LinearGradient(0,
            bitmap.getHeight(), 0, bitmapWithReflection.getHeight()
            + reflectionGap, 0x70ffffff, 0x00ffffff, TileMode.CLAMP);
        paint.setShader(shader);

        // Set the Transfer mode to be porter duff and destination in
        // 覆盖效果
        paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
        // Draw a rectangle using the paint with our linear gradient
        canvas.drawRect(0, height, width, bitmapWithReflection.getHeight()
            + reflectionGap, paint);

        return bitmapWithReflection;
    }

}
