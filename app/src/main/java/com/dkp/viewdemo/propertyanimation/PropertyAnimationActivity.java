package com.dkp.viewdemo.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.widget.Button;

import com.dkp.viewdemo.R;

/**
 * Created by Administrator on 2019/5/9.
 * 属性动画使用demo
 */

public class PropertyAnimationActivity extends Activity {
    private Button myButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_animation);
        myButton = (Button) findViewById(R.id.btn);
//        initAnimator();
//        initButtonWidChangeAnimator();
//        initObjectAnimator_Alpha();
//        initObjectAnimator_Rotation();
//        initObjectAnimator_translation();
//        initObjectAnimator_scaleX();
        initAnimatorSet();
    }

    /**
     * 动画集合，实现多个动画同时播放
     */
    private void initAnimatorSet() {
        AnimatorSet set = new AnimatorSet();
        //playSequentially顺序一个一个的播放，playTogether多个动画同时一起播放
        set.playTogether(ObjectAnimator.ofFloat(myButton,"rotationX",0,360),
                ObjectAnimator.ofFloat(myButton,"rotationY",0,180),
                ObjectAnimator.ofFloat(myButton,"rotation",0,-90),
                ObjectAnimator.ofFloat(myButton,"translationX",0,90),
                ObjectAnimator.ofFloat(myButton,"translationY",0,90),
                ObjectAnimator.ofFloat(myButton,"scaleX",1,3f),
                ObjectAnimator.ofFloat(myButton,"scaleY",1,3f),
                ObjectAnimator.ofFloat(myButton,"alpha",1,0.25f,1)
                );
//        set.playSequentially();
        set.setDuration(3000).setStartDelay(1000);
        set.start();

    }


    /**
     * ObjectAnimator
     * 透明度变化
     */
    private void initObjectAnimator_Alpha() {
        //步骤1：设置动画的初始值&结束值
        ObjectAnimator buttonObjectAnimator = ObjectAnimator.ofFloat(myButton,"alpha",1f,0f,1f);
        //步骤2：设置动画的各种属性
        buttonObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        buttonObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        buttonObjectAnimator.setDuration(3000);
        buttonObjectAnimator.setStartDelay(2000);
        //步骤3：开启动画
        buttonObjectAnimator.start();
    }
    /**
     * ObjectAnimator
     * 旋转变化
     */
    private void initObjectAnimator_Rotation() {
        //步骤1：设置动画的初始值&结束值
        ObjectAnimator buttonObjectAnimator = ObjectAnimator.ofFloat(myButton,"rotation",0f,360f);
        //步骤2：设置动画的各种属性
        buttonObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        buttonObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        buttonObjectAnimator.setDuration(3000);
        buttonObjectAnimator.setStartDelay(2000);
        //步骤3：开启动画
        buttonObjectAnimator.start();
    }
    /**
     * ObjectAnimator
     * 平移变化
     */
    private void initObjectAnimator_translation() {
        float currentX = myButton.getTranslationX();
        float currentY = myButton.getTranslationY();
        float currentZ = myButton.getTranslationZ();


        //步骤1：设置动画的初始值&结束值,currentX到500f,然后再回到currentX
        ObjectAnimator buttonObjectAnimator = ObjectAnimator.ofFloat(myButton,"translationX",currentX,500f,currentX);
        //步骤2：设置动画的各种属性
        buttonObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        buttonObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        buttonObjectAnimator.setDuration(3000);
        buttonObjectAnimator.setStartDelay(2000);
        //步骤3：开启动画
        buttonObjectAnimator.start();
    }

    /**
     * ObjectAnimator
     * 缩放变化
     */
    private void initObjectAnimator_scaleX() {

        //步骤1：设置动画的初始值&结束值,1f到3f,然后再回到1f
        ObjectAnimator buttonObjectAnimator = ObjectAnimator.ofFloat(myButton,"scaleX",1f,3f,0.3f);
        //步骤2：设置动画的各种属性
        buttonObjectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        buttonObjectAnimator.setRepeatMode(ObjectAnimator.RESTART);
        buttonObjectAnimator.setDuration(3000);
        buttonObjectAnimator.setStartDelay(2000);
        //步骤3：开启动画
        buttonObjectAnimator.start();
    }

    /**
     * ValueAnimator
     * 初始化一个按钮宽度变化的动画
     */
    private void initButtonWidChangeAnimator() {
        //步骤1：设置按钮的初始值&结束值
        ValueAnimator buttonAnimator = ValueAnimator.ofInt(myButton.getLayoutParams().width,500);
        //步骤2：设置动画的各种属性
        buttonAnimator.setDuration(500);
        buttonAnimator.setRepeatMode(ValueAnimator.RESTART);
        buttonAnimator.setRepeatCount(0);
        buttonAnimator.setStartDelay(1000);
        //步骤3：通过动画的更新监听器，将变化的属性值手动赋值给对象
        buttonAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int currentValue = (int) valueAnimator.getAnimatedValue();
                myButton.getLayoutParams().width = currentValue;
         //步骤4：刷新view,实现动画效果
                myButton.requestLayout();
            }
        });
        //步骤5：开启动画
        buttonAnimator.start();
    }

    /**
     * 动画初始化
     */
    private void initAnimator() {
        // 步骤1：设置动画属性的初始值 & 结束值
            // ofInt（）作用有两个
            // 1. 创建动画实例
            // 2. 将传入的多个Int参数进行平滑过渡:此处传入0和1,表示将值从0平滑过渡到1
            // 如果传入了3个Int参数 a,b,c ,则是先从a平滑过渡到b,再从b平滑过渡到C，以此类推
            // ValueAnimator.ofInt()内置了整型估值器,直接采用默认的.不需要设置，即默认设置了如何从初始值 过渡到 结束值

            ValueAnimator myValueAnimator = ValueAnimator.ofInt(0,3);

        //步骤2：设置动画播放的各种属性
            myValueAnimator.setTarget(myButton);
            //设置动画播放的时长
            myValueAnimator.setDuration(500);
            //设置动画的重复播放次数=重复次数+1
            //// 动画播放次数 = infinite时,动画无限重复
            myValueAnimator.setRepeatCount(0);
            //设置重复播放动画的模式
            //ValueAnimator.RESTART(默认):正序重放
            //ValueAnimator.REVERSE:倒序回放
            myValueAnimator.setRepeatMode(ValueAnimator.RESTART);
            // 设置动画延迟播放时间
            myValueAnimator.setStartDelay(500);
        //步骤3： 通过动画的更新监听器，将变化的属性值手动赋值给对象
            // 设置 值的更新监听器
            // 即：值每次改变、变化一次,该方法就会被调用一次
            myValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    //获得改变后的属性值
                    int currntValue = (int) valueAnimator.getAnimatedValue();
        //步骤4:手动设置给对象的属性
                    myButton.setText("第"+currntValue+"次变化");
        //步骤5:刷新视图，重新绘制，实现动画效果
                    myButton.requestLayout();
                }
            });
        //步骤6:开启动画
        myValueAnimator.start();


    }
}
