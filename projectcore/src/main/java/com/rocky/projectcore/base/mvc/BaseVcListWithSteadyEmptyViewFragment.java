package com.rocky.projectcore.base.mvc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.rocky.projectcore.R;
import com.rocky.projectcore.R2;
import com.rocky.core.base.mvc.BaseVcFragment;
import com.rocky.customview.widget.WinTopBar;

import butterknife.BindView;

/**
 * ${activity列表基类,封装刷新和加载更多}
 * 这个
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/26
 */
public abstract class BaseVcListWithSteadyEmptyViewFragment extends BaseVcFragment implements OnRefreshListener, OnLoadMoreListener {

    protected int page = 0;
    protected int pageSize = 10;
    protected boolean isRefresh = true;
    @BindView(R2.id.core_ll_container)
    public ViewGroup mRootContainer;
    @BindView(R2.id.core_top_bar)
    public WinTopBar mTopBar;
    @BindView(R2.id.core_refresh_layout)
    public SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.core_fl_refresh_container)
    FrameLayout mRefreshContainer;
    @BindView(R2.id.core_rv_content)
    public RecyclerView mRv;
    @BindView(R2.id.core_ll_empty_container)
    public FrameLayout mEmptyContainer;
    @BindView(R2.id.core_ll_no_net_container)
    public FrameLayout mNoNetContainer;

    @Override
    protected void initView() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setEnableAutoLoadMore(false);
            mSmartRefreshLayout.setOnRefreshListener(this);
            mSmartRefreshLayout.setOnLoadMoreListener(this);
        }
        if (getEmptyViewLayout() != 0) {
            mEmptyContainer.addView(LayoutInflater.from(getContext()).inflate(getEmptyViewLayout(), null));
        }
        if (getNoNetLayout() != 0) {
            mNoNetContainer.addView(LayoutInflater.from(getContext()).inflate(getNoNetLayout(), null));
        }
        showNormal();
    }

    @Override
    protected int getLayout(){
        return R.layout.core_activity_simple_rv_with_srl_steady_empty_view;
    }

    protected int getEmptyViewLayout() {
        return R.layout.core_empty_view;
    }

    protected int getNoNetLayout() {
        return R.layout.core_no_network;
    }

    @Override
    protected void initData() {
    }

    @Override
    public void lazyLoadData(){
        super.lazyLoadData();
        onRefresh(mSmartRefreshLayout);
    }

    @Override
    public void showNormal() {
        mEmptyContainer.setVisibility(View.GONE);
        mNoNetContainer.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        mEmptyContainer.setVisibility(View.VISIBLE);
        mNoNetContainer.setVisibility(View.GONE);
    }

    @Override
    public void showError() {
        mEmptyContainer.setVisibility(View.GONE);
        mNoNetContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 刷新
     *
     * @param refreshLayout
     */
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        this.page = 1;
        isRefresh = true;
        loadListData(mSmartRefreshLayout, page, pageSize);
    }

    /**
     * 加载更多
     *
     * @param refreshLayout
     */
    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
        isRefresh = false;
        loadListData(mSmartRefreshLayout, page, pageSize);
    }

    /**
     * 实现自动刷新的网络请求，page=0则代表不分页加载
     *
     * @param rlRefreshLayout
     * @param page
     * @param pageSize
     */
    public abstract void loadListData(SmartRefreshLayout rlRefreshLayout, int page, int pageSize);

    /**
     * 分页加载状态下
     * 网络错误和获取出来的data为空时，一定要调用这个函数，重置pageNum
     */
    public void syncLoadMoreFailed() {
        if (!isRefresh) {
            page--;
        }
    }

    /**
     * 分页加载状态下
     * 根据当前获取出来的数据的长度，来判断是否如何展示srl。
     * 在网络请求成功下，必须要调用。
     */
    public void syncSmartRefreshLayout(@NonNull SmartRefreshLayout srl, int dataLength) {
        if (dataLength < pageSize) {
            if (isRefresh) {
                srl.finishLoadMore(0, true, true);
                return;
            } else {
                srl.finishLoadMoreWithNoMoreData();
                return;
            }
        } else {
            if (isRefresh) {
                srl.finishRefresh(0);
            } else {
                srl.finishLoadMore(0);
            }
        }
    }
}
