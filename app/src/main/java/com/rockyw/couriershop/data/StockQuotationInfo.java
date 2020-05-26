package com.rockyw.couriershop.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 *
 * @author along
 * @date 2017/10/27
 */
@Entity
public class StockQuotationInfo {
    @Id
    private Long id;

    private String orderNum;

    @Generated(hash = 291532085)
    public StockQuotationInfo(Long id, String orderNum) {
        this.id = id;
        this.orderNum = orderNum;
    }

    @Generated(hash = 1716714323)
    public StockQuotationInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return this.orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }
}
