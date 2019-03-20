package com.xuhuawei.matchuidemo.view.scales;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xuhuawei.matchuidemo.view.BaseMatchUIHelper;


/**
 * Created by xuhuawei on 2017/12/29.
 */

public class ScaleMarginImageView extends ImageView {

    private BaseMatchUIHelper mRatioViewHelper;

    public ScaleMarginImageView(Context context) {
        this(context,null);
    }

    public ScaleMarginImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public ScaleMarginImageView(Context context, AttributeSet attrs, int defStyleAttr) {
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
