package com.rockyw.weather.data;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
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
public class CompanyInfoManager {

    private static CompanyInfoListEntityDao sDao = WFApplication.getInstance().getDaoSession().getCompanyInfoListEntityDao();

    /**
     * 转换
     */
    private static CompanyInfoListEntity transform(List<String> indexName, List<String> info) {
        CompanyInfoListEntity entity = new CompanyInfoListEntity();
        entity.setArea(info.get(getIndex(indexName, CompanyInfoListEntityDao.Properties.Area.name)));
        entity.setIndustry(info.get(getIndex(indexName, CompanyInfoListEntityDao.Properties.Industry.name)));
        entity.setList_date(info.get(getIndex(indexName, CompanyInfoListEntityDao.Properties.List_date.name)));
        entity.setName(info.get(getIndex(indexName, CompanyInfoListEntityDao.Properties.Name.name)));
        entity.setSymbol(info.get(getIndex(indexName, CompanyInfoListEntityDao.Properties.Symbol.name)));
        entity.setTs_code(info.get(getIndex(indexName, CompanyInfoListEntityDao.Properties.Ts_code.name)));
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
     * 插入 or 更新
     */
    public static void insert(CompanyListResponse.DataBean company, List<String> info) {
        sDao.insertOrReplace(transform(company.fields, info));
    }

    /**
     * 批量插入 or 更新
     * <p>
     * 要保证 messages 里面所有的 message 是同一个会话内的（即 conversationId 要一样，否则会导致错删消息）
     */
    public static void insert(final CompanyListResponse.DataBean companyList) {
        if (companyList == null) {
            return;
        }
        Observable
                .fromIterable(companyList.items)
                .map(new Function<List<String>, CompanyInfoListEntity>() {
                    @Override
                    public CompanyInfoListEntity apply(@NonNull List<String> companyInfo) throws Exception {
                        return transform(companyList.fields, companyInfo);
                    }
                })
                .toList()
                .map(new Function<List<CompanyInfoListEntity>, Boolean>() {
                    @Override
                    public Boolean apply(@NonNull List<CompanyInfoListEntity> entities) throws Exception {
                        sDao.insertOrReplaceInTx(entities);
                        return true;
                    }
                })
                .subscribeOn(DatabaseManager.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Functions.<Boolean>emptyConsumer());
    }

    /**
     * 获取一条记录
     *
     * @param symbolId
     * @return
     */
    private static CompanyInfoListEntity get(String symbolId) {
        QueryBuilder qb = sDao.queryBuilder();
        qb.where(
                CompanyInfoListEntityDao.Properties.Symbol.eq(symbolId)
        );
        return (CompanyInfoListEntity) qb.unique();
    }

    public static CompanyInfoListEntity getCompanyInfo(String symbolId) {
        CompanyInfoListEntity entity = get(symbolId);
        if (entity != null) {
            return entity;
        } else {
            return null;
        }
    }

    /**
     * 获取所有
     */
    public static Observable<List<CompanyInfoListEntity>> getAll(final String symbolId) {
        return Observable
                .just(symbolId)
                .map(new Function<String, List<CompanyInfoListEntity>>() {
                    @Override
                    public List<CompanyInfoListEntity> apply(@NonNull String s) throws Exception {
                        List<CompanyInfoListEntity> results = new ArrayList<>();
                        try {
                            QueryBuilder qb = sDao.queryBuilder();
                            qb.orderAsc(CompanyInfoListEntityDao.Properties.Id);
                            results.addAll(qb.list());
                        } catch (Exception exception) {
                            // 缓存有异常返回空数据
                            results = new ArrayList<>();
                        }
                        return results;
                    }
                });
    }

    /**
     * 当前是否有缓存
     *
     * @param symbolId
     * @return
     */
    public static boolean hasCache(String symbolId) {
        QueryBuilder qb = sDao.queryBuilder();
        qb.where(
                CompanyInfoListEntityDao.Properties.Symbol.eq(symbolId)
        );
        return qb.count() != 0f;
    }

    /**
     * 删除一条记录
     *
     * @param symbolId
     */
    public static void delete(String symbolId) {
        CompanyInfoListEntity entity = get(symbolId);
        if (entity != null) {
            sDao.delete(entity);
        }
    }

    /**
     * 删除一个会话里面的所有消息
     */
    public static void deleteBySymbolId(String symbol) {
        QueryBuilder qb = sDao.queryBuilder();
        qb.where(
                CompanyInfoListEntityDao.Properties.Symbol.eq(symbol)
        );
        sDao.deleteInTx(qb.list());
    }

    /**
     * 删除当前用户的缓存
     */
    public static void deleteAll() {
        QueryBuilder qb = sDao.queryBuilder();
        sDao.deleteInTx(qb.list());
    }

    /**
     * 更新一条记录
     *
     * @param message
     */
    public static void update(CompanyInfoListEntity message) {
        Observable
                .just(message)
                .map(new Function<CompanyInfoListEntity, CompanyInfoListEntity>() {
                    @Override
                    public CompanyInfoListEntity apply(CompanyInfoListEntity chatMessage) throws Exception {
                        CompanyInfoListEntity entity = get(chatMessage.getSymbol());
                        if (entity != null) {
                            sDao.update(entity);
                        }
                        return chatMessage;
                    }
                })
                .subscribeOn(DatabaseManager.getScheduler())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(Functions.emptyConsumer());
    }

    /**
     * 清空所有数据
     */
    public static void clear() {
        sDao.deleteAll();
    }

}
