package com.example.iris.product.Jsjava;

import android.content.Context;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;

/**
 * Created by iris on 2017/12/1.
 */

public class Lateralinterface {
    private Context context;
    private UpdateUi updateui;
    private Loginout loginout;

    public Lateralinterface(Context context) {
        this.context = context;
        updateui= (UpdateUi) context;
        loginout= (Loginout) context;
    }

    /**
     * 侧滑
     * @param type
     */
    @JavascriptInterface
    public void showToast(String type){
        if(!TextUtils.isEmpty(type)){
         updateui.showOrhide(type);
        }
    }

    public interface UpdateUi{
        void showOrhide(String type);
    }

    /**
     * 退出
     */
    @JavascriptInterface
    public void loginOut(){

        loginout.loginoutact();

    }

    public interface Loginout{
        void loginoutact();
    }
}
