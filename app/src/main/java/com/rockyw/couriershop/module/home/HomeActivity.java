package com.rockyw.couriershop.module.home;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.rockyw.core.base.mvc.BaseVcActivity;
import com.rockyw.core.widget.NoTouchViewPager;
import com.rockyw.couriershop.R;
import com.rockyw.couriershop.R2;
import com.rockyw.projectcore.common.router.CommonAction;
import com.rockyw.projectcore.common.router.RouterUrl;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Home页，加载4个tab用.
 *
 * @author: WangXueMing
 * @version: 1.0.0
 * @date: 2018/12/24
 */
@Route(path = RouterUrl.HOME)
public class HomeActivity extends BaseVcActivity {

    private static final String OBTAIN_AGREEMENT_SUCCESS = "6101";

    private static final int INDEX_FIRST_PAGE = 0;
    private static final int INDEX_PRODUCT = 1;
    private static final int INDEX_ACCOUNT = 2;
    private static final int INDEX_MY = 3;

    private static final int DEFAULT_INDEX = INDEX_FIRST_PAGE;

    @BindView(R2.id.ll_nav_container)
    LinearLayout mNavContainer;
    @BindView(R2.id.vp_container)
    NoTouchViewPager mContainerViewPager;

    private List<NavigationItemView> mNavItemViews = new ArrayList<>();
    private List<NavigationItemView.Data> mItemData = new ArrayList<>();
    private List<Fragment> mFragments = new ArrayList<>();
    private int mCurrentSelectedPos = DEFAULT_INDEX;
    private int mSelectedTabIndex;

    private Dialog mQualifyInvestorDialog, mNewAgreementDlg;

    @Override
    public int getLayoutId() {
        return R.layout.main_activity_home;
    }

    @Override
    public void initTitle() {
    }

    @Override
    public void initView() {
        bindNavItemData();
        initViewPager();
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        handleSwitchTab();
    }

    @Override
    public void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleSwitchTab();
    }

    private void handleSwitchTab() {
        mSelectedTabIndex = getIntent().getIntExtra(CommonAction.COM_ACT_DATA_1, 0);
        switch (mSelectedTabIndex) {
            case CommonAction.HOME_TAB_MAIN:
                selectTabPage(mCurrentSelectedPos = INDEX_FIRST_PAGE);
                break;
            case CommonAction.HOME_TAB_STORAGE:
                selectTabPage(mCurrentSelectedPos = INDEX_PRODUCT);
                break;
            case CommonAction.HOME_TAB_MESSAGE:
                selectTabPage(mCurrentSelectedPos = INDEX_ACCOUNT);
                break;
            case CommonAction.HOME_TAB_MY:
                selectTabPage(mCurrentSelectedPos = INDEX_MY);
                break;
            default:
                break;
        }
    }

    private void bindNavItemData() {

        mItemData.add(INDEX_FIRST_PAGE, new NavigationItemView.Data(R.drawable.main_home_ic_first_page,
                getString(R.string.main_home_title_first_page), mFragments.get(INDEX_FIRST_PAGE)));
        mItemData.add(INDEX_PRODUCT, new NavigationItemView.Data(R.drawable.main_home_ic_product,
                getString(R.string.main_home_title_product), mFragments.get(INDEX_PRODUCT)));
        mItemData.add(INDEX_ACCOUNT, new NavigationItemView.Data(R.drawable.main_home_ic_account,
                getString(R.string.main_home_title_my), mFragments.get(INDEX_ACCOUNT)));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1);

        for (int i = 0; i < mItemData.size(); i++) {
            final int index = i;
            NavigationItemView itemView = new NavigationItemView(getContext());
            itemView.setNavigationData(mItemData.get(i));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mCurrentSelectedPos == index) {
                        return;
                    }
                    selectTabPage(mCurrentSelectedPos = index);
                }
            });
            mNavItemViews.add(itemView);
            mNavContainer.addView(mNavItemViews.get(i), params);
        }
    }

    private void initViewPager() {
        mContainerViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mFragments.get(position);
            }

            @Override
            public int getCount() {
                return mFragments.size();
            }
        });
        mContainerViewPager.setOffscreenPageLimit(mFragments.size());
        // 设置默认选中item
        mContainerViewPager.setCurrentItem(DEFAULT_INDEX, false);
        selectTabPage(mCurrentSelectedPos = DEFAULT_INDEX);
    }

    private void selectTabPage(int pos) {

        for (int i = 0, size = mNavItemViews.size(); i < size; i++) {
            if (i == pos) {
                mNavItemViews.get(i).setSelect(true);
            } else {
                mNavItemViews.get(i).setSelect(false);
            }
        }
        mContainerViewPager.setCurrentItem(pos, true);
    }
}
