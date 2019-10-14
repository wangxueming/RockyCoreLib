package com.rockyw.weather.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/9/23
 */
@Entity
public class IndustryEntity {
    @Id
    private Long id;

    private String industry;

    @Generated(hash = 29809582)
    public IndustryEntity(Long id, String industry) {
        this.id = id;
        this.industry = industry;
    }

    @Generated(hash = 1035089923)
    public IndustryEntity() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustry() {
        return this.industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }
}
