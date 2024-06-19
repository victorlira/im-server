/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfirechat.message.core;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import cn.wildfirechat.message.MessageContentMediaType;
import cn.wildfirechat.proto.WFCMessage;

/**
 * Created by heavyrain lee on 2017/12/6.
 */

public class MessagePayload implements Parcelable {

    public /*MessageContentType*/ int type;
    public String searchableContent;
    public String pushContent;
    public String pushData;
    public String content;
    public byte[] binaryContent;

    public String extra;

    public int mentionedType;
    public List<String> mentionedTargets;

    public MessageContentMediaType mediaType;
    public String remoteMediaUrl;

    //前面的属性都会在网络发送，下面的属性只在本地存储
    public String localMediaPath;

    //前面的属性都会在网络发送，下面的属性只在本地存储
    public String localContent;

    public MessagePayload() {
    }

    public MessagePayload(cn.wildfirechat.pojos.MessagePayload protoMessageContent) {
        this.type = protoMessageContent.getType();
        this.searchableContent = protoMessageContent.getSearchableContent();
        this.pushContent = protoMessageContent.getPushContent();
        this.pushData = protoMessageContent.getPushData();
        this.content = protoMessageContent.getContent();
        this.binaryContent = Base64.getDecoder().decode(protoMessageContent.getBase64edData());
        //this.localContent = protoMessageContent.getLocalContent();
        this.remoteMediaUrl = protoMessageContent.getRemoteMediaUrl();
        //this.localMediaPath = protoMessageContent.getLocalMediaPath();
        this.mediaType = MessageContentMediaType.mediaType(protoMessageContent.getMediaType());
        this.mentionedType = protoMessageContent.getMentionedType();
        if (protoMessageContent.getMentionedTarget() != null) {
            this.mentionedTargets = protoMessageContent.getMentionedTarget();
        } else {
            this.mentionedTargets = new ArrayList<>();
        }
        this.extra = protoMessageContent.getExtra();
    }

    public MessagePayload(WFCMessage.MessageContent protoMessageContent) {
        this.type = protoMessageContent.getType();
        this.searchableContent = protoMessageContent.getSearchableContent();
        this.pushContent = protoMessageContent.getPushContent();
        this.pushData = protoMessageContent.getPushData();
        this.content = protoMessageContent.getContent();
        if (protoMessageContent.getData() != null && !protoMessageContent.getData().isEmpty()) {
            this.binaryContent = protoMessageContent.getData().toByteArray();
        }
        protoMessageContent.getContentBytes();
        //this.localContent = protoMessageContent.getLocalContent();
        this.remoteMediaUrl = protoMessageContent.getRemoteMediaUrl();
        //this.localMediaPath = protoMessageContent.getLocalMediaPath();
        this.mediaType = MessageContentMediaType.mediaType(protoMessageContent.getMediaType());
        this.mentionedType = protoMessageContent.getMentionedType();
        this.mentionedTargets = new ArrayList<>();
        this.mentionedTargets.addAll(protoMessageContent.getMentionedTargetList());
        this.extra = protoMessageContent.getExtra();
    }

    public cn.wildfirechat.pojos.MessagePayload toProtoContent() {
        cn.wildfirechat.pojos.MessagePayload out = new cn.wildfirechat.pojos.MessagePayload();
        out.setType(type);
        out.setSearchableContent(searchableContent);
        out.setPushContent(pushContent);
        out.setPushData(pushData);
        out.setContent(content);
        //out.setBinaryContent(binaryContent);
        out.setBase64edData(Base64.getEncoder().encodeToString(binaryContent));
        out.setRemoteMediaUrl(remoteMediaUrl);
        out.setMediaType(mediaType != null ? mediaType.ordinal() : 0);
        out.setMentionedType(mentionedType);
        out.setMentionedTarget(mentionedTargets);
        out.setExtra(extra);
        return out;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
        dest.writeString(this.searchableContent);
        dest.writeString(this.pushContent);
        dest.writeString(this.pushData);
        dest.writeString(this.content);
        dest.writeByteArray(this.binaryContent);
        dest.writeInt(this.mentionedType);
        dest.writeStringList(this.mentionedTargets);
        dest.writeInt(this.mediaType == null ? -1 : this.mediaType.ordinal());
        dest.writeString(this.remoteMediaUrl);
        dest.writeString(this.localMediaPath);
        dest.writeString(this.localContent);
        dest.writeString(this.extra);
    }

    public MessagePayload(Parcel in) {
        this.type = in.readInt();
        this.searchableContent = in.readString();
        this.pushContent = in.readString();
        this.pushData = in.readString();
        this.content = in.readString();
        this.binaryContent = in.createByteArray();
        this.mentionedType = in.readInt();
        this.mentionedTargets = in.createStringArrayList();
        int tmpMediaType = in.readInt();
        this.mediaType = tmpMediaType == -1 ? null : MessageContentMediaType.values()[tmpMediaType];
        this.remoteMediaUrl = in.readString();
        this.localMediaPath = in.readString();
        this.localContent = in.readString();
        this.extra = in.readString();
    }

    public static final Creator<MessagePayload> CREATOR = new Creator<MessagePayload>() {
        @Override
        public MessagePayload createFromParcel(Parcel source) {
            return new MessagePayload(source);
        }

        @Override
        public MessagePayload[] newArray(int size) {
            return new MessagePayload[size];
        }
    };
}
