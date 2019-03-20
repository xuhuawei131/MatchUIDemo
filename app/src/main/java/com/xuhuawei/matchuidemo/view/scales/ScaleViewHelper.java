package com.xuhuawei.matchuidemo.view.scales;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.xuhuawei.matchuidemo.R;
import com.xuhuawei.matchuidemo.view.BaseMatchUIHelper;

/**
 * 以横向或者纵向的缩放比例为准进行适配
 */
public class ScaleViewHelper extends BaseMatchUIHelper {

    public static final int DEFAULT_DESIGN_WIDTH = 375;
    public static final int DEFAULT_DESIGN_HEIGHT = 667;
    public static final boolean DEFAULT_RATIO_WIDTH_BASE = true;

    public static final int MATCH_TYPE_RATIO = 0;//宽高比模式
    public static final int MATCH_TYPE_SCALE = 1;//比例缩放模式
    public static final int MATCH_TYPE_ORIGIN = 2;//原始模式

    private float widthRatio = -1;
    private float heightRatio = -1;
    //匹配模式
    private int matchType;

    private float dreamWdithLength;
    private float dreamHeightLength;

    private boolean baseWidth = true;
    private float ratioValue = 1;//宽高比值

    private float padding = -1;
    private float leftPadding = -1;
    private float topPadding = -1;
    private float rightPadding = -1;
    private float bottomPadding = -1;

    private float leftMargin = -1;
    private float topMargin = -1;
    private float rightMargin = -1;
    private float bottomMargin = -1;

    private float horizionScaleValue;
    private float verticalScaleValue;

    private int childWidthSize;
    private int childHeightSize;

    private View view;

    public ScaleViewHelper(View view, AttributeSet attrs) {
        this.view = view;
        TypedArray typedArray = view.getContext().obtainStyledAttributes(attrs, R.styleable.RatioMarginLayout);//TypedArray是一个数组容器
        matchType = typedArray.getInt(R.styleable.RatioMarginLayout_matchType, MATCH_TYPE_ORIGIN);

        widthRatio = typedArray.getFloat(R.styleable.RatioMarginLayout_widthDesign, -1);
        heightRatio = typedArray.getFloat(R.styleable.RatioMarginLayout_heightDesign, -1);
        //如果是ratio模式会使用
        baseWidth = typedArray.getBoolean(R.styleable.RatioMarginLayout_baseWidth, DEFAULT_RATIO_WIDTH_BASE);
        ratioValue = typedArray.getFloat(R.styleable.RatioMarginLayout_ratioValue, -1);

        dreamWdithLength = typedArray.getFloat(R.styleable.RatioMarginLayout_DesignScreenWidth, DEFAULT_DESIGN_WIDTH);
        dreamHeightLength = typedArray.getFloat(R.styleable.RatioMarginLayout_DesignScreenHeight, DEFAULT_DESIGN_HEIGHT);

        leftMargin = typedArray.getFloat(R.styleable.RatioMarginLayout_leftMarginDesign, 0);
        topMargin = typedArray.getFloat(R.styleable.RatioMarginLayout_topMarginDesign, 0);
        rightMargin = typedArray.getFloat(R.styleable.RatioMarginLayout_rightMarginDesign, 0);
        bottomMargin = typedArray.getFloat(R.styleable.RatioMarginLayout_bottomMarginDesign, 0);

        padding = typedArray.getFloat(R.styleable.RatioMarginLayout_paddingDesign, 0);
        leftPadding = typedArray.getFloat(R.styleable.RatioMarginLayout_leftPaddingDesign, 0);
        topPadding = typedArray.getFloat(R.styleable.RatioMarginLayout_topPaddignDesign, 0);
        rightPadding = typedArray.getFloat(R.styleable.RatioMarginLayout_rightPaddingDesign, 0);
        bottomPadding = typedArray.getFloat(R.styleable.RatioMarginLayout_bottomPaddingDesign, 0);

        typedArray.recycle();
        calculateScales();
        calculatePadding();
        calculateMargin();
        calculateSize();
    }

    /**
     * 计算缩放值
     */
    private void calculateScales() {
        Context context = view.getContext();
        Resources resources = context.getResources();

        DisplayMetrics dm = resources.getDisplayMetrics();
        int width = dm.widthPixels;
        int height = dm.heightPixels;

        if (matchType == MATCH_TYPE_RATIO) {
            float scaleValue;
            if (baseWidth) {
                scaleValue = width / dreamWdithLength;
            } else {
                scaleValue = height / dreamHeightLength;
            }
            horizionScaleValue = scaleValue;
            verticalScaleValue = scaleValue;
        } else if (matchType == MATCH_TYPE_SCALE) {
            horizionScaleValue = width / dreamWdithLength;
            verticalScaleValue = height / dreamHeightLength;
        }

    }
    /**
     * 计算padding
     */
    private void calculatePadding() {
        if (matchType != MATCH_TYPE_ORIGIN) {
            float horizionPadding = horizionScaleValue * padding;
            float verticalPadding = verticalScaleValue * padding;

            leftPadding = horizionScaleValue * leftPadding;
            rightPadding = horizionScaleValue * rightPadding;

            topPadding = verticalScaleValue * topPadding;
            bottomPadding = verticalScaleValue * bottomPadding;

            if (padding != 0) {
                int horizionValue = (int) horizionPadding;
                int verticalValue = (int) verticalPadding;

                if (leftPadding == 0) {
                    leftPadding = horizionValue;
                }
                if (rightPadding == 0) {
                    rightPadding = horizionValue;
                }

                if (topPadding == 0) {
                    topPadding = verticalValue;
                }
                if (bottomPadding == 0) {
                    bottomPadding = verticalValue;
                }
            }
            view.setPadding((int) leftPadding, (int) topPadding, (int) rightPadding, (int) bottomPadding);
        }
    }
    /**
     * 计算margin值
     */
    private void calculateMargin() {
        if (matchType != MATCH_TYPE_ORIGIN) {
            //纵向margin
            leftMargin = horizionScaleValue * leftMargin;
            rightMargin = horizionScaleValue * rightMargin;
            //横向margin
            topMargin = verticalScaleValue * topMargin;
            bottomMargin = verticalScaleValue * bottomMargin;
        }
    }

    /**
     * 计算控件大小
     */
    private void calculateSize() {
        if (matchType == MATCH_TYPE_RATIO) {
            if (baseWidth) {
                childWidthSize = (int) (horizionScaleValue * widthRatio);
                childHeightSize = (int) (childWidthSize / ratioValue);
            } else {
                childHeightSize = (int) (verticalScaleValue * heightRatio);
                childWidthSize = (int) (childHeightSize * ratioValue);
            }
        } else if (matchType == MATCH_TYPE_SCALE) {
            childWidthSize = (int) (horizionScaleValue * widthRatio);
            childHeightSize = (int) (verticalScaleValue * heightRatio);
        }
    }

    @Override
    public int[] onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measureSpec[] = new int[]{widthMeasureSpec, heightMeasureSpec};
        if (matchType != MATCH_TYPE_ORIGIN) {
            setMarginData();
            measureSpec[0] = View.MeasureSpec.makeMeasureSpec(
                    childWidthSize, View.MeasureSpec.EXACTLY);
            measureSpec[1] = View.MeasureSpec.makeMeasureSpec(
                    childHeightSize, View.MeasureSpec.EXACTLY);
        }
        return measureSpec;
    }

    /**
     * 设置margin
     */
    private void setMarginData() {
        if (matchType != MATCH_TYPE_ORIGIN) {
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            if (lp != null) {
                if (leftMargin > 0) {
                    lp.leftMargin = (int) leftMargin;
                }
                if (topMargin > 0) {
                    lp.topMargin = (int) topMargin;
                }
                if (rightMargin > 0) {
                    lp.rightMargin = (int) rightMargin;
                }
                if (bottomMargin > 0) {
                    lp.bottomMargin = (int) bottomMargin;
                }
                view.setLayoutParams(lp);
            }
        }
    }
}
