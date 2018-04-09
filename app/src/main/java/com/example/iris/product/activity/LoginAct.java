package com.example.iris.product.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.iris.product.R;
import com.example.iris.product.base.BaseActivity;
import com.example.iris.product.http.Manage.UserManager;
import com.example.iris.product.http.ResultCallback;
import com.example.iris.product.util.MyToastUtils;
import com.example.iris.product.util.ProductUtils;
import com.example.iris.product.util.SpUtils;
import com.example.iris.product.view.LoadDialog;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by iris on 2017/11/30.
 */

public class LoginAct extends BaseActivity{
    @ViewInject(R.id.et_account)
    private EditText et_account;

    @ViewInject(R.id.et_password)
    private EditText et_password;

    @ViewInject(R.id.tv_login)
    private TextView tv_login;
    private String user,pass;
    @ViewInject(R.id.tv_cleracount)
    private TextView tv_cleracount;

    @ViewInject(R.id.tv_clearpwd)
    private TextView tv_clearpwd;

    @ViewInject(R.id.sc_view)
    private ScrollView sc_view;

    @ViewInject(R.id.tv_bq)
    private TextView tv_bq;

    private String acount,pwd;

    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private float scale = 0.6f; //logo缩放比例
    private int height = 0;


    @Override
    public void setLayout()
    {
        //全屏
        getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN ,
                WindowManager.LayoutParams. FLAG_FULLSCREEN);
        setContentView(R.layout.act_newlogin);
    }

    @Override
    public void init(Bundle savedInstanceState) {

        if (Build.VERSION.SDK_INT >= 23) {//电话权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 110);
                return;
            }
        }
        if (Build.VERSION.SDK_INT >= 23) {//短信权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 100);
                return;
            }
        }

        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//设置软键盘默认不弹出


        screenHeight = this.getResources().getDisplayMetrics().heightPixels; //获取屏幕高度
        keyHeight = screenHeight / 3;//弹起高度为屏幕高度的1/3
        tv_bq.setText("©"+ ProductUtils.getCurrtime()+"润雅信息技术(上海)有限公司 版权所有");
        acount=SpUtils.getpwd(LoginAct.this).get("user");
        pwd=SpUtils.getpwd(LoginAct.this).get("pswd");
        if(!TextUtils.isEmpty(acount)&&!TextUtils.isEmpty(pwd)){
            tv_cleracount.setVisibility(View.VISIBLE);
            tv_clearpwd.setVisibility(View.VISIBLE);
            et_account.setText(acount);
            et_password.setText(pwd);
            login(acount,pwd);
        }else{
            tv_cleracount.setVisibility(View.GONE);
            tv_clearpwd.setVisibility(View.GONE);
        }




        et_account.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    tv_cleracount.setVisibility(View.VISIBLE);
                }else {
                    tv_cleracount.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    tv_cleracount.setVisibility(View.VISIBLE);
                }else {
                    tv_cleracount.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    tv_cleracount.setVisibility(View.VISIBLE);
                }else {
                    tv_cleracount.setVisibility(View.GONE);
                }

            }
        });


        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!TextUtils.isEmpty(charSequence)){
                    tv_clearpwd.setVisibility(View.VISIBLE);

                }else {
                    tv_clearpwd.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
              if(!TextUtils.isEmpty(charSequence)){
                  tv_clearpwd.setVisibility(View.VISIBLE);

              }else {
                  tv_clearpwd.setVisibility(View.GONE);
              }

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!TextUtils.isEmpty(editable.toString())){
                    tv_clearpwd.setVisibility(View.VISIBLE);

                }else {
                    tv_clearpwd.setVisibility(View.GONE);
                }
            }
        });

        final Handler mhandler=new Handler();
        sc_view.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, final int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
      /* old是改变前的左上右下坐标点值，没有old的是改变后的左上右下坐标点值
      现在认为只要控件将Activity向上推的高度超过了1/3屏幕高，就认为软键盘弹起*/
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("wenzhihao", "up------>" + (oldBottom - bottom));
//                    sc_view.post(new Runnable() {
//                        @Override
//                        public void run() {
//                          sc_view.scrollTo(50,100);
//                        }
//                    });

                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                         int offset = screenHeight - sc_view.getHeight();
                         sc_view.scrollTo(0,offset);
                        }
                    });


                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("wenzhihao", "down------>" + (bottom - oldBottom));

//                    sc_view.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            sc_view.scrollTo(0,0);
//                        }
//                    });
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            int offset = screenHeight - sc_view.getHeight();
                            sc_view.scrollTo(0,0);
                        }
                    });
                }
            }
        });
    }

    /**
     * 动态添加电话权限
     */
    private void requestPhonePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 110);
                return;
            }
        }

    }

    @OnClick({R.id.tv_login,R.id.ll_cleracount,R.id.ll_clearpwd})
    public void OnClick(View view){

        switch (view.getId()){
            case R.id.tv_login:
             if(isValidate()){
                 login(user,pass);//登陆

             }
                break;

//            case R.id.ll_phone:
//                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:02153520778"));
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                startActivity(intent);
//
//                break;

            case R.id.ll_cleracount:
                et_account.setText("");
                break;

            case R.id.ll_clearpwd:
                et_password.setText("");
                break;


        }
    }

    /**
     * 登陆
     * @param user
     * @param pass
     */
    private void login(final String user, final String pass) {
        tv_login.setEnabled(false);
        final LoadDialog dialog=new LoadDialog(LoginAct.this,"");
        dialog.builder().show();
        dialog.setCancledOnTouchOutside(false);
        UserManager.getUserManager().toLogin(user, pass,new ResultCallback<String>() {
            @Override
            public void onError(int status, String errorMsg) {
                tv_login.setEnabled(true);
                dialog.dissmiss();
             MyToastUtils.showShortToast(LoginAct.this,errorMsg);
            }

            @Override
            public void onResponse(String response) {
                tv_login.setEnabled(true);
                dialog.dissmiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.getString("code");
                    String message=jsonObject.getString("message");
                    if(code.equals("success")){
                        JSONObject obj=jsonObject.getJSONObject("obj");
                        String token=obj.getString("empToken");
                        SpUtils.setToken(LoginAct.this,token);
                        SpUtils.setpwd(LoginAct.this,user,pass);
                        SpUtils.setLoginsuc(LoginAct.this,false);//默认设置h5登陆失败
//                        openActivity(MainActivity.class);
                        openActivity(HomeActivity.class);
                        finish();
                    }else {
                        MyToastUtils.showShortToast(LoginAct.this,message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private boolean isValidate() {
        user = et_account.getText().toString().trim();
        pass = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(user)) {
            MyToastUtils.showShortToast(LoginAct.this, "请输入账号！");
            et_account.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(pass)) {
            MyToastUtils.showShortToast(LoginAct.this, "请输入密码!");
            et_password.requestFocus();
            return false;
        }
        return true;
    }






}
