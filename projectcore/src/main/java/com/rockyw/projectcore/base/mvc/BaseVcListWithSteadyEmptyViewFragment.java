package com.rockyw.projectcore.base.mvc;

import com.rockyw.projectcore.R;

/**
 * ${activity列表基类,封装刷新和加载更多}
 * 这个
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2018/12/26
 */
public abstract class BaseVcListWithSteadyEmptyViewFragment extends BaseVcListFragment {

    @Override
    protected int getLayout(){
        return R.layout.core_activity_simple_rv_with_srl_steady_empty_view;
    }
}
