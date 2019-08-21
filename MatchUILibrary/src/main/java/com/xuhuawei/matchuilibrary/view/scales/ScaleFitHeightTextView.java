package com.xuhuawei.matchuilibrary.view.scales;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;


/**
 * Created by xuhuawei on 2017/11/30.
 */

public class ScaleFitHeightTextView extends TextView {
    private Paint mTextPaint;
    private float mMaxTextSize; // 获取当前所设置文字大小作为最大文字大小
    private float mMinTextSize = 8;    //最小的字体大小

    private ScaleViewHelper mRatioViewHelper;

    public ScaleFitHeightTextView(Context context) {
        super(context);
    }
    public ScaleFitHeightTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public ScaleFitHeightTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);init(attrs);
    }
    private void init(AttributeSet attrs) {
        setGravity(getGravity() | Gravity.CENTER_VERTICAL); // 默认水平居中
        setLines(1);

        mTextPaint = new TextPaint();
        mTextPaint.set(this.getPaint());
        //默认的大小是设置的大小，如果撑不下了 就改变
        mMaxTextSize = this.getTextSize();

        mRatioViewHelper = new ScaleViewHelper(this, attrs);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] value = mRatioViewHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasureSpec = value[0];
        heightMeasureSpec = value[1];
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    //文字改变的时候
    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        refitText(text.toString(), this.getHeight());   //textview视图的高度
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    private void refitText(String textString, int height) {
        if (height > 0) {
            int availableHeight = height - this.getPaddingTop() - this.getPaddingBottom();   //减去边距为字体的实际高度
            float trySize = height;
            mTextPaint.setTextSize(trySize);
            while (mTextPaint.descent()-mTextPaint.ascent() > availableHeight) {   //测量的字体高度过大，不断地缩放
                trySize -= 1;  //字体不断地减小来适应
                if (trySize <= mMinTextSize) {
                    trySize = mMinTextSize;  //最小为这个
                    break;
                }
                mTextPaint.setTextSize(trySize);
            }
            super.setTextSize(TypedValue.COMPLEX_UNIT_PX, trySize);
        }
    }

    //大小改变的时候
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w,h,oldw,oldh);
        if (h != oldh) {
            refitText(this.getText().toString(), h);
        }
    }
}