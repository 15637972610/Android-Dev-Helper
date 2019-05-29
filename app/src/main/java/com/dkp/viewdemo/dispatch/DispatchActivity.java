package com.dkp.viewdemo.dispatch;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dkp.viewdemo.R;

/**
 * Created by Administrator on 2019/5/17.
 */

public class DispatchActivity extends Activity implements View.OnClickListener {
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispatch);
//        button  =  (Button) findViewById(R.id.btn_dispatch);
//        button.setOnClickListener(this);
//        button.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View view, MotionEvent motionEvent) {
//                Log.d("DispatchActivity","onTouch");
//                return false;
//            }
//        });

    }

    @Override
    public void onClick(View view) {
//        if (view.getId()==R.id.btn_dispatch){
//            Log.d("DispatchActivity","onClick");
//        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("DispatchActivity","dispatchTouchEvent"+super.dispatchTouchEvent(ev));
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("DispatchActivity","onTouchEvent"+super.onTouchEvent(event));
//        switch (event.getAction()){
//            case MotionEvent.ACTION_DOWN:
//                Log.d("DispatchActivity","onTouchEvent  ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("DispatchActivity","onTouchEvent  ACTION_UP");
//                break;
//            case MotionEvent.ACTION_MOVE:
//                Log.d("DispatchActivity","onTouchEvent  ACTION_MOVE");
//                break;
//            default:
//                break;
//        }
        return false;
    }


}
