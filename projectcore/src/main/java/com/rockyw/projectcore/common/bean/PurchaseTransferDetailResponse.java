package com.rockyw.projectcore.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/6/23
 */
public class PurchaseTransferDetailResponse extends BaseResponse implements Parcelable {

    public DataBean data;

    public static class DataBean implements Parcelable {
        public String productName;
        public int sn;
        public double transferPrice;
        public double oldValue;

        protected DataBean(Parcel in) {
            productName = in.readString();
            sn = in.readInt();
            transferPrice = in.readDouble();
            oldValue = in.readDouble();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(productName);
            dest.writeInt(sn);
            dest.writeDouble(transferPrice);
            dest.writeDouble(oldValue);
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

    public PurchaseTransferDetailResponse() {
    }

    protected PurchaseTransferDetailResponse(Parcel in) {
        data = in.readParcelable(DataBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(data, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PurchaseTransferDetailResponse> CREATOR = new Creator<PurchaseTransferDetailResponse>() {
        @Override
        public PurchaseTransferDetailResponse createFromParcel(Parcel in) {
            return new PurchaseTransferDetailResponse(in);
        }

        @Override
        public PurchaseTransferDetailResponse[] newArray(int size) {
            return new PurchaseTransferDetailResponse[size];
        }
    };
}
