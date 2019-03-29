package com.xuhuawei.matchuilibrary.view.scales;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.xuhuawei.matchuilibrary.view.BaseMatchUIHelper;


/**
 * Created by xuhuawei on 2017/12/29.
 */

public class ScaleMarginFrameLayout extends FrameLayout {

    private BaseMatchUIHelper mRatioViewHelper;

    public ScaleMarginFrameLayout(Context context) {
        this(context,null);
    }

    public ScaleMarginFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ScaleMarginFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRatioViewHelper = new ScaleViewHelper(this, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec),
//                getDefaultSize(0, heightMeasureSpec));

        int[] value = mRatioViewHelper.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasureSpec = value[0];
        heightMeasureSpec = value[1];
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
