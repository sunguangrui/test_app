package com.example.iris.product.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by iris on 2017/12/18.
 */

public class ProductUtils {


    /**
     * 获取app版本
     * @param context
     * @return
     */
    public static String getAppcode(Context context){

        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(context.getPackageName(), 0);
            String version = info.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrtime(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        Date curdate=new Date(System.currentTimeMillis());
        return formatter.format(curdate);
    }
}
