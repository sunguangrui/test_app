package com.example.iris.product.fragment;
/*
 *  @创建者    lihaijun
 *  @创建时间   2018/3/14 17:51
 *  @描述     ${TODO}
 *
 *  @更新者    $Author
 *  @更新时间   $Date
 *  @更新描述   ${TODO}
 */

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iris.product.R;
import com.example.iris.product.adapter.MessageAdapter;
import com.example.iris.product.entity.MessageEntity;
import com.example.iris.product.view.LoadDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MessageFragment extends Fragment {


    private Context mContext;
    private RecyclerView mRvContent;
    private SwipeRefreshLayout mSrlFresh;

    private List<MessageEntity> mData;

    private String x =
            "从结果来看旧金山警察的枪法着实令人着急警方消息他们会询问参与枪战的警员为什么开这么多枪" +
                    "他们还公布了这7名警察的姓名。其中，5名警察在枪战发生前就打开了执法记录仪，另2名不知是否因过于慌张，在结束后才打开。" +
                    "据报道，去年12月，旧金山新人警员透过巡逻车窗户，射杀了一42岁男子。这是自该事件发生后上报的首场枪战。" +
                    "报道称，“可能是没有伤亡的缘故”，这场枪战并未在当地引发多少争议。当地居民在市政厅参加此次枪战相关的大会时，似乎更关心该地区近年来大量涌入的流浪汉及犯罪问题。" +
                    "然而，一名在枪战事发街区工作了15年的居民还是表示，执法记录仪拍下的画面“非常令人震惊”。" +
                    "文章援引他的话称，“我半夜里接到员工电话。他当时就在现场，听到了枪声，看到了整件事的经过。这里会有人走进来威胁我们，每天都要面对这些让人有点创伤后应激障碍PTSD。”该居民为防报复，还要求匿名。";
    private MessageAdapter mAdapter;
    private LoadDialog     mDialog;

    private Handler sHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (mData != null && mData.size() > 0) {
                        mAdapter.setData(mData);
                        mAdapter.notifyDataSetChanged();
                        if (mDialog != null) {
                            mDialog.dissmiss();
                        }
                    }
                    break;
                case 2:
                    break;
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = (Context) getActivity();
//        mRvContent = view.findViewById(R.id.rv_content);
//        mSrlFresh = view.findViewById(R.id.srl_fresh);
//        init();
    }

    private void init() {
        final char[] chars = x.toCharArray();
        mRvContent.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new MessageAdapter(mContext,null);
        mRvContent.setAdapter(mAdapter);
        mDialog = new LoadDialog(mContext,"");
        mDialog.builder().show();
        mDialog.setCancledOnTouchOutside(false);
        new Thread(){
            @Override
            public void run() {
                super.run();
                mData = initData(chars);
                Message message = Message.obtain();
                message.what = 1;
                sHandler.sendMessage(message);
            }
        }.start();
    }

    private List<MessageEntity> initData(char[] chars) {
        int size = 10;
        List<MessageEntity> d = new ArrayList<>();
        boolean isDate = false;
        for (int i = 0; i < size; i++) {
            int v = (int) (Math.random() * 3);
            while (isDate){
                int i1 = (int) (Math.random() * 4);
                if (i1 > 0) {
                    break;
                }
            }
            MessageEntity message = new MessageEntity();
            message.setType(v);
            switch (v) {
                case 0://date
                    createDate(message);
                    break;
                case 1://image
                    createDate(message);
                    createTitleAndContent(message,chars);
                    break;
                case 2://text
                    createDate(message);
                    createTitleAndContent(message,chars);
                    break;
            }
            d.add(message);
            if (v == 0) {
                isDate = true;
            } else {
                isDate = false;
            }
        }
        return listData(d);
    }



    private List<MessageEntity> listData(List<MessageEntity> d) {
        Collections.sort(d, new Comparator<MessageEntity>() {

            @Override
            public int compare(MessageEntity o1, MessageEntity o2) {
                long i = o1.getTimeStamp() - o2.getTimeStamp();
                if(i == 0){
                    //                    return o1.getTitle().length() - o2.getTitle().length() ;
                    return 1 ;
                }
                return i >= 0 ? -1 : 1;
            }
        });
        return d;
    }
    //1519985704
    private void createDate(MessageEntity message) {
        //        int l = (int) SystemClock.currentThreadTimeMillis();
        long l = System.currentTimeMillis();
        long x = (long)((Math.random() * 1111111) + 1);
        if (l > x) {
            long y = l - x;
            message.setTimeStamp(y);
        } else {
            message.setTimeStamp(l + 1);
        }
    }

    private void createTitleAndContent(MessageEntity messageEntity,char[] chars) {
        int titleLength = (int) (Math.random() * 9) + 1;
        int contentLength = (int) (Math.random() * (chars.length - 1)) + 1;
        String content = "";
        String title = "";

        for (int n = 0; n < titleLength; n++) {
            title += chars[n];
        }
        for (int m = 0; m < contentLength; m++) {
            content += chars[m];
        }
        messageEntity.setTitle(title);
        messageEntity.setContent(content);
    }
}
