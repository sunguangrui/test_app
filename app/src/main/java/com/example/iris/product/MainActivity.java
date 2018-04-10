package com.example.iris.product;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.example.iris.product.Jsjava.Lateralinterface;
import com.example.iris.product.Jsjava.LoginSucinterface;
import com.example.iris.product.activity.LoginAct;
import com.example.iris.product.base.BaseActivity;
import com.example.iris.product.http.CadillacUrl;
import com.example.iris.product.util.MyLogUtils;
import com.example.iris.product.util.ProductUtils;
import com.example.iris.product.util.SpUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.io.File;

public class MainActivity extends BaseActivity implements
        Lateralinterface.UpdateUi, Lateralinterface.Loginout {
    @ViewInject(R.id.ll_web)
    private LinearLayout ll_web;
    @ViewInject(R.id.pb)
    private ProgressBar progressBar;
    @ViewInject(R.id.rg_tab)
    private RadioGroup rg_tab;
    private WebView webView;

    private String surl;
    private static final String TAG = "MainActivity";
    private static final String APP_CACAHE_DIRNAME = "/webcache";//app缓存地址

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_home);
        int date=20180410;
        int time=1115;
        int date1=20180411;

        surl = CadillacUrl.HOME_URL;
        Log.e("路径",surl);
        webset();
    }

    @Override
    public void init(Bundle savedInstanceState) {
        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);//设置软键盘默认不弹出

        surl = CadillacUrl.HOME_URL;
        Log.e("路径",surl);
        webset();
    }

    /**
     * webview的一些设置
     */
    private void webset() {
        clearWebViewCache();//清除掉缓存
        ll_web.removeAllViews();
        webView = null;
        webView = new WebView(MainActivity.this);
        webView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        ll_web.addView(webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//这里要设置为不适用缓存但是下面数据库路径要设置下可以清除缓存

//        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式 （这种情况的意思视情况来决定是否使用缓存）
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;
//        Log.i(TAG, "cacheDirPath="+cacheDirPath);
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        //下面三个最常用，基本都需要设置
//        setCacheMode 设置缓存的模式 eg: settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        setJavaSciptEnabled 设置是否支持Javascript eg: settings.setJavaScriptEnabled(true);
//        setDefaultTextEncodingName 设置在解码时使用的默认编码 eg: settings.setDefaultTextEncodingName(“utf-8”);
//        setAllowFileAccess 启用或禁止WebView访问文件数据
//        setBlockNetworkImage 是否显示网络图像
//        setBuiltInZoomControls 设置是否支持缩放
//        setDefaultFontSize 设置默认的字体大小
//        setFixedFontFamily 设置固定使用的字体
//        setLayoutAlgorithm 设置布局方式
//        setLightTouchEnabled 设置用鼠标激活被选项
//        setSupportZoom 设置是否支持变焦

//        webView.setWebChromeClient(new WebChromeClient());
        String userAgent = webView.getSettings().getUserAgentString();//找到webview的useragent
        //在useragent上添加APP_WEBVIEW 标识符,服务器会获取该标识符进行判断
        String key=userAgent.replace("Android","iris"+"_"+ProductUtils.getAppcode(MainActivity.this));
        webView.getSettings().setUserAgentString(key);




        //暴露js侧滑动，java暴露方法然后显示隐藏

        webView.addJavascriptInterface(new Lateralinterface(MainActivity.this), "wst");
        webView.addJavascriptInterface(new LoginSucinterface(MainActivity.this), "log");
//
        synCookies();//同步token
        webView.loadUrl(surl);
//

        webView.setWebViewClient(new WebViewClient() {


            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
////                return super.shouldOverrideUrlLoading(view, request.getUrl().toString())
////                view.loadUrl(request.getUrl().toString());
////                return true;
//                if(request.getUrl().toString().contains("tel:")){
//                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(request.getUrl().toString())));
//                    return true;
//                }else if(request.getUrl().toString().contains("sms")){
//                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(request.getUrl().toString())));
//                    return true;
//                }else
//                    view.loadUrl(request.getUrl().toString());
//                return true;
//
//            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                return super.shouldOverrideUrlLoading(view, url);
                if (url.startsWith("tel:")) {
                    startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)));
                    return true;
                }else if(url.startsWith("sms:")){
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
                } else
                    view.loadUrl(url);
                    return true;


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                MyLogUtils.info("路径"+url);
                progressBar.setProgress(0);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                progressBar.setVisibility(View.GONE);

//                boolean b=SpUtils.getLoginsuc(MainActivity.this);
//                if(!b) {//false 表示h5没登陆成功
//                    webView.loadUrl("javascript:getUserInfo('" + SpUtils.getpwd(MainActivity.this).get("user") + "','" + SpUtils.getpwd(MainActivity.this).get("pswd") + "')");
//
//                }
                }



            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                // 加载网页失败时处理 如：提示失败，或显示新的界面
            }


        });



        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setProgress(newProgress);
                }

            }

        });



    }

    private void synCookies() {
        CookieSyncManager.createInstance(this);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie( true );
        cookieManager.removeSessionCookie();// 移除  
        cookieManager.removeAllCookie();
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append(String.format("e_token=%s",SpUtils.getToken(this)));
//        String domain=SpUtils.getCookie(MainActivity.this).split(";")[1].split(";")[1].split(";")[1].split(";")[0].split("=")[1];
//        MyLogUtils.info("domain"+domain);
//        sbCookie.append(String.format(";domain=%s","iris.com"));
        sbCookie.append(String.format(";domain=%s","imwork.net"));
        sbCookie.append(String.format(";path=%s","/"));
        String cookieValue = sbCookie.toString();
        cookieManager.setCookie(surl,cookieValue);//为url设置cookie    
        CookieSyncManager.getInstance().sync();//同步cookie  

//        String newCookie = cookieManager.getCookie(surl);
//        Log.e("同步后cookie", newCookie);
    }


    @OnClick({R.id.rb_data, R.id.rb_stat, R.id.rb_flat})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rb_data://首页
                surl = CadillacUrl.HOME_URL;
                webset();
                break;

            case R.id.rb_stat://
                surl = CadillacUrl.CENT_URL;
                webset();

                break;

            case R.id.rb_flat:

                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ll_web.removeAllViews();
        webView.destroy();
        webView = null;
        clearWebViewCache();//清除掉缓存


    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();
                rg_tab.setVisibility(View.VISIBLE);

                return true;
            }
//            else {
////                finish();
//            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void showOrhide(String type) {
        switch (type) {
            case "1"://显示
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rg_tab.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                }.start();


                break;

            case "2"://隐藏
                new Thread() {
                    @Override
                    public void run() {
                        super.run();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                rg_tab.setVisibility(View.GONE);
                            }
                        });
                    }
                }.start();
                break;
        }

    }


    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
//        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
//        Log.e(TAG, "webviewCacheDir path="+webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }


    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }


    @Override
    public void loginoutact() {
        SpUtils.clearSp(MainActivity.this);
        finish();
        openActivity(LoginAct.class);
    }


}
