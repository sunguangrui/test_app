package com.example.iris.product.http.Manage;

import android.text.TextUtils;

import com.example.iris.product.http.CadillacUrl;
import com.example.iris.product.http.ResultCallback;
import com.example.iris.product.util.GsonUtils;

import java.util.HashMap;
import java.util.Map;



/**
 * 用户
 * Created by bitch-1 on 2017/3/28.
 */

public class UserManager {
    private static UserManager userManager;

    public static UserManager getUserManager() {
        if (userManager == null) {
            userManager = new UserManager();
        }
        return userManager;
    }

    /**登录接口
     * @param loginName 用户名
     * @param loginPasswd 密码
     * @param
     * @param
     * @param resultCallback
     */
    public void toLogin(String loginName, String loginPasswd,ResultCallback resultCallback){
        Map<String, String> params = new HashMap<>();
        params.put("loginName",loginName);
        params.put("loginPasswd",loginPasswd);
        OkHttpManager.getInstance().dojsonPost(CadillacUrl.LOGIN_URL,params,resultCallback);
    }


}
