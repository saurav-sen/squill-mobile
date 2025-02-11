package com.pack.pack.application.topic.activity.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.pack.pack.common.util.JSONUtil;
import com.pack.pack.model.web.JPack;
import com.pack.pack.model.web.JPackAttachment;
import com.pack.pack.services.exception.PackPackException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Saurav on 21-05-2016.
 */
public class ParcelablePack implements Parcelable {

    private String id;

    private String story;

    private String creatorName;

    private String title;

    private String rating;

    private long creationTime;

    private int likes;

    private int views;

    private String parentTopicId;

    public String getNextLink() {
        return nextLink;
    }

    public void setNextLink(String nextLink) {
        this.nextLink = nextLink;
    }

    public String getPreviousLink() {
        return previousLink;
    }

    public void setPreviousLink(String previousLink) {
        this.previousLink = previousLink;
    }

    private String nextLink;

    private String previousLink;

    public ParcelablePack() {
    }

    public ParcelablePack(JPack pack) {
        this(pack.getId(), pack.getStory(), pack.getCreatorName(), pack.getTitle(),
                pack.getRating(), pack.getCreationTime(), pack.getLikes(), pack.getViews(),
                pack.getParentTopicId());
    }

    public ParcelablePack(String id, String story, String creatorName, String title,
                          String rating, long creationTime, int likes, int views, String parentTopicId) {
        setId(id);
        setStory(story);
        setCreatorName(creatorName);
        setTitle(title);
        setRating(rating);
        setCreationTime(creationTime);
        setLikes(likes);
        setViews(views);
        setParentTopicId(parentTopicId);
        setNextLink("FIRST_PAGE");
        setPreviousLink("FIRST_PAGE");
    }

    public String getParentTopicId() {
        return parentTopicId;
    }

    public void setParentTopicId(String parentTopicId) {
        this.parentTopicId = parentTopicId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

   @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(story);
        parcel.writeString(creatorName);
        parcel.writeString(title);
        parcel.writeString(rating);
        parcel.writeLong(creationTime);
        parcel.writeInt(likes);
        parcel.writeInt(views);
        parcel.writeString(parentTopicId);
        if(nextLink != null) {
            parcel.writeString(nextLink);
        } else {
            parcel.writeString("FIRST_PAGE");
        }
        if(previousLink != null) {
            parcel.writeString(previousLink);
        } else {
            parcel.writeString("FIRST_PAGE");
        }
    }

    public static final Parcelable.Creator<ParcelablePack> CREATOR = new Parcelable.Creator<ParcelablePack>() {

        @Override
        public ParcelablePack createFromParcel(Parcel parcel) {
            String id = parcel.readString();
            String story = parcel.readString();
            String creatorName = parcel.readString();
            String title = parcel.readString();
            String rating = parcel.readString();
            long creationTime = parcel.readLong();
            int likes = parcel.readInt();
            int views = parcel.readInt();
            String parentTopicId = parcel.readString();
            String nextLink = parcel.readString();
            String previousLink = parcel.readString();
            ParcelablePack result = new ParcelablePack(id, story, creatorName, title, rating, creationTime, likes, views, parentTopicId);
            if(nextLink != null) {
                result.setNextLink(nextLink);
            } else {
                result.setNextLink("FIRST_PAGE");
            }
            if(previousLink != null) {
                result.setPreviousLink(previousLink);
            } else {
                result.setPreviousLink("FIRST_PAGE");
            }
            return result;
        }

        @Override
        public ParcelablePack[] newArray(int size) {
            return new ParcelablePack[size];
        }
    };
}
