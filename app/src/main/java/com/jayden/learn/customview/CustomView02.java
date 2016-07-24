package com.jayden.learn.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2016/7/24.
 */
public class CustomView02 extends View {

    private float mTextSize;
    private String mText;
    private Bitmap mImage;
    private Context mContext;

    private Rect mBound;
    private Paint mPaint;

    public CustomView02(Context context) {
        this(context, null);
    }

    public CustomView02(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomView02(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView02, defStyleAttr, 0);
        int indexCount = array.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.CustomView02_j_text:
                    mText = array.getString(attr);
                    break;
                case R.styleable.CustomView02_j_textSize:
                    mTextSize = array.getDimension(attr, 10f);
                    break;
                case R.styleable.CustomView02_j_src:
                    mImage = BitmapFactory.decodeResource(getResources(), array.getResourceId(attr, 0));
                    break;
            }
        }
        array.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextSize(mTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mText, 0, mText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            int maxWidth = Math.max(mImage.getWidth(), mBound.width());
            width = maxWidth + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = mImage.getHeight() + mBound.height() + getPaddingTop() + getPaddingBottom();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int imageX = (getWidth() - mImage.getWidth()) / 2;
        int imageY = (getHeight() - (mImage.getHeight() + mBound.height())) / 2;
        canvas.drawBitmap(mImage, imageX, imageY, mPaint);

        int textX = (getWidth() - mBound.width()) / 2;
        int textY = imageY + mImage.getHeight();
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(textX, textY, textX + mBound.width(), textY + mBound.height(), mPaint);
        mPaint.setColor(Color.RED);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        canvas.drawText(mText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 - fontMetrics.descent + (fontMetrics.bottom - fontMetrics.top) / 2, mPaint);
    }
}
