package com.rockyw.weather;

import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rockyw.core.base.mvc.BaseVcActivity;
import com.rockyw.projectcore.common.router.RouterUrl;
import com.rockyw.projectcore.net.CommonObserver;
import com.rockyw.weather.bean.CompanyListBody;
import com.rockyw.weather.bean.CompanyListResponse;
import com.rockyw.weather.bean.StockQuotationBody;
import com.rockyw.weather.bean.StockQuotationResponse;
import com.rockyw.weather.data.CompanyInfoListEntity;
import com.rockyw.weather.data.CompanyInfoManager;
import com.rockyw.weather.data.IndustryEntityDao;
import com.rockyw.weather.data.IndustryManager;
import com.rockyw.weather.net.Server;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2019/9/22
 */
@Route(path = RouterUrl.STOCK_DATA)
public class CompanyListActivity extends BaseVcActivity {

    @BindView(R2.id.stock_btn_add_data)
    Button mAddDataBtn;
    @BindView(R2.id.stock_btn_add_company)
    Button mAddCompanyBtn;
    @BindView(R2.id.stock_tv_sum)
    TextView mSumTv;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity_stock_main;
    }

    @Override
    protected void initTitle() {
    }

    @Override
    protected void initView() {
    }

    @OnClick(R2.id.stock_btn_add_company)
    public void clickAddCompaniesList() {
        CompanyListBody body = new CompanyListBody();
        body.api_name = "stock_basic";
        body.token = "221e6f93806763b37923622e9933139ef2e1a2e72ca964beb1ec27b8";
        CompanyListBody.ParamsBean params = new CompanyListBody.ParamsBean();
        params.list_status = "L";
        body.params = params;

        addDisposable(Server.getServerApi().getCompaniesInfoList(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<CompanyListResponse>(this) {
                                   @Override
                                   public void onSuccess(CompanyListResponse response) {
                                       updateDCompanies(response);
                                   }
                               }
                ));
    }

    @OnClick(R2.id.stock_btn_add_industry)
    public void clickIndustry() {
        final List<String> industryList = new ArrayList();
        CompanyInfoManager.getAll("")
                .subscribe(new Consumer<List<CompanyInfoListEntity>>() {
            @Override
            public void accept(List<CompanyInfoListEntity> companyInfoListEntities) throws Exception {
                for(CompanyInfoListEntity entity : companyInfoListEntities) {
                    if (!industryList.contains(entity.getIndustry())) {
                        industryList.add(entity.getIndustry());
                        IndustryManager.insert(entity.getIndustry());
                    }
                }
            }
        });
    }

    @OnClick(R2.id.stock_btn_add_data)
    public void clickAddData() {
        StockQuotationBody body = new StockQuotationBody();
        body.api_name = "daily";
        body.token = "221e6f93806763b37923622e9933139ef2e1a2e72ca964beb1ec27b8";
        StockQuotationBody.ParamsBean params = new StockQuotationBody.ParamsBean();
        params.ts_code = "000001.SZ";
        params.end_date = "20190901";
        params.start_date = "20100901";
        body.params = params;

        addDisposable(Server.getServerApi().getSingleStockInfo(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CommonObserver<StockQuotationResponse>(this) {
                                   @Override
                                   public void onSuccess(StockQuotationResponse response) {
                                   }
                               }
                ));
    }

    private void updateDCompanies(CompanyListResponse response) {
        CompanyInfoManager.insert(response.data);
    }
}
