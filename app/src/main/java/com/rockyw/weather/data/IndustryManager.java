package com.rockyw.weather.data;

import android.text.TextUtils;

import com.rockyw.weather.WFApplication;
import com.rockyw.weather.bean.CompanyListResponse;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.internal.functions.Functions;

/**
 * Author:      hanjie
 * Created at:  2017/9/30.
 * Email:
 * Description: 聊天消息缓存管理
 */
public class IndustryManager {

    private static IndustryEntityDao sDao = WFApplication.getInstance().getDaoSession().getIndustryEntityDao();

    /**
     * 转换
     */
    private static IndustryEntity transform(String industry) {
        IndustryEntity entity = new IndustryEntity();
        entity.setIndustry(industry);
        return entity;
    }

    private static int getIndex(List<String> list, String colName) {
        if (list == null || list.size() == 0) {
            return -1;
        }
        for (int i = 0; i < list.size(); i++) {
            if (TextUtils.equals(colName, list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 批量插入 or 更新
     * <p>
     * 要保证 messages 里面所有的 message 是同一个会话内的（即 conversationId 要一样，否则会导致错删消息）
     */
    public static void insert(String industry) {
        if (TextUtils.isEmpty(industry)) {
            return;
        }
        Observable
                .just(industry)
                .map(new Function<String, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull String industry) throws Exception {
                        sDao.insertOrReplaceInTx(transform(industry));
                        return true;
                    }
                })
                .subscribeOn(DatabaseManager.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Functions.<Boolean>emptyConsumer());
    }

    /**
     * 获取所有
     */
    public static List<IndustryEntity> getAll() {
        List<IndustryEntity> results = new ArrayList<>();
        try {
            QueryBuilder qb = sDao.queryBuilder();
            qb.orderAsc(IndustryEntityDao.Properties.Id);
            results.addAll(qb.list());
        } catch (Exception exception) {
            // 缓存有异常返回空数据
            results = new ArrayList<>();
        }
        return results;
    }

    /**
     * 清空所有数据
     */
    public static void clear() {
        sDao.deleteAll();
    }

}
