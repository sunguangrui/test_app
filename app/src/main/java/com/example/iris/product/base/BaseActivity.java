package com.example.iris.product.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.iris.product.R;
import com.lidroid.xutils.ViewUtils;


/**
 * 基础类
 */
public abstract class BaseActivity extends FragmentActivity {
    private TextView tv_title;
    private RelativeLayout rlRight,rl_title;
    private TextView tvRight;
    private ImageView iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.putParcelable("android:support:fragments",null);
        }
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setLayout();
        // 注入控件
        ViewUtils.inject(this);
        init(savedInstanceState);
    }

    /**
     * 设置布局
     */
    public abstract void setLayout();

    /**
     * 填充数据
     */
    public abstract void init(Bundle savedInstanceState);


    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }


    /**
     * 设置顶部标题
     *
     * @param title
     */
    public void setTopTitle(String title) {
        tv_title = (TextView) findViewById(R.id.tv_title);
        if (title != null) {
            tv_title.setText(title);
        }
    }

    /**
     * 设置标题栏背景色此项目中只在H5Web中用了下
     * @param color
     */
    public void setLayoutbac(int color){
        rl_title= (RelativeLayout) findViewById(R.id.rl_title);
        if(color+""!=null){
            rl_title.setBackgroundColor(color);
        }

    }

    /**
     * 设置后退键样式（这里有在H5Web中用了下）
     * @param res
     */
    public void setLeftRes(int res){
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
        if(res+""!=null){
            iv_back.setImageResource(res);
        }else {

        }

    }

    public void setRight(String rightText, View.OnClickListener onClick){
        rlRight= (RelativeLayout) findViewById(R.id.rlRight);
        tvRight= (TextView) findViewById(R.id.tvRight);
        rlRight.setVisibility(View.VISIBLE);
        tvRight.setText(rightText);
        rlRight.setOnClickListener(onClick);
    }

    public void setRightEnable(boolean flag){
//        rlRight= (RelativeLayout) findViewById(R.id.rlRight);
        rlRight.setEnabled(flag);
    }

    /**
     * 设置左边后退箭头显示
     * @param
     */
    public void setLeftIvShow(){
        iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setVisibility(View.VISIBLE);
    }




    /**
     * 返回
     *
     * @param view
     */
    public void goback(View view) {
        finish();
    }

    protected void onResume() {
        super.onResume();

    }
    protected void onPause() {
        super.onPause();

    }
}
