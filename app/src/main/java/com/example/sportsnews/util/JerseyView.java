package com.example.sportsnews.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

public class JerseyView extends View {

    private Paint paint;
    private Path jerseyPath;
    private Paint textPaint;
    private String name = "USER";
    private String number = "10";
    private float scaleFactor = 2.3f;  // 设置缩放因子（1.0表示原始大小，1.5表示放大1.5倍）

    public JerseyView(Context context) {
        super(context);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        init();
    }

    public JerseyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JerseyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(0xFF00FF00); // 默认颜色为绿色
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        jerseyPath = new Path();
        // 球衣轮廓
        jerseyPath.moveTo(50, 10); // 起点
        jerseyPath.lineTo(150, 10); // 上边
        jerseyPath.lineTo(160, 50); // 右上角
        jerseyPath.lineTo(140, 150); // 右侧
        jerseyPath.lineTo(60, 150); // 底部
        jerseyPath.lineTo(40, 50); // 左上角
        jerseyPath.close(); // 封闭路径

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK); // 白色文字
//        textPaint.setTextSize(30f); // 设置文字大小
        textPaint.setStrokeWidth(5);
        textPaint.setTextAlign(Paint.Align.CENTER); // 文字居中
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 获取视图的宽高
        int viewWidth = getWidth();
        int viewHeight = getHeight();

        // 计算球衣形状的原始宽高
        float originalJerseyWidth = 160f; // 球衣的原始宽度
        float originalJerseyHeight = 160f; // 球衣的原始高度

        // 计算缩放后的宽高
        float jerseyWidth = originalJerseyWidth * scaleFactor;
        float jerseyHeight = originalJerseyHeight * scaleFactor;

        // 计算居中的偏移量
        float offsetX = (viewWidth - jerseyWidth) / 2;
        float offsetY = (viewHeight - jerseyHeight) / 2;

        // 移动画布到居中的位置
        canvas.translate(offsetX, offsetY);

        // 缩放画布
        canvas.scale(scaleFactor, scaleFactor); // 放大球衣

        // 绘制球衣
        canvas.drawPath(jerseyPath, paint);

        // 设置文本的大小，以便在缩放后保持合适的显示
        textPaint.setTextSize(30f);  // 根据缩放因子调整文字大小

        // 计算缩放后的文本位置，确保它们在正确的位置
        float numberX = (float) offsetX / 2;
        float numberY = (float) offsetY / 3;
        float nameX = (float) offsetX / 2;
        float nameY = (float) (offsetY * 2) / 3;

        // 绘制数字
        if (!number.isEmpty()) {
            canvas.drawText(number, numberX-57, numberY+70, textPaint);// 显示号码
        }

        // 绘制姓名
        if (!name.isEmpty()) {
            canvas.drawText(name, nameX-55, nameY+12, textPaint); // 显示姓名
        }
    }


    // 设置球衣的颜色
    public void setJerseyColor(int color) {
        paint.setColor(color);
        invalidate(); // 重绘视图
    }

    // 设置球衣缩放因子
    public void setScaleFactor(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        invalidate(); // 重绘视图
    }

    // 设置球衣上的姓名
    public void setName(String name) {
        this.name = name;
        postInvalidate(); // 更新视图
    }

    // 设置球衣上的号码
    public void setNumber(String number) {
        this.number = number;
        postInvalidate(); // 更新视图
    }
}
