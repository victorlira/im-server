package cn.wildfirechat.pojos.moments;


import java.util.List;

public class FeedPojo {
    //feedid
    public long feedId;

    //sender
    public String sender;

    //timestamp
    public long timestamp;

    //type
    public int type;

    //content
    public String text;

    //media urls
    public List<MediaEntry> medias;

    //to users
    public List<String> to;

    //excloud users
    public List<String> ex;

    //mentioned users
    public List<String> mu;

    //extra
    public String extra;

    public List<CommentPojo> comments;
}
