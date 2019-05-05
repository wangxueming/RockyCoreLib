package com.rocky.projectcore.login;

import android.content.Context;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.rocky.projectcore.common.bean.DetailUserBean;
import com.rocky.projectcore.common.bean.LoginUserBean;
import com.rocky.projectcore.common.bean.RiskEvaluationResponse;
import com.rocky.projectcore.common.bean.UserInfoBean;

/**
 * 账户登录服务
 *
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/3
 */
public interface IAccountService extends IProvider {

    /**
     * 判断是否登录
     * @param context
     * @return 是否登录成功
     */
    boolean isLogin(Context context);

    /**
     * 判断是否已实名认证
     * @param context
     * @return 是否已实名
     */
    boolean isCertification(Context context);

    /**
     * 是否设置过交易密码
     * @param context
     * @return 是否 设置过交易密码
     */
    boolean isTradePwdSetted(Context context);

    /**
     * 银行卡是否认证成功
     * @param context
     * @return
     */
    boolean isBankAuthSuccess(Context context);

    /**
     * 设置交易密码的状态
     * @param context
     */
    void makeTradePwdSetted(Context context);

    /**
     * 判断是否通过风险评估
     * @param context
     * @return
     */
    boolean hasPassRiskEval(Context context);

    /**
     * 设置风险评估的状态
     * @param context
     * @param value
     */
    void setRiskEval(Context context, int value);

    /**
     * 跳转到登陆页
     * @param context
     */
    void startLogin(Context context);

    /**
     * 跳转到启动页
     */
    void startSplashPage();

    /**
     * 保存用户信息
     * @param context
     * @param userBean
     */
    void saveUserInfo(Context context, LoginUserBean userBean);

    /**
     * 保存用户信息
     * @param context
     * @param userBean
     */
    void saveUserInfo(Context context, DetailUserBean userBean);

    /**
     * 保存用户信息
     * @param context
     * @param riskBean
     */
    void saveUserInfo(Context context, RiskEvaluationResponse riskBean);

    /**
     * 保存用户信息
     * @param context
     * @param identityId
     * @param name
     */
    void saveUserInfo(Context context, String identityId, String name);

    /**
     * 清理用户数据
     * @param context
     */
    void clearUserInfo(Context context);

    /**
     * 清理用户数据
     * @param context
     */
    void clearUserInfoExcludeSystemSettings(Context context);

    /**
     * 服务器的类型，用于调试
     * @param context
     * @param type
     */
    void setServerType(Context context, int type);

    /**
     * 获取服务类型，用于调试
     * @param context
     * @return
     */
    int getServerType(Context context);

    /**
     * 获取用户数据
     * @param context
     * @return
     */
    UserInfoBean getUserInfo(Context context);

    /**
     * 注册监听
     * @param observer
     */
    void registerObserver(Observer observer);

    /**
     * 注销监听
     * @param observer
     */
    void unregisterObserver(Observer observer);

    /**
     * 调用登陆成功
     */
    void notifyLoginSuccess();

    /**
     * 调用登陆中止
     */
    void notifyLoginCancel();

    /**
     * 调用登陆失败
     */
    void notifyLoginFailure();

    /**
     * 调用登出
     */
    void notifyLogout();

    interface Observer {

        /**
         * 登陆成功
         */
        void onLoginSuccess();

        /**
         * 登陆中止
         */
        void onLoginCancel();

        /**
         * 登陆失败
         */
        void onLoginFailure();

        /**
         * 登出
         */
        void onLogout();
    }
}
