package com.xuhuawei.matchuilibrary.view.scales;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ScaleMarginButton extends Button {
    private ScaleViewHelper mRatioViewHelper;
    public ScaleMarginButton(Context context) {
        super(context);
    }

    public ScaleMarginButton(Context context,  AttributeSet attrs) {
        super(context, attrs);
        mRatioViewHelper=new ScaleViewHelper(this,attrs);
    }

    public ScaleMarginButton(Context context,  AttributeSet attrs, int defStyleAttr) {
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
