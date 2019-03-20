package com.xuhuawei.matchuidemo.view.scales;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.EditText;

public class ScaleMarginEditText extends android.support.v7.widget.AppCompatEditText {
    private ScaleViewHelper mRatioViewHelper;
    public ScaleMarginEditText(Context context) {
        super(context);
    }

    public ScaleMarginEditText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRatioViewHelper=new ScaleViewHelper(this,attrs);
    }

    public ScaleMarginEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int[] value = mRatioViewHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasureSpec = value[0];
        heightMeasureSpec = value[1];
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
