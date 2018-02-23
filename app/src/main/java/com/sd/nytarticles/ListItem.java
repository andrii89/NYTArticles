package com.sd.nytarticles;

import java.util.UUID;

/**
 * Created by AzAlex2 on 21.02.2018.
 */

public class ListItem {

    private UUID mId;
    private Boolean mChecked;
    private String mCaption;
    private String mColumn;
    private String mSection;
    private String mAbstract;
    private String mPublishedDate;
    private String mByline;
    private String mUrl;
    private String mImageUrl;

    public ListItem () {
        mId = UUID.randomUUID();
        mChecked = false;
    }

    public ListItem (UUID id) {
        mId = id;
        mChecked = true;
    }

    public void setCaption(String caption) {
        mCaption = caption;
    }

    public void setChecked(Boolean checked) {
        mChecked = checked;
    }

    public void setColumn(String column) {
        mColumn = column;
    }

    public void setSection(String section) {
        mSection = section;
    }

    public void setAbstract(String anAbstract) {
        mAbstract = anAbstract;
    }

    public void setPublishedDate(String publishedDate) {
        mPublishedDate = publishedDate;
    }

    public void setByline(String byline) {
        mByline = byline;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }



    public UUID getId() {
        return mId;
    }

    public Boolean getChecked() {
        return mChecked;
    }

    public String getCaption() {
        return mCaption;
    }

    public String getColumn() {
        return mColumn;
    }

    public String getSection() {
        return mSection;
    }

    public String getAbstract() {
        return mAbstract;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public String getByline() {
        return mByline;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    @Override
    public String toString(){
        return mCaption;
    }
}
