package com.rockyw.projectcore.common.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.rockyw.projectcore.net.BaseResponse;

import java.util.List;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/1/14
 */
public class ProductDetailResponse extends BaseResponse implements Parcelable {

    public DataBean data;

    public static class DataBean implements Parcelable {
        public int accessType;
        public int activityType;
        public int addUnit;
        public int allowInvest;
        public String assignType;
        public int assignTypeCode;
        public AwardGuessBean awardGuess;
        public double bonus;
        public String bonusShowName;
        public String bonusType;
        public String buyAcctTypes;
        public String buyFromTime;
        public long buyFromTimeMillis;
        public String buyToTime;
        public long buyToTimeMillis;
        public String createdTime;
        public String custody;
        /**
         * 投资期限计算
         */
        public int daysOfYear;
        public String delegator;
        public int duration;
        public int entryUnit;
        public String financeType;
        public double highestYield;
        public String icon;
        public String isOnlyForFirst;
        public String label;
        public double lowestYield;
        public int maxUnit;
        public double maxYield;
        public String name;
        public int naturalSeasonRepaymentDay;
        /**
         * 最小计算到 元 或者分
         */
        public int precision;
        public String productType;
        public String productTypeName;
        public double progress;
        public String publishDate;
        public boolean qualified;
        public int quotaForFirst;
        public String realSettlementDate;
        public String realValueEndDate;
        public String realValueStartDate;
        public double remainQuota;
        public String remark;
        public String riskInfo;
        public long serverTimeMillis;
        public String settlementDate;
        public String settlementDateDesc;
        public int showDefaultStairYield;
        public String showImage;
        public boolean showSymbol;
        public int showTagType;
        public int sn;
        public int stageNum;
        public int status;
        public double stockAwardYield;
        public long stockBeginDate;
        public long stockEndDate;
        public String supportCouponType;
        public int supportRedeem;
        public int supportStock;
        public double totalQuota;
        public double unitValue;
        public String valueEndDate;
        public String valueStartDate;
        public String welfare;
        public double wzeCardYield;
        public String wzeCardYieldOnOff;
        public double yield;
        public double yieldForFirst;
        public int yieldRise;
        public List<DefaultStairYieldListBean> defaultStairYieldList;
        public List<ExtralYieldListBean> extralYieldList;
        public List<RedeemScheduleListBean> redeemScheduleList;

        public static class AwardGuessBean {
            public String appDetailImage;
            public String appGuideImage;
            public long beginTime;
            public long createTime;
            public long endTime;
            public String name;
            public int productSn;
            public int resultChoiceSn;
            public String rule;
            public int sn;
            public int type;
            public String typeExplain;
            public long updateTime;
            public String webShowImage;
            public int willing;
            public List<GuessChoicesBean> guessChoices;

            public static class GuessChoicesBean {
                public int awardGuessSn;
                public double loseYield;
                public String name;
                public int sn;
                public double supportRate;
                public double winYield;
            }
        }

        public static class DefaultStairYieldListBean {
            public int areaSn;
            public String areaSns;
            public double endAmount;
            public String needWzeCard;
            public int sn;
            public double startAmount;
            public int summarySn;
            public double yield;
        }

        public static class ExtralYieldListBean {
            public String dispayText;
            public String name;
            public int type;
            public double yield;
        }

        public static class RedeemScheduleListBean {
            public double baseYield;
            public String createTime;
            public int duration;
            public String productName;
            public int productSn;
            public String remark;
            public String settlementDate;
            public int sn;
            public int stairYieldSn;
            public int status;
            public int type;
            public String updateTime;
            public String valueEndDate;
            public String valueStartDate;
            /**
             * 外部的extRedeemListSize
             *
             * @link ProductDetailResponse.DataBean.settlementDate
             */
            public int extRedeemListSize;
        }

        protected DataBean(Parcel in) {
            accessType = in.readInt();
            activityType = in.readInt();
            addUnit = in.readInt();
            allowInvest = in.readInt();
            assignType = in.readString();
            assignTypeCode = in.readInt();
            bonus = in.readDouble();
            bonusShowName = in.readString();
            bonusType = in.readString();
            buyAcctTypes = in.readString();
            buyFromTime = in.readString();
            buyFromTimeMillis = in.readLong();
            buyToTime = in.readString();
            buyToTimeMillis = in.readLong();
            createdTime = in.readString();
            custody = in.readString();
            daysOfYear = in.readInt();
            delegator = in.readString();
            duration = in.readInt();
            entryUnit = in.readInt();
            financeType = in.readString();
            highestYield = in.readDouble();
            icon = in.readString();
            isOnlyForFirst = in.readString();
            label = in.readString();
            lowestYield = in.readDouble();
            maxUnit = in.readInt();
            maxYield = in.readDouble();
            name = in.readString();
            naturalSeasonRepaymentDay = in.readInt();
            precision = in.readInt();
            productType = in.readString();
            productTypeName = in.readString();
            progress = in.readDouble();
            publishDate = in.readString();
            qualified = in.readByte() != 0;
            quotaForFirst = in.readInt();
            realSettlementDate = in.readString();
            realValueEndDate = in.readString();
            realValueStartDate = in.readString();
            remainQuota = in.readDouble();
            remark = in.readString();
            riskInfo = in.readString();
            serverTimeMillis = in.readLong();
            settlementDate = in.readString();
            settlementDateDesc = in.readString();
            showDefaultStairYield = in.readInt();
            showImage = in.readString();
            showSymbol = in.readByte() != 0;
            showTagType = in.readInt();
            sn = in.readInt();
            stageNum = in.readInt();
            status = in.readInt();
            stockAwardYield = in.readDouble();
            stockBeginDate = in.readLong();
            stockEndDate = in.readLong();
            supportCouponType = in.readString();
            supportRedeem = in.readInt();
            supportStock = in.readInt();
            totalQuota = in.readDouble();
            unitValue = in.readDouble();
            valueEndDate = in.readString();
            valueStartDate = in.readString();
            welfare = in.readString();
            wzeCardYield = in.readDouble();
            wzeCardYieldOnOff = in.readString();
            yield = in.readDouble();
            yieldForFirst = in.readDouble();
            yieldRise = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(accessType);
            dest.writeInt(activityType);
            dest.writeInt(addUnit);
            dest.writeInt(allowInvest);
            dest.writeString(assignType);
            dest.writeInt(assignTypeCode);
            dest.writeDouble(bonus);
            dest.writeString(bonusShowName);
            dest.writeString(bonusType);
            dest.writeString(buyAcctTypes);
            dest.writeString(buyFromTime);
            dest.writeLong(buyFromTimeMillis);
            dest.writeString(buyToTime);
            dest.writeLong(buyToTimeMillis);
            dest.writeString(createdTime);
            dest.writeString(custody);
            dest.writeInt(daysOfYear);
            dest.writeString(delegator);
            dest.writeInt(duration);
            dest.writeInt(entryUnit);
            dest.writeString(financeType);
            dest.writeDouble(highestYield);
            dest.writeString(icon);
            dest.writeString(isOnlyForFirst);
            dest.writeString(label);
            dest.writeDouble(lowestYield);
            dest.writeInt(maxUnit);
            dest.writeDouble(maxYield);
            dest.writeString(name);
            dest.writeInt(naturalSeasonRepaymentDay);
            dest.writeInt(precision);
            dest.writeString(productType);
            dest.writeString(productTypeName);
            dest.writeDouble(progress);
            dest.writeString(publishDate);
            dest.writeByte((byte) (qualified ? 1 : 0));
            dest.writeInt(quotaForFirst);
            dest.writeString(realSettlementDate);
            dest.writeString(realValueEndDate);
            dest.writeString(realValueStartDate);
            dest.writeDouble(remainQuota);
            dest.writeString(remark);
            dest.writeString(riskInfo);
            dest.writeLong(serverTimeMillis);
            dest.writeString(settlementDate);
            dest.writeString(settlementDateDesc);
            dest.writeInt(showDefaultStairYield);
            dest.writeString(showImage);
            dest.writeByte((byte) (showSymbol ? 1 : 0));
            dest.writeInt(showTagType);
            dest.writeInt(sn);
            dest.writeInt(stageNum);
            dest.writeInt(status);
            dest.writeDouble(stockAwardYield);
            dest.writeLong(stockBeginDate);
            dest.writeLong(stockEndDate);
            dest.writeString(supportCouponType);
            dest.writeInt(supportRedeem);
            dest.writeInt(supportStock);
            dest.writeDouble(totalQuota);
            dest.writeDouble(unitValue);
            dest.writeString(valueEndDate);
            dest.writeString(valueStartDate);
            dest.writeString(welfare);
            dest.writeDouble(wzeCardYield);
            dest.writeString(wzeCardYieldOnOff);
            dest.writeDouble(yield);
            dest.writeDouble(yieldForFirst);
            dest.writeInt(yieldRise);
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

    public ProductDetailResponse() {
    }

    public ProductDetailResponse(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProductDetailResponse> CREATOR = new Creator<ProductDetailResponse>() {
        @Override
        public ProductDetailResponse createFromParcel(Parcel in) {
            return new ProductDetailResponse(in);
        }

        @Override
        public ProductDetailResponse[] newArray(int size) {
            return new ProductDetailResponse[size];
        }
    };
}
