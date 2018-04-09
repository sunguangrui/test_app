package com.example.iris.product.adapter;
/*
 *  @创建者    lihaijun
 *  @创建时间   2018/3/14 17:52
 *  @描述     ${TODO}
 *
 *  @更新者    $Author
 *  @更新时间   $Date
 *  @更新描述   ${TODO}
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iris.product.R;
import com.example.iris.product.entity.MessageEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter {

private static final int TYPE_DATE       = 0;
private static final int TYPE_TEXT_IMAGE = 1;
private static final int TYPE_TEXT       = 2;
private List<MessageEntity> mData;
private Context             mContext;

public MessageAdapter(Context context,List<MessageEntity> data) {
        this.mData = data;
        this.mContext = context;
        }

public List<MessageEntity> getData() {
        return mData;
        }

public void setData(List<MessageEntity> data) {
        mData = data;
        }

@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_TEXT_IMAGE) {//带图片
        view = LayoutInflater.from(mContext).inflate(R.layout.item_message_image,parent, false);
        return new ImageTextHolder(view);
        } else if (viewType == TYPE_TEXT) {//纯文字
        view = LayoutInflater.from(mContext).inflate(R.layout.item_message_text,parent, false);
        return new TextHolder(view);
        } else if (viewType == TYPE_DATE) {//日期
        view = LayoutInflater.from(mContext).inflate(R.layout.item_message_date,parent, false);
        return new DateHolder(view);
        } else {
        return null;
        }
        }

@Override
public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MessageEntity messageEntity = mData.get(position);
        if (holder instanceof ImageTextHolder) {
        ((ImageTextHolder) holder).mContent.setText(messageEntity.getContent());
        ((ImageTextHolder) holder).mTitle.setText(messageEntity.getTitle());
        } else if (holder instanceof TextHolder) {
        ((TextHolder) holder).mContent.setText(messageEntity.getContent());
        ((TextHolder) holder).mTitle.setText(messageEntity.getTitle());

        } else if (holder instanceof DateHolder) {
        ((DateHolder) holder).mDate.setText(stampToDate(messageEntity.getTimeStamp()));

        }
        }



@Override
public int getItemCount() {
        if (mData != null) {
        return mData.size();
        }
        return 0;
        }


@Override
public int getItemViewType(int position) {
        MessageEntity messageEntity = mData.get(position);
        return messageEntity.getType();
        //        return super.getItemViewType(position);
        }


private class DateHolder extends RecyclerView.ViewHolder {

    private TextView mDate;

    DateHolder(View itemView) {
        super(itemView);
        mDate = itemView.findViewById(R.id.tv_message_date);
    }
}

private class ImageTextHolder extends RecyclerView.ViewHolder {
    private TextView mTitle;
    private TextView mContent;
    ImageTextHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_message_title);
        mContent = itemView.findViewById(R.id.tv_message_content);
    }
}

private class TextHolder extends RecyclerView.ViewHolder {

    private TextView mTitle;
    private TextView mContent;

    TextHolder(View itemView) {
        super(itemView);
        mTitle = itemView.findViewById(R.id.tv_message_title);
        mContent = itemView.findViewById(R.id.tv_message_content);
    }
}

    /*
 * 将时间戳转换为时间
 */
    public static String stampToDate(long s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //        long lt = new Long(s);
        Date date = new Date(s);
        res = simpleDateFormat.format(date);
        return res;
    }
}
