package cn.wildfirechat.pojos.moments;


import java.util.List;

public class CommentMsgPojo {
    //commentId;
    public long commentId;

    //sender;
    public String sender;

    //timestamp
    public long serverTime;

    //feedId
    public long feedId;

    //type
    public int type;

    //content
    public String text;

    //to replyTo
    public String replyTo;

    //extra
    public String extra;

    public int ftype;

    public String fcontent;

    public String fsender;

    public List<MediaEntry> fmedias;
}
