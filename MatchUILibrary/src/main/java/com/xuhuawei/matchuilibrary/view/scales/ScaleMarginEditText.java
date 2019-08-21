package com.xuhuawei.matchuilibrary.view.scales;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

public class ScaleMarginEditText extends EditText {
    private ScaleViewHelper mRatioViewHelper;
    public ScaleMarginEditText(Context context) {
        super(context);
    }

    public ScaleMarginEditText(Context context,  AttributeSet attrs) {
        super(context, attrs);
        mRatioViewHelper=new ScaleViewHelper(this,attrs);
    }

    public ScaleMarginEditText(Context context,  AttributeSet attrs, int defStyleAttr) {
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
