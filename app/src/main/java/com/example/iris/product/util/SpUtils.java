package com.example.iris.product.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangbin on 17/2/7.
 */
public class SpUtils {

    private final static String PRODUCT_SP="product_sp";
    private static final String COOKIE="cookie";
    private final static String SCREEN_WITH="screen_with";


    public static SharedPreferences getSp(Context context) {
        return context.getSharedPreferences(PRODUCT_SP, Context.MODE_PRIVATE);
    }

    /**
     * 保存cookie
     * @param context
     * @param cookie
     */
    public static void setCooike(Context context, String cookie){
//        getSp(context).edit().putString(COOKIE, cookie).commit();
        getSp(context).edit().putString(COOKIE, cookie).apply();
    }

    /**
     * 获取cookie
     * @param context
     * @return
     */
    public static String getCookie(Context context){
        return getSp(context).getString(COOKIE, "");
    }

    /**
     * 清除sp
     * @param context
     */
    public static void clearSp(Context context){
        getSp(context).edit().clear().commit();
    }


    /**设置屏幕
     * @param context
     * @param screenWith
     */
    public static void setScreenWith(Context context, int screenWith){
        getSp(context).edit().putInt(SCREEN_WITH, screenWith).commit();
    }

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static int getScreenWith(Context context){
        return getSp(context).getInt(SCREEN_WITH, 0);
    }



    /**
     * 保存登录名和密码
     */
    public static void setpwd(Context context, String user, String pswd) {
      getSp(context).edit().putString("user",user).putString("pswd",pswd).commit();
    }


    /**
     * 获取登录名和密码
     * @param context
     * @return
     */
    public static Map<String, String> getpwd(Context context) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user", getSp(context).getString("user", ""));//登录名
        params.put("pswd", getSp(context).getString("pswd", ""));//密码

        return params;
    }


    /**
     * 保存职务
     * @param context
     * @param role
     */
    public static void saverole(Context context, String role) {
        getSp(context).edit().putString("role",role).commit();
    }


    /**
     * 获取职务
     * @param context
     * @return
     */
    public static String getrole(Context context){
        return getSp(context).getString("role", "");
    }


    /**
     * 记录h5是否登陆成功
     * @return
     */
    public static void setLoginsuc(Context context,boolean b){
        getSp(context).edit().putBoolean("boolean",b).commit();
    }

    /**
     * 获取h5的登陆成功与否
     * @param context
     * @return
     */
    public static boolean getLoginsuc(Context context){
       return getSp(context).getBoolean("boolean",true);

    }


    /**
     * 设置token
     * @param context
     * @param token
     */
    public static void setToken(Context context, String token) {
        getSp(context).edit().putString("token",token).commit();
    }

    /**
     * 获取token
     * @param context
     * @return
     */
    public static String getToken(Context context){
        return getSp(context).getString("token","");
    }
}
