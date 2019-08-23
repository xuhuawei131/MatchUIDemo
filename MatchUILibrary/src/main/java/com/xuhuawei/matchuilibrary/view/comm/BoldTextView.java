package com.xuhuawei.matchuilibrary.view.comm;

import android.content.Context;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

public class BoldTextView extends TextView {

    public BoldTextView(Context context) {
        super(context);
        init();
    }

    public BoldTextView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BoldTextView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private void init(){
        Paint paint=getPaint();
        paint.setFakeBoldText(true);
    }
}
