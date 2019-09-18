package com.rockyw.projectcore.base.mvc;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.rockyw.projectcore.R;
import com.rockyw.projectcore.R2;
import com.rockyw.core.base.mvc.BaseVcActivity;
import com.rockyw.customview.widget.WinTopBar;

import butterknife.BindView;
import me.drakeet.multitype.Items;
import me.drakeet.multitype.MultiTypeAdapter;

/**
 * ${activity列表基类,封装刷新和加载更多}
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/26
 */
public abstract class BaseVcListActivity extends BaseVcActivity implements OnRefreshListener, OnLoadMoreListener {

    /**
     * 分页加载的变量定义
     */
    protected int page = 0;
    protected int pageSize = 10;
    protected boolean isRefresh = true;

    @BindView(R2.id.core_ll_container)
    public LinearLayout mContainer;
    @BindView(R2.id.core_top_bar)
    public WinTopBar mTopBar;
    @BindView(R2.id.core_refresh_layout)
    public SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R2.id.core_rv_content)
    public RecyclerView mRv;
    @BindView(R2.id.core_ll_empty_container)
    public FrameLayout mEmptyContainer;
    @BindView(R2.id.core_ll_no_net_container)
    public FrameLayout mNoNetContainer;

    protected MultiTypeAdapter mAdapter;
    protected Items mItems = new Items();

    @Override
    protected int getLayoutId(){
        return R.layout.core_activity_simple_rv_with_srl;
    }

    protected int getEmptyViewLayout() {
        return R.layout.core_empty_view;
    }

    protected int getNoNetLayout() {
        return R.layout.core_no_network;
    }

    @Override
    protected void initView() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.setEnableLoadMore(false);
            mSmartRefreshLayout.setOnRefreshListener(this);
            mSmartRefreshLayout.setOnLoadMoreListener(this);
        }
        if (getEmptyViewLayout() != 0) {
            mEmptyContainer.addView(LayoutInflater.from(getContext()).inflate(getEmptyViewLayout(), null));
        }
        if (getNoNetLayout() != 0) {
            mNoNetContainer.addView(LayoutInflater.from(getContext()).inflate(getNoNetLayout(), null));
        }
        showNormalView();
    }

    @Override
    protected void onLazyLoad() {
        onRefresh(mSmartRefreshLayout);
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

    @Override
    public void showNormalView() {
        mEmptyContainer.setVisibility(View.GONE);
        mNoNetContainer.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView() {
        mEmptyContainer.setVisibility(View.VISIBLE);
        mNoNetContainer.setVisibility(View.GONE);
    }

    @Override
    public void showNoNetView() {
        mEmptyContainer.setVisibility(View.GONE);
        mNoNetContainer.setVisibility(View.VISIBLE);
    }

    /**
     * 实现自动刷新的网络请求，page=0则代表不分页加载
     *
     * @param srl 自动刷新空间
     * @param page 当前页标签
     * @param pageSize 页面Item数
     */
    public abstract void loadListData(SmartRefreshLayout srl, int page, int pageSize);

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
            } else {
                srl.finishLoadMoreWithNoMoreData();
            }
        } else {
            if (isRefresh) {
                srl.finishRefresh(0);
                srl.setNoMoreData(false);
            } else {
                srl.finishLoadMore(0);
            }
        }
    }

    protected void notifyDataSetChanged() {
        notifyDataSetChanged(false);
    }

    protected void notifyDataSetChanged(boolean showNoNet) {
        if (showNoNet) {
            showNoNetView();
        } else if (mItems.size() == 0) {
            showEmptyView();
        } else {
            showNormalView();
        }
        if(mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
