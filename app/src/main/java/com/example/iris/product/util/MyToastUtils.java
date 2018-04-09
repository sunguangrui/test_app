package com.example.iris.product.util;

import android.content.Context;
import android.widget.Toast;


/**
 * Created by sunshaochi on 15/11/16.
 */
public class MyToastUtils {

    /**
     * 显示时间短的吐司---调试用
     *
     * @param context
     * @param message
     */
//    public static void showShortDebugToast(Context context, String message) {
//        if (ConstantValue.IS_SHOW_DEBUG) {
//            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
//        }
//    }

    /**
     * 显示时间长的吐司---调试用
     *
     * @param context
     * @param message
     */
//    public static void showLongDebugToast(Context context, String message) {
//        if (ConstantValue.IS_SHOW_DEBUG) {
//            Toast.makeText(context, message, Toast.LENGTH_LONG).show();
//        }
//    }

    private static Toast mToast;

    /**
     * 显示时间短的吐司
     *
     * @param context
     * @param message
     */
    public static void showShortToast(Context context, String message) {
        if(context!=null) {
            if (mToast == null) {
                mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            } else {
                mToast.setText(message);
                mToast.setDuration(Toast.LENGTH_SHORT);
            }
            mToast.show();
        }
    }

    /**
     * 显示时间长的吐司
     *
     * @param context
     * @param message
     */
    public static void showLongToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}
