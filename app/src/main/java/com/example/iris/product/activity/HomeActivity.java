package com.example.iris.product.activity;
/*
 *  @创建者    lihaijun
 *  @创建时间   2018/2/23 10:39
 *  @描述     ${TODO}
 *
 *  @更新者    $Author
 *  @更新时间   $Date
 *  @更新描述   ${TODO}
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.iris.product.Jsjava.Lateralinterface;
import com.example.iris.product.R;
import com.example.iris.product.base.BaseActivity;
import com.example.iris.product.fragment.MessageFragment;
import com.example.iris.product.fragment.WebViewFragment;
import com.example.iris.product.http.CadillacUrl;
import com.example.iris.product.util.AndroidBug5497Workaround;
import com.example.iris.product.util.MyLogUtils;
import com.example.iris.product.util.SpUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tencent.smtt.sdk.WebView;

public class HomeActivity extends BaseActivity implements
        Lateralinterface.UpdateUi, Lateralinterface.Loginout {
    private static final String TAG = "HomeActivity";
    @ViewInject(R.id.rl_fragment_container)
    private RelativeLayout mRlFragmentContainer;
    @ViewInject(R.id.rg_tab)
    private RadioGroup     mRgTab;
    private static final int FIRST = 0;
    private static final int SECOND = 1;
    private static final int THIRD = 2;

    Fragment        mCurrentFragment;
    WebViewFragment mFirstFragment;
    WebViewFragment mSecondFragment;
    MessageFragment mThirdFragment;
    private FragmentManager mFragmentManager;

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
                                mRgTab.setVisibility(View.VISIBLE);
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
                                mRgTab.setVisibility(View.GONE);
                            }
                        });
                    }
                }.start();
                break;
        }
    }

    @Override
    public void loginoutact() {
        SpUtils.clearSp(HomeActivity.this);
        finish();
        openActivity(LoginAct.class);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_home);
    }

    @Override
    public void init(Bundle savedInstanceState) {
//        getWindow().setSoftInputMode( WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mFragmentManager = getSupportFragmentManager();
        AndroidBug5497Workaround.assistActivity(this, new AndroidBug5497Workaround.OnKeyboardToggleListener() {
            @Override
            public void onKeyboardShown(int keyboardSize) {
                mRgTab.setVisibility(View.GONE);
            }

            @Override
            public void onKeyboardClosed() {
                mRgTab.setVisibility(View.VISIBLE);
            }
        });
        showView(FIRST);
    }

    @OnClick({R.id.rb_data, R.id.rb_stat, R.id.rb_flat})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.rb_data://首页
                showView(FIRST);
                break;
            case R.id.rb_stat://客户中心
                showView(SECOND);
                break;
            case R.id.rb_flat://消息
                showView(THIRD);
                break;
        }
    }

    private void showView(int pageIndex) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (null != transaction) {
            if (mCurrentFragment != null){
                transaction.hide(mCurrentFragment);
            }
            switch (pageIndex) {
                case FIRST:
                    if (null == mFirstFragment) {
                        mFirstFragment = WebViewFragment.newInstance(CadillacUrl.HOME_URL);
                        transaction.add(R.id.rl_fragment_container, mFirstFragment);
                        mCurrentFragment = mFirstFragment;
                    }else {
                        if (mCurrentFragment != mFirstFragment || (mFirstFragment.mWebView!=null && mFirstFragment.mWebView.canGoBack())) {
                            mCurrentFragment = mFirstFragment;
                            mFirstFragment.createWebView(CadillacUrl.HOME_URL);
                        }
                    }
                    break;
                case SECOND:
                    if (null == mSecondFragment) {
                        mSecondFragment = WebViewFragment.newInstance(CadillacUrl.CENT_URL);
                        transaction.add(R.id.rl_fragment_container, mSecondFragment);
                        mCurrentFragment = mSecondFragment;
                    } else {
                        if (mCurrentFragment != mSecondFragment || (mSecondFragment.mWebView!=null && mSecondFragment.mWebView.canGoBack())){
                            mCurrentFragment = mSecondFragment;
                            mSecondFragment.createWebView(CadillacUrl.CENT_URL);
                        }
                    }
                    break;
                case THIRD:
                    if (null == mThirdFragment) {
                        mThirdFragment = new MessageFragment();
                        transaction.add(R.id.rl_fragment_container, mThirdFragment);
                    }
                    mCurrentFragment = mThirdFragment;
                    Toast.makeText(HomeActivity.this, "模块正在开发中，敬请期待...",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
        if (null != transaction && null != mCurrentFragment) {
            transaction.show(mCurrentFragment);
            transaction.commitAllowingStateLoss();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLogUtils.info(TAG+":onDestroy");
    }

    /**
     * back键被点击时
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mCurrentFragment != null && mCurrentFragment instanceof WebViewFragment && ((WebViewFragment)mCurrentFragment).mWebView != null){
                WebViewFragment current = (WebViewFragment) mCurrentFragment;
                WebView webView = current.mWebView;
                if (webView.canGoBack()) {
                    webView.goBack();
                    mRgTab.setVisibility(View.VISIBLE);
                    return true;
                }
            }
        }
        return super.onKeyDown(keyCode, event);

    }


    /**
     * 禁用恢复内存不够而被杀死的fragment实例，从而防止fragment重叠的现象
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        //    super.onRestoreInstanceState(savedInstanceState);
    }
}