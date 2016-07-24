package com.jayden.learn.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/7/17.
 * TextView 文字居中：http://blog.csdn.net/u014702653/article/details/51985821
 */
public class NoPaddingTextView extends View {

    private static final String TAG = NoPaddingTextView.class.getSimpleName();
    private float mJTextSize;
    private String mJText = "";
    private Paint mPaint = null;
    private Rect mBound = null;

    public NoPaddingTextView(Context context) {
        this(context, null);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoPaddingTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NoPaddingTextView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.NoPaddingTextView_j_textSize:
                    mJTextSize = array.getDimension(attr, 10f);
                    break;
                case R.styleable.NoPaddingTextView_j_text:
                    mJText = array.getString(attr);
                    break;
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(mJTextSize);
        mBound = new Rect();
        mPaint.getTextBounds(mJText, 0, mJText.length(), mBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
       int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            mPaint.setTextSize(mJTextSize);
            mPaint.getTextBounds(mJText, 0, mJText.length(), mBound);
            width = getPaddingLeft() + mBound.width() + getPaddingRight();
            if(widthMode == MeasureSpec.AT_MOST){
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            mPaint.setTextSize(mJTextSize);
            mPaint.getTextBounds(mJText, 0, mJText.length(), mBound);
            height = getPaddingTop() + mBound.height() + getPaddingBottom();
            if(heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.GREEN);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);

        mPaint.setColor(Color.RED);
        Log.d(TAG, "onDraw getHeight(): " + getHeight() + " mBound.height(): " + mBound.height() + " getWidth(): " + getWidth() + " mBound.width: " + mBound.width());
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        canvas.drawText(mJText, getWidth() / 2 - mBound.width() / 2, getHeight() / 2 - fontMetrics.descent + (fontMetrics.bottom - fontMetrics.top) / 2, mPaint);
    }

}
