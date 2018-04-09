package com.example.iris.product.Jsjava;

import android.content.Context;
import android.support.v4.app.SupportActivity;
import android.webkit.JavascriptInterface;

import com.example.iris.product.MainActivity;
import com.example.iris.product.util.MyToastUtils;
import com.example.iris.product.util.SpUtils;

/**
 * 登陆成功回掉保证下次不去在掉h5的登陆接口
 * Created by iris on 2017/12/7.
 */

public class LoginSucinterface {

    private Context context;

    public LoginSucinterface(Context context) {
        this.context = context;
    }


    @JavascriptInterface
    public void loginSuc(){
//        MyToastUtils.showShortToast(context,"h5登陆成功");
        SpUtils.setLoginsuc(context,true);
    }



}
