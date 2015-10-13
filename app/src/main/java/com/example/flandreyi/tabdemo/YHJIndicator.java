package com.example.flandreyi.tabdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.OverScroller;

import java.net.ContentHandler;

/**
 * Created by FlandreYi on 2015/10/13.
 */

public class YHJIndicator extends HorizontalScrollView implements ViewPager.OnPageChangeListener{

    private  int VISIBLE_COUNT;
    private Paint mPaint;
    private int mBallColor;
    private int mBallRadius;
    private int mCurrentView;
    private int mFirstVisibleView;
    private int mViewCount;
    private ViewPager mViewPager;
    private int mScreenWidth;
    private int mTextHeight;
    private float mBallX;

    public void setViewPager(ViewPager viewPager) {
        this.mViewPager = viewPager;
        mViewPager.setOnPageChangeListener(this);
    }

    public YHJIndicator(Context context) {
        this(context, null);
    }
    public YHJIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        mScreenWidth = windowManager.getDefaultDisplay().getWidth();
        VISIBLE_COUNT=4;
        mTextHeight=100;
        mBallRadius=10;
        mFirstVisibleView=0;
        mCurrentView=0;
        mFirstVisibleView=0;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBallColor = Color.CYAN;
        mPaint.setColor(mBallColor);
    }

    /**
     * 得到子view的数量，并加上LayoutParams
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LinearLayout ll= (LinearLayout) getChildAt(0);
        mViewCount= ll.getChildCount();
        mBallX=mScreenWidth/VISIBLE_COUNT/2;
        System.out.println("onFinishInflate");
        System.out.println(mScreenWidth / mViewCount);
        for (int i = 0; i < mViewCount; i++) {
            View view = ll.getChildAt(i);
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            lp.width=mScreenWidth/VISIBLE_COUNT;
            lp.height=mTextHeight+mBallRadius*2;
            view.setLayoutParams(lp);
            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mViewPager.setCurrentItem(finalI);
                }
            });
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public void onPageScrolled(int position, float offset, int offsetPx) {
        mBallX =mScreenWidth/VISIBLE_COUNT/2 +
                (position+offset) * (mScreenWidth *1f/ VISIBLE_COUNT);
        invalidate();
        getViewInfo(position);
        //判断是否需要自动scroll
        if (mCurrentView==1&&mFirstVisibleView==0) {
            System.out.println("最左端不用滑动");
            return;
        }
        if (mCurrentView>=mViewCount-2&&mFirstVisibleView==mViewCount-VISIBLE_COUNT) {
            System.out.println("最有右端不用滑动");
            return;
        }
        if (mFirstVisibleView+VISIBLE_COUNT-2<mCurrentView+0.1) {
            System.out.println("需要右滑动");
            smoothScrollTo(getScrollX() + (int) (offset * mScreenWidth / VISIBLE_COUNT), 0);
            return;
        }
        if (mCurrentView-mFirstVisibleView<0.9) {
            System.out.println("需要左滑动");
            smoothScrollTo(getScrollX() - (int) ((1 - offset) * mScreenWidth / VISIBLE_COUNT), 0);
            return;
        }
    }



    private void getViewInfo(int position) {
        mCurrentView=position;
        mFirstVisibleView = getScrollX() / (mScreenWidth / VISIBLE_COUNT);
    }

    @Override
    public void onPageSelected(int i) {
    }

    @Override
    public void onPageScrollStateChanged(int i) {
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(mBallX, mTextHeight + mBallRadius, mBallRadius, mPaint);
    }


}
