package com.rockyw.projectcore.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.rockyw.projectcore.net.BaseResponse;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/15
 */
public class ProductDetailPjtBriefResponse extends BaseResponse implements Parcelable {

    public DataBean data;

    public static class DataBean implements Parcelable {
        public String borrowerInfo;
        public String capitalPurpose;
        public int contractSn;
        public String custody;
        public int delegator;
        public String label;
        public String productIntro;
        public String realBorrower;
        public String repaySource;
        public String riskInfo;
        public String riskLevel;
        public String safeguard;
        public int sn;

        protected DataBean(Parcel in) {
            borrowerInfo = in.readString();
            capitalPurpose = in.readString();
            contractSn = in.readInt();
            custody = in.readString();
            delegator = in.readInt();
            label = in.readString();
            productIntro = in.readString();
            realBorrower = in.readString();
            repaySource = in.readString();
            riskInfo = in.readString();
            riskLevel = in.readString();
            safeguard = in.readString();
            sn = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(borrowerInfo);
            dest.writeString(capitalPurpose);
            dest.writeInt(contractSn);
            dest.writeString(custody);
            dest.writeInt(delegator);
            dest.writeString(label);
            dest.writeString(productIntro);
            dest.writeString(realBorrower);
            dest.writeString(repaySource);
            dest.writeString(riskInfo);
            dest.writeString(riskLevel);
            dest.writeString(safeguard);
            dest.writeInt(sn);
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

    protected ProductDetailPjtBriefResponse(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductDetailPjtBriefResponse> CREATOR = new Creator<ProductDetailPjtBriefResponse>() {
        @Override
        public ProductDetailPjtBriefResponse createFromParcel(Parcel in) {
            return new ProductDetailPjtBriefResponse(in);
        }

        @Override
        public ProductDetailPjtBriefResponse[] newArray(int size) {
            return new ProductDetailPjtBriefResponse[size];
        }
    };
}
