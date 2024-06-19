/*
 * Copyright (c) 2020 WildFireChat. All rights reserved.
 */

package cn.wildfirechat.message;

import android.os.Parcel;

import cn.wildfirechat.model.Location;
import org.json.JSONException;
import org.json.JSONObject;

import cn.wildfirechat.message.core.ContentTag;
import cn.wildfirechat.message.core.MessagePayload;
import cn.wildfirechat.message.core.PersistFlag;

import static cn.wildfirechat.message.core.MessageContentType.ContentType_Location;

/**
 * Created by heavyrain lee on 2017/12/6.
 */

@ContentTag(type = ContentType_Location, flag = PersistFlag.Persist_And_Count)
public class LocationMessageContent extends MessageContent {
    private String title;
    public byte[] thumbnailByte;
    private Location location;

    public LocationMessageContent() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = super.encode();
        payload.searchableContent = title;
        payload.binaryContent = this.thumbnailByte;

        try {
            JSONObject objWrite = new JSONObject();
            objWrite.put("lat", location.getLatitude());
            objWrite.put("long", location.getLongitude());

            payload.content = objWrite.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return payload;
    }


    @Override
    public void decode(MessagePayload payload) {
        if (payload.binaryContent != null) {
            this.thumbnailByte = payload.binaryContent;
        }
        title = payload.searchableContent;
        try {
            if (payload.content != null) {
                JSONObject jsonObject = new JSONObject(payload.content);
                location.setLatitude(jsonObject.optDouble("lat"));
                location.setLongitude(jsonObject.optDouble("long"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String digest(Message message) {
        return "位置";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.title);
        dest.writeByteArray(this.thumbnailByte);
        dest.writeParcelable(this.location, flags);
    }

    protected LocationMessageContent(Parcel in) {
        super(in);
        this.title = in.readString();
        this.thumbnailByte = in.createByteArray();
        this.location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<LocationMessageContent> CREATOR = new Creator<LocationMessageContent>() {
        @Override
        public LocationMessageContent createFromParcel(Parcel source) {
            return new LocationMessageContent(source);
        }

        @Override
        public LocationMessageContent[] newArray(int size) {
            return new LocationMessageContent[size];
        }
    };
}
