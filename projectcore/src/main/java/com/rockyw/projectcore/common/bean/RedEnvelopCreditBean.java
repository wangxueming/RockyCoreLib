package com.rockyw.projectcore.common.bean;

/**
 * 是否获得红包或者积分的基础类
 *
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/7/5
 */
public class RedEnvelopCreditBean {

    public boolean redPacketGet;
    public double redPacketQuota;
    public boolean integralGet;
    public double integralQuota;

    /**
     * 本地额外增加的字段
     * 用来匹配，红包是在什么埋点类型中产生的
     */
    public String type;
    /**
     * 本地额外增加的字段
     * 用在红包dialog上
     */
    public String title;
    /**
     * 本地额外增加的字段
     * 判断埋点的数据是否应该show出来。
     * true：show出来，show之后。要设置为false
     * false：则不show
     */
    public boolean status;

    public RedEnvelopCreditBean(){}

    public RedEnvelopCreditBean(boolean redPacketGet, double redPacketQuota, boolean integralGet, double integralQuota) {
        this.redPacketGet = redPacketGet;
        this.redPacketQuota = redPacketQuota;
        this.integralGet = integralGet;
        this.integralQuota = integralQuota;
    }

    public RedEnvelopCreditBean(boolean redPacketGet, double redPacketQuota, boolean integralGet, double integralQuota, boolean status) {
        this.redPacketGet = redPacketGet;
        this.redPacketQuota = redPacketQuota;
        this.integralGet = integralGet;
        this.integralQuota = integralQuota;
        this.status = status;
    }
//
//    protected RedEnvelopCreditBean(Parcel in) {
//        redPacketGet = in.readByte() != 0;
//        redPacketQuota = in.readDouble();
//        integralGet = in.readByte() != 0;
//        integralQuota = in.readDouble();
//        type = in.readString();
//        title = in.readString();
//        status = in.readByte() != 0;
//    }
//
//    @Override
//    public void writeToParcel(Parcel dest, int flags) {
//        dest.writeByte((byte) (redPacketGet ? 1 : 0));
//        dest.writeDouble(redPacketQuota);
//        dest.writeByte((byte) (integralGet ? 1 : 0));
//        dest.writeDouble(integralQuota);
//        dest.writeString(type);
//        dest.writeString(title);
//        dest.writeByte((byte) (status ? 1 : 0));
//    }
//
//    @Override
//    public int describeContents() {
//        return 0;
//    }
//
//    public static final Creator<RedEnvelopCreditBean> CREATOR = new Creator<RedEnvelopCreditBean>() {
//        @Override
//        public RedEnvelopCreditBean createFromParcel(Parcel in) {
//            return new RedEnvelopCreditBean(in);
//        }
//
//        @Override
//        public RedEnvelopCreditBean[] newArray(int size) {
//            return new RedEnvelopCreditBean[size];
//        }
//    };
}
