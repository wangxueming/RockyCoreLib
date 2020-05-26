package com.rockyw.couriershop.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/9/22
 */
@Entity
public class CompanyInfoListEntity {

    @Id
    private Long id;

    private String ts_code;
    private String symbol;
    private String name;
    private String area;
    private String industry;
    private String list_date;
    @Generated(hash = 1931731437)
    public CompanyInfoListEntity(Long id, String ts_code, String symbol,
            String name, String area, String industry, String list_date) {
        this.id = id;
        this.ts_code = ts_code;
        this.symbol = symbol;
        this.name = name;
        this.area = area;
        this.industry = industry;
        this.list_date = list_date;
    }
    @Generated(hash = 1548921050)
    public CompanyInfoListEntity() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTs_code() {
        return this.ts_code;
    }
    public void setTs_code(String ts_code) {
        this.ts_code = ts_code;
    }
    public String getSymbol() {
        return this.symbol;
    }
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getArea() {
        return this.area;
    }
    public void setArea(String area) {
        this.area = area;
    }
    public String getIndustry() {
        return this.industry;
    }
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    public String getList_date() {
        return this.list_date;
    }
    public void setList_date(String list_date) {
        this.list_date = list_date;
    }
}
