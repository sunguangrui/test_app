package com.example.iris.product.fragment;
/*
 *  @创建者    lihaijun
 *  @创建时间   2018/2/23 10:34
 *  @描述     ${TODO}
 *
 *  @更新者    $Author
 *  @更新时间   $Date
 *  @更新描述   ${TODO}
 */

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.iris.product.Jsjava.Lateralinterface;
import com.example.iris.product.Jsjava.LoginSucinterface;
import com.example.iris.product.R;
import com.example.iris.product.util.MyLogUtils;
import com.example.iris.product.util.ProductUtils;
import com.example.iris.product.util.SpUtils;
import com.tencent.smtt.sdk.CookieManager;
import com.tencent.smtt.sdk.CookieSyncManager;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

public class WebViewFragment extends Fragment {
    private static final String TAG = "WebViewFragment";
    private static final String APP_CACAHE_DIRNAME = "/webcache";//app缓存地址
    public static final String URL = "url";
    private String           mUrl;
    public  WebView          mWebView;
    private FragmentActivity mActivity;
    private ProgressBar      mProgressBar;
    private LinearLayout mLl_web;


    public static WebViewFragment newInstance(String url) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(URL, url);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUrl = getArguments().getString(URL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //        View inflate = inflater.inflate(R.layout.fragment_webview, container, false);
        //        mWebView = inflate.findViewById(R.id.webview);
        return inflater.inflate(R.layout.fragment_webview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mActivity = getActivity();
//        mWebView = view.findViewById(R.id.webview);
        mLl_web = view.findViewById(R.id.ll_web);
        mProgressBar = view.findViewById(R.id.pb);
    }

    public void createWebView(String url){
        clearWebViewCache();//清除掉缓存
        mLl_web.removeAllViews();
        if (mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        mWebView = new com.tencent.smtt.sdk.WebView(mActivity);
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mLl_web.addView(mWebView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        //        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);//这里要设置为不适用缓存但是下面数据库路径要设置下可以清除缓存

        //        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式 （这种情况的意思视情况来决定是否使用缓存）
        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = mActivity.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
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
        String userAgent = mWebView.getSettings().getUserAgentString();//找到webview的useragent
        //在useragent上添加APP_WEBVIEW 标识符,服务器会获取该标识符进行判断
        String key=userAgent.replace("Android","iris"+"_"+ProductUtils.getAppcode(mActivity));
        mWebView.getSettings().setUserAgentString(key);




        //暴露js侧滑动，java暴露方法然后显示隐藏

        mWebView.addJavascriptInterface(new Lateralinterface(mActivity), "wst");
        mWebView.addJavascriptInterface(new LoginSucinterface(mActivity), "log");
        //
        synCookies(url);//同步token
        mWebView.loadUrl(url);
        //

        mWebView.setWebViewClient(new WebViewClient() {


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
//                progressBar.setProgress(0);
//                progressBar.setVisibility(View.VISIBLE);
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



        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    progressBar.setVisibility(View.GONE);
//                } else {
//                    progressBar.setVisibility(View.VISIBLE);
//                    progressBar.setProgress(newProgress);
//                }

            }

        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        createWebView(mUrl);
    }

    private void synCookies(String url) {
        CookieSyncManager.createInstance(mActivity);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie( true );
        cookieManager.removeSessionCookie();// 移除  
        cookieManager.removeAllCookie();
        StringBuilder sbCookie = new StringBuilder();
        sbCookie.append(String.format("e_token=%s",SpUtils.getToken(mActivity)));
        //        String domain=SpUtils.getCookie(MainActivity.this).split(";")[1].split(";")[1].split(";")[1].split(";")[0].split("=")[1];
        //        MyLogUtils.info("domain"+domain);
        //        sbCookie.append(String.format(";domain=%s","iris.com"));
        sbCookie.append(String.format(";domain=%s","imwork.net"));
        sbCookie.append(String.format(";path=%s","/"));
        String cookieValue = sbCookie.toString();
        cookieManager.setCookie(url,cookieValue);//为url设置cookie    
        CookieSyncManager.getInstance().sync();//同步cookie  
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLl_web.removeAllViews();
        if(mWebView != null) {
            mWebView.destroy();
            mWebView = null;
        }
        clearWebViewCache();//清除掉缓存
        MyLogUtils.info(TAG + ":onDestroy");
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MyLogUtils.info(TAG + ":onDestroyView");

    }

    /**
     * 清除WebView缓存
     */
    public void clearWebViewCache() {

        //清理Webview缓存数据库
        try {
            mActivity.deleteDatabase("webview.db");
            mActivity.deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(mActivity.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        //        Log.e(TAG, "appCacheDir path="+appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(mActivity.getCacheDir().getAbsolutePath() + "/webviewCache");
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
}