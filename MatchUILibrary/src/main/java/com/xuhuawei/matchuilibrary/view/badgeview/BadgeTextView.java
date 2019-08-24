package com.xuhuawei.matchuilibrary.view.badgeview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import com.xuhuawei.matchuilibrary.R;


/**
 * Created by xuhuawei on 2017/6/27 0027.
 */

public class BadgeTextView extends TextView {
    private static final String TAG="BadgeTextView";
    //background color
    private int mNormalBackgroundColor = 0;
    private int mPressedBackgroundColor = 0;
    private int mUnableBackgroundColor = 0;

    private GradientDrawable mNormalBackground;
    private GradientDrawable mPressedBackground;
    private GradientDrawable mUnableBackground;

    private int[][] states;
    private StateListDrawable mStateBackground;

    private float SMALL_SIZE;
    private float DOU_SMALL_SIZE;
    private float BIG_SIZE;
    private static final float TEXT_SIZE = 20f;

    private float textSizeVerticalRatio;
    private float textSizeHorizionRatio;

    private Paint mTextPaint;
    private float mMinTextSize = 8;    //最小的字体大小

    private int heightMode;//测试模式

    public BadgeTextView(Context context) {
        super(context);
        init(null);
    }

    public BadgeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BadgeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        SMALL_SIZE = UIUtils.dip2px(getContext(), 2);
        DOU_SMALL_SIZE=SMALL_SIZE+SMALL_SIZE;
        BIG_SIZE = UIUtils.dip2px(getContext(), 8);


        setLines(1);
        setTextColor(0xffffffff);
        mTextPaint = new TextPaint();
        mTextPaint.set(this.getPaint());

        states = new int[4][];
        Drawable drawable = getBackground();
        if (drawable != null && drawable instanceof StateListDrawable) {
            mStateBackground = (StateListDrawable) drawable;
        } else {
            mStateBackground = new StateListDrawable();
        }

        mNormalBackground = new GradientDrawable();
        mPressedBackground = new GradientDrawable();
        mUnableBackground = new GradientDrawable();

        //pressed, focused, normal, unable
        states[0] = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
        states[1] = new int[]{android.R.attr.state_enabled, android.R.attr.state_focused};
        states[3] = new int[]{-android.R.attr.state_enabled};
        states[2] = new int[]{android.R.attr.state_enabled};

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BadgeTextView);


        textSizeVerticalRatio = a.getFloat(R.styleable.BadgeTextView_textSizeVerticalRatio, -1);
        textSizeHorizionRatio = a.getFloat(R.styleable.BadgeTextView_textSizeHorizionRatio, -1);

        if (textSizeVerticalRatio < 0 || textSizeHorizionRatio < 0) {
            textSizeVerticalRatio = SMALL_SIZE / TEXT_SIZE;
            textSizeHorizionRatio = BIG_SIZE / TEXT_SIZE;
        }

        //set background color
        mNormalBackgroundColor = a.getColor(R.styleable.BadgeTextView_normalBackgroundColor, 0xffff6666);
        mPressedBackgroundColor = a.getColor(R.styleable.BadgeTextView_pressedBackgroundColor, 0xffff6666);
        mUnableBackgroundColor = a.getColor(R.styleable.BadgeTextView_unableBackgroundColor, 0xffff6666);
        mNormalBackground.setColor(mNormalBackgroundColor);
        mPressedBackground.setColor(mPressedBackgroundColor);
        mUnableBackground.setColor(mUnableBackgroundColor);


        //set background
        mStateBackground.addState(states[0], mPressedBackground);
        mStateBackground.addState(states[1], mPressedBackground);
        mStateBackground.addState(states[3], mUnableBackground);
        mStateBackground.addState(states[2], mNormalBackground);
        setBackgroundDrawable(mStateBackground);
        a.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int measureWidth = getMeasuredWidth();
        int measureHeight = getMeasuredHeight();

        heightMode = MeasureSpec.getMode(heightMeasureSpec);
        float heightSize = MeasureSpec.getSize(heightMeasureSpec);




        if (heightMode == MeasureSpec.EXACTLY) {
            refitText(this.getText().toString(), measureHeight-DOU_SMALL_SIZE);
        }
        float textSize = getTextSize();
        float smalSize = textSize * textSizeVerticalRatio;
        float bigSize = textSize * textSizeHorizionRatio;
        int size = getText().length();


        if (heightMode == MeasureSpec.EXACTLY) {
            if (size <= 1) {
                measureWidth = measureHeight;
            } else {
                measureWidth += bigSize;
            }
        }else{
            if (size <= 1) {
                int maxSize = measureWidth > measureHeight ? measureWidth : measureHeight;
                maxSize += smalSize;
                measureWidth = maxSize;
                measureHeight = maxSize;
            } else {
                measureWidth += bigSize;
                measureHeight += smalSize;
            }
        }
        Log.v(TAG,"heightSize="+heightSize+" measureHeight="+measureHeight+" measureWidth="+measureWidth);
        setRadius(measureHeight / 2f);
        setMeasuredDimension(measureWidth, measureHeight);
    }

//    @Override
//    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
//        if (heightMode != MeasureSpec.EXACTLY) {
//            refitText(text.toString(), this.getHeight());   //textview视图的高度
//        }
//        super.onTextChanged(text, start, lengthBefore, lengthAfter);
//    }

    private float refitText(String textString, float height) {
        if (height > 0) {
            float availableHeight = height - this.getPaddingTop() - this.getPaddingBottom();   //减去边距为字体的实际高度
            float trySize = height;
            mTextPaint.setTextSize(trySize);
            while (mTextPaint.descent() - mTextPaint.ascent() > availableHeight) {   //测量的字体高度过大，不断地缩放
                trySize -= 1;  //字体不断地减小来适应
                if (trySize <= mMinTextSize) {
                    trySize = mMinTextSize;  //最小为这个
                    break;
                }
                mTextPaint.setTextSize(trySize);
            }
            Log.v(TAG,"trySize="+trySize);
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
            return trySize;
        }else{
            return 8;
        }
    }

    public void setRadius(float radius) {
        mNormalBackground.setCornerRadius(radius);
        mPressedBackground.setCornerRadius(radius);
        mUnableBackground.setCornerRadius(radius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //解决文本无法垂直居中的问题，动态绘制获取文本高度和TextView的宽度，计算出居中的位置 (getHeight() - textWidth) / 2）
        float textWidth = getPaint().measureText(getText().toString());
        canvas.translate((getWidth() - textWidth) / 2, 0);
        super.onDraw(canvas);
    }

}
