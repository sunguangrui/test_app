package com.example.iris.product.entity;
/*
 *  @创建者    lihaijun
 *  @创建时间   2018/3/2 16:18
 *  @描述     ${TODO}
 *
 *  @更新者    $Author
 *  @更新时间   $Date
 *  @更新描述   ${TODO}
 */

import java.io.Serializable;

public class MessageEntity implements Serializable {

    private long timeStamp;
    private String imgUrl;
    private String title;
    private String content;
    private int type;
    private boolean read;

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
