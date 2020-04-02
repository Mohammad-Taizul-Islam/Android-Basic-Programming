package com.example.dday.recyclerviewdemo;

public class ExampleItem {

    private int mImageResourcesId;
    private String mText1;
    private String mText2;

    public ExampleItem(int mImageResourcesId, String mText1, String mText2) {
        this.mImageResourcesId = mImageResourcesId;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResourcesId() {
        return mImageResourcesId;
    }

    public String getmText1() {
        return mText1;
    }

    public String getmText2() {
        return mText2;
    }
}
