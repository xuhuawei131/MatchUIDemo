package com.xuhuawei.matchuilibrary.view.scales;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class ScaleMarginButton extends android.support.v7.widget.AppCompatButton {
    private ScaleViewHelper mRatioViewHelper;
    public ScaleMarginButton(Context context) {
        super(context);
    }

    public ScaleMarginButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mRatioViewHelper=new ScaleViewHelper(this,attrs);
    }

    public ScaleMarginButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
