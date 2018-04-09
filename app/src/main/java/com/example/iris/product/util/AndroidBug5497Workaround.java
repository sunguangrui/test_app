package com.example.iris.product.util;
/*
 *  @创建者    lihaijun
 *  @创建时间   2018/2/26 16:56
 *  @描述     ${Android5497号bug解决类，使带有Webview里软键盘弹出input可见}
 *
 *  @更新者    $Author
 *  @更新时间   $Date
 *  @更新描述   ${TODO}
 */

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;

public class AndroidBug5497Workaround {

    private static final String TAG = "AndroidBug5497Workaroun";
    // For more information, see https://issuetracker.google.com/issues/36911528
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    public static void assistActivity (Activity activity,OnKeyboardToggleListener onKeyboardToggleListener) {
        new AndroidBug5497Workaround(activity,onKeyboardToggleListener);
    }

    private View                     mChildOfContent;
    private int                      usableHeightPrevious;
    private FrameLayout.LayoutParams frameLayoutParams;

    private AndroidBug5497Workaround(Activity activity, final OnKeyboardToggleListener onKeyboardToggleListener) {
        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        mChildOfContent = content.getChildAt(0);
        mChildOfContent.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                possiblyResizeChildOfContent(onKeyboardToggleListener);
            }
        });
        frameLayoutParams = (FrameLayout.LayoutParams) mChildOfContent.getLayoutParams();
    }

    private void possiblyResizeChildOfContent(OnKeyboardToggleListener onKeyboardToggleListener)  {
        int usableHeightNow = computeUsableHeight();
        if (usableHeightNow != usableHeightPrevious) {
            int usableHeightSansKeyboard = mChildOfContent.getRootView().getHeight();
            int heightDifference = usableHeightSansKeyboard - usableHeightNow;
            if (heightDifference > (usableHeightSansKeyboard/4)) {
                // keyboard probably just became visible
//                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference;
                onKeyboardToggleListener.onKeyboardShown(heightDifference);
            } else {
                // keyboard probably just became hidden
//                frameLayoutParams.height = usableHeightSansKeyboard;
                onKeyboardToggleListener.onKeyboardClosed();
            }
//            mChildOfContent.requestLayout();
            usableHeightPrevious = usableHeightNow;
        }
    }

    private int computeUsableHeight() {
        Rect r = new Rect();
        mChildOfContent.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top);
//        return r.bottom;
    }

    public interface OnKeyboardToggleListener {
        void onKeyboardShown(int keyboardSize);

        void onKeyboardClosed();
    }
}
