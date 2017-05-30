package com.kefujiqiren.bean;

/**
 * Created by 殇痕 on 2017/4/2.
 */

public class Msg {
    public static final int TYPE_RECEIVED=0;
    public static final int TYPE_SENT=1;
    public static final int TYPE_TIME=2;
    private String content;
    private String time;
    private int type;
    public Msg(String content, String time, int type) {
        this.content = content;
        this.time = time;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
