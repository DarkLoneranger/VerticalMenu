package com.example.verticalmenu.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;


/**
 * Created by ZHAO on 2016/11/11.
 */
public class VerticalSatelliteMenu extends FrameLayout{


    private int childViewNumber;
    private int childViewHeight;
    private onVerticalMenuItemClickListener listener;



    public VerticalSatelliteMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
        childViewNumber = this.getChildCount();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (childViewNumber != 0) {
            childViewHeight = getChildAt(0).getMeasuredHeight();
        }
        for (int i = 0; i < childViewNumber; i++) {
            //根据需要调整圆形子控件的初始位置，这里是将按钮都叠放在一个位置
            //Change the initial postions of subViews here
            getChildAt(i).layout(getMeasuredWidth()  - childViewHeight-10,  0, getMeasuredWidth()-10 ,  childViewHeight);
         }
    }


    //打开垂直菜单的方法，菜单最初在xml设置为隐藏，点击打开时变为可见
    //method to open menu. The menu was set "INVISIBLE" in the layout xml. When we open it, we should change it to "VISIBLE" firstly.
    public void open() {
        this.setVisibility(View.VISIBLE);
        for (int i = 0; i < childViewNumber; i++) {
                ObjectAnimator
                        .ofFloat(getChildAt(i), "TranslationY", 0.0F, i * (childViewHeight * 3 / 2))//
                        .setDuration(300)
                        .start();
        }
    }
    //关闭菜单的方法
    //method to close menu
    public void close() {
        for (int i = 0; i < childViewNumber; i++) {
            ObjectAnimator translationY = ObjectAnimator
                    .ofFloat(getChildAt(i), "TranslationY", i * (childViewHeight * 3 / 2), 0.0F)
                    .setDuration(300);
            translationY.start();
            if(i==childViewNumber-1){
                translationY.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        VerticalSatelliteMenu.this.setVisibility(View.GONE);
                    }
                });
            }
        }
    }


    //一下均是为圆形子条目设置点击事件的方法
    //all the below were methods setting ClickListener for the VerticalMenuItem
    public interface onVerticalMenuItemClickListener {
        void onItemClick(int number);
    }

    public void setOnVerticalMenuItemClickListener(onVerticalMenuItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i=0;i<childViewNumber;i++){
            final int currentIndex=i;
            View childView = getChildAt(i);
            childView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        listener.onItemClick(currentIndex);
                    }

                }
            });
        }
    }

}

