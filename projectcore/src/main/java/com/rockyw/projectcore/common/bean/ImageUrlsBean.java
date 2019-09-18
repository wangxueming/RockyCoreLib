package com.rockyw.projectcore.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/3/20
 */
public class ImageUrlsBean implements Parcelable {
    public List<String> imageUrls = new ArrayList();

    public ImageUrlsBean() {
    }

    public ImageUrlsBean(String... urls) {
        for (int i = 0, size = urls.length; i < size; i++) {
            imageUrls.add(urls[i]);
        }
    }

    public ImageUrlsBean(List<String> urls) {
        if (urls == null || urls.size() == 0) {
            return;
        }
        imageUrls.addAll(urls);
    }


    public ImageUrlsBean(Parcel in) {
        imageUrls = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(imageUrls);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ImageUrlsBean> CREATOR = new Creator<ImageUrlsBean>() {
        @Override
        public ImageUrlsBean createFromParcel(Parcel in) {
            return new ImageUrlsBean(in);
        }

        @Override
        public ImageUrlsBean[] newArray(int size) {
            return new ImageUrlsBean[size];
        }
    };
}
