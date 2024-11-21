package com.example.sportsnews.util;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.Drawable;

public class JerseyDrawable extends Drawable {

    private Paint paint;
    private Path jerseyPath;

    public JerseyDrawable(int color) {
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.FILL);

        jerseyPath = new Path();
        // 定义球衣形状路径
        // 球衣的轮廓（以矩形为基础，但更符合球衣的形状）
        jerseyPath.moveTo(50, 10); // 起点
        jerseyPath.lineTo(150, 10); // 上边
        jerseyPath.lineTo(160, 50); // 右上角
        jerseyPath.lineTo(140, 150); // 右侧
        jerseyPath.lineTo(60, 150); // 底部
        jerseyPath.lineTo(40, 50); // 左上角
        jerseyPath.close(); // 封闭路径

    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawPath(jerseyPath, paint);
    }

    @Override
    public void setAlpha(int alpha) {
        paint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(android.graphics.ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        return paint.getAlpha();
    }
}
