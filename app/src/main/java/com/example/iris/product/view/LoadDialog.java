package com.example.iris.product.view;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iris.product.R;

/**
 * Created by iris on 2017/12/1.
 */

public class LoadDialog {
    private Dialog dialog;
    private Context context;
    private Display display;
    private String text;
    private ImageView iv;
    private TextView tv;

    public LoadDialog(Context context,String text) {
        this.context = context;
        this.text=text;
        WindowManager manager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        display=manager.getDefaultDisplay();
    }

    public LoadDialog builder(){
        View view= LayoutInflater.from(context).inflate(R.layout.dialog,null);
        view.setMinimumHeight(display.getWidth());//设置最小宽度
        iv=view.findViewById(R.id.img);
        tv=view.findViewById(R.id.tipTextView);
        if(!TextUtils.isEmpty(text)){
            tv.setVisibility(View.VISIBLE);
            tv.setText(text);
        }else {
            tv.setVisibility(View.GONE);
        }
        // 加载动画
//        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
//                context, R.anim.);
        Animation animation=AnimationUtils.loadAnimation(context,R.anim.load_dilog);
        // 使用ImageView显示动画
        iv.startAnimation(animation);

        dialog=new Dialog(context,R.style.ActionSheetDialogStyle);
        dialog.setContentView(view);
        Window window=dialog.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp=window.getAttributes();
        lp.x=0;
        lp.y=0;
        window.setAttributes(lp);
        return this;
    }


    /**
     * 设置点击外面是否消失
     * @param cancel
     * @return
     */
   public LoadDialog setCancledOnTouchOutside(boolean cancel){

        dialog.setCancelable(cancel);
        return this;
   }


   public void show(){
        dialog.show();
   }

   public void dissmiss(){
       dialog.dismiss();
       dialog.cancel();
   }

}
