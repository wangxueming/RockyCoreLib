package com.rockyw.projectcore.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/6/19
 */
public class InquiryProductDetailResponse extends BaseResponse implements Parcelable {

    public DataBean data;

    public static class DataBean implements Parcelable {
        public String assignType;
        public int duration;
        public String endDate;
        public double oldProductYield;
        public String productName;
        public int productSn;
        public int sn;
        public double transferPrice;
        public double yield;

        protected DataBean(Parcel in) {
            assignType = in.readString();
            duration = in.readInt();
            endDate = in.readString();
            oldProductYield = in.readDouble();
            productName = in.readString();
            productSn = in.readInt();
            sn = in.readInt();
            transferPrice = in.readDouble();
            yield = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(assignType);
            dest.writeInt(duration);
            dest.writeString(endDate);
            dest.writeDouble(oldProductYield);
            dest.writeString(productName);
            dest.writeInt(productSn);
            dest.writeInt(sn);
            dest.writeDouble(transferPrice);
            dest.writeDouble(yield);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };
    }

    public InquiryProductDetailResponse() {
    }

    protected InquiryProductDetailResponse(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InquiryProductDetailResponse> CREATOR = new Creator<InquiryProductDetailResponse>() {
        @Override
        public InquiryProductDetailResponse createFromParcel(Parcel in) {
            return new InquiryProductDetailResponse(in);
        }

        @Override
        public InquiryProductDetailResponse[] newArray(int size) {
            return new InquiryProductDetailResponse[size];
        }
    };
}
