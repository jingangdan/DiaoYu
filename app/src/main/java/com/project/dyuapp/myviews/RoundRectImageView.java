package com.project.dyuapp.myviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.project.dyuapp.myutils.BitmapUtil;

/**
 * 圆角矩形图片
 *
 * @author zhouzhan
 */
@SuppressLint("DrawAllocation")
public class RoundRectImageView extends ImageView {
    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角的大小
     */
    private int mBorderRadius = 10;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;
    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    public RoundRectImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

    }

    public RoundRectImageView(Context context) {
        this(context, null);
        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = mWidth / 2;
        setMeasuredDimension(mWidth, mWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // 圆角图片的范围
        mRoundRect = new RectF(0, 0, w, h);
    }

    /**
     * 初始化BitmapShader
     */
    private void initShader() {
        Drawable drawable = getDrawable();
        if (drawable == null) {
            return;
        }

        Bitmap bmp = BitmapUtil.drawableToBitamp(drawable);
        // 将bmp作为着色器，就是在指定区域内绘制bmp
        mBitmapShader = new BitmapShader(bmp, TileMode.CLAMP, TileMode.CLAMP);
        float scale = 1.0f;
        if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight())) {
            // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
            scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
                    getHeight() * 1.0f / bmp.getHeight());
        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        // 设置shader
        mBitmapPaint.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null) {
            return;
        }
        initShader();
        canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
                mBitmapPaint);

    }

}
