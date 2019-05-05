package com.rocky.projectcore.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.rocky.core.util.SPUtil;
import com.rocky.projectcore.common.bean.DetailUserBean;
import com.rocky.projectcore.common.bean.LoginUserBean;
import com.rocky.projectcore.common.bean.RiskEvaluationResponse;
import com.rocky.projectcore.common.bean.SystemSettingsBean;
import com.rocky.projectcore.common.bean.UserInfoBean;
import com.rocky.projectcore.common.router.CommonServerData;
import com.rocky.projectcore.common.router.RouterUrl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WangXueMing
 * @version 1.0.0
 * @date 2019/1/23
 */
@Route(path = RouterUrl.SERVER_ACCOUNT, name = "账户信息拦截器")
public class AccountServiceImpl implements IAccountService {

    private Context mContext;

    private final List<Observer> mObservers = new ArrayList<>();
    private Object mUserLock = new Object();

    @Override
    public void init(Context context) {
        mContext = context;
    }

    @Override
    public void startLogin(Context context) {
        ARouter.getInstance().build(RouterUrl.LOGIN_MAIN).greenChannel().navigation();
    }

    @Override
    public void startSplashPage() {
        ARouter.getInstance().build(RouterUrl.SPLASH_PAGE)
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK)
                .greenChannel()
                .navigation();
    }

    @Override
    public void saveUserInfo(Context context, LoginUserBean userBean) {
        synchronized (mUserLock) {
            SPUtil.put(context, LoginUserBean.ACCESS_TOKEN, userBean == null ? null : userBean.accessToken);
            SPUtil.put(context, LoginUserBean.MOBILE_PHONE, userBean == null ? null : userBean.mobilePhone);
            SPUtil.put(context, DetailUserBean.MOBILE_PHONE_QUICK_LOGIN, userBean == null ? null : userBean.mobilePhone);
            SPUtil.put(context, LoginUserBean.TIME_OUT, userBean == null ? null : userBean.timeout);
        }
    }

    @Override
    public void saveUserInfo(Context context, DetailUserBean userBean) {
        synchronized (mUserLock) {
            SPUtil.put(context, DetailUserBean.NAME, userBean.personName);
            SPUtil.put(context, DetailUserBean.IDENTITY_ID, userBean.personCardNo);
            SPUtil.put(context, DetailUserBean.ACCOUNT_TYPE, userBean.accountType);
            SPUtil.put(context, DetailUserBean.AUTH_LEVEL, userBean.authLevel);
            SPUtil.put(context, DetailUserBean.AUTH_MESSAGE, userBean.authMessage);
            SPUtil.put(context, DetailUserBean.AUTH_STATUS, userBean.authStatus);
            SPUtil.put(context, DetailUserBean.BANK_AUTH_STATUS, userBean.bankAuthStatus);
            SPUtil.put(context, DetailUserBean.CLIENT_SN, userBean.clientSn);
            SPUtil.put(context, DetailUserBean.EMAIL, userBean.email);
            SPUtil.put(context, DetailUserBean.EMERGENCY_STATUS, userBean.emergencyStatus);
            SPUtil.put(context, DetailUserBean.MAIL_AUTH_STATUS, userBean.mailAuthStatus);
            SPUtil.put(context, DetailUserBean.MOBILE_PHONE, userBean.mobilePhone);
            SPUtil.put(context, DetailUserBean.MOBILE_PHONE_QUICK_LOGIN, userBean.mobilePhone);
            SPUtil.put(context, DetailUserBean.PERSON_AUTH_STATUS, userBean.personAuthStatus);
            SPUtil.put(context, DetailUserBean.PERSON_CARD_TYPE, userBean.personCardType);
            SPUtil.put(context, DetailUserBean.PHONE_AUTH_STATUS, userBean.phoneAuthStatus);
            SPUtil.put(context, DetailUserBean.RISK_AUTH_STATUS, userBean.riskAuthStatus);
            SPUtil.put(context, DetailUserBean.TRADE_PWD_STATUS, userBean.tradePasswordStatus);
            SPUtil.put(context, DetailUserBean.WX_AUTH_STATUS, userBean.weixinAuthStatus);
        }
    }

    @Override
    public void saveUserInfo(Context context, RiskEvaluationResponse riskBean) {
        synchronized (mUserLock) {
            int type = (riskBean == null || riskBean.data == null) ? 0 : riskBean.data.type;
            SPUtil.put(context, RiskEvaluationResponse.RISK_EVAL_TYPE, type);

            String answer = (riskBean == null || riskBean.data == null) ? "" : riskBean.data.riskAnswer;
            SPUtil.put(context, RiskEvaluationResponse.RISK_EVAL_ANSWERS, answer);

            int score = (riskBean == null || riskBean.data == null) ? 0 : riskBean.data.score;
            SPUtil.put(context, RiskEvaluationResponse.RISK_EVAL_SCORE, score);

            int status = (riskBean == null || riskBean.data == null) ? RiskEvaluationResponse.RISK_EVAL_STATUS_UNAUDIT : riskBean.data.status;
            SPUtil.put(context, RiskEvaluationResponse.RISK_EVAL_STATUS, status);
        }
    }

    @Override
    public void saveUserInfo(Context context, String identityId, String name) {
        synchronized (mUserLock) {
            SPUtil.put(context, DetailUserBean.NAME, name);
            SPUtil.put(context, DetailUserBean.IDENTITY_ID, identityId);
        }
    }

    @Override
    public void clearUserInfo(Context context) {
        String lastPhoneNumber = getUserInfo(context).loginUserInfo.mobilePhoneQuickLogin;
        synchronized (mUserLock) {
            SPUtil.clear(context);
            SPUtil.put(context, DetailUserBean.MOBILE_PHONE_QUICK_LOGIN, lastPhoneNumber);
        }
    }

    @Override
    public void clearUserInfoExcludeSystemSettings(Context context) {
        String lastPhoneNumber = getUserInfo(context).loginUserInfo.mobilePhoneQuickLogin;
        boolean showMoney = SPUtil.getBoolean(context, SystemSettingsBean.K_SHOW_MONEY, SystemSettingsBean.DEF_SHOW_MONEY);
        synchronized (mUserLock) {
            SPUtil.clear(context);
            SPUtil.put(context, SystemSettingsBean.K_SHOW_MONEY, showMoney);
            SPUtil.put(context, DetailUserBean.MOBILE_PHONE_QUICK_LOGIN, lastPhoneNumber);
        }
    }

    @Override
    public void setServerType(Context context, int type) {
        SharedPreferences pm = context.getSharedPreferences(CommonServerData.FILE_NAME_SAVE_SERVER, Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = pm.edit();
        edit.putInt(CommonServerData.DEBUG_SERVICE_TYPE, type);
        edit.apply();
    }

    @Override
    public int getServerType(Context context) {
        SharedPreferences pm = context.getSharedPreferences(CommonServerData.FILE_NAME_SAVE_SERVER, Context.MODE_PRIVATE);
        return pm.getInt(CommonServerData.DEBUG_SERVICE_TYPE, CommonServerData.T_SERVICE_DEFAULT);
    }

    @Override
    public UserInfoBean getUserInfo(Context context) {
        synchronized (mUserLock) {
            UserInfoBean userInfoBean = new UserInfoBean();
            //#登陆部分的信息
            LoginUserBean loginUserBean = new LoginUserBean();
            loginUserBean.accessToken = SPUtil.getString(context, LoginUserBean.ACCESS_TOKEN, "");
            loginUserBean.mobilePhone = SPUtil.getString(context, LoginUserBean.MOBILE_PHONE, "");
            loginUserBean.mobilePhoneQuickLogin = SPUtil.getString(context, LoginUserBean.MOBILE_PHONE_QUICK_LOGIN, "");
            loginUserBean.timeout = SPUtil.getString(context, LoginUserBean.TIME_OUT, "");
            userInfoBean.loginUserInfo = loginUserBean;

            //#查出来的信息
            DetailUserBean detailUserBean = new DetailUserBean();
            detailUserBean.personName = SPUtil.getString(context, DetailUserBean.NAME, "");
            detailUserBean.personCardNo = SPUtil.getString(context, DetailUserBean.IDENTITY_ID, "");

            detailUserBean.accountType = SPUtil.getString(context, DetailUserBean.ACCOUNT_TYPE, "1");
            detailUserBean.authLevel = SPUtil.getInt(context, DetailUserBean.AUTH_LEVEL, 4);
            detailUserBean.authMessage = SPUtil.getString(context, DetailUserBean.AUTH_MESSAGE, "");
            detailUserBean.authStatus = SPUtil.getInt(context, DetailUserBean.AUTH_STATUS, DetailUserBean.AUTH_UNDO);
            detailUserBean.bankAuthStatus = SPUtil.getInt(context, DetailUserBean.BANK_AUTH_STATUS, DetailUserBean.BANK_AUTH_UNDO);
            detailUserBean.clientSn = SPUtil.getInt(context, DetailUserBean.CLIENT_SN, 1001);
            detailUserBean.email = SPUtil.getString(context, DetailUserBean.EMAIL, "");
            detailUserBean.emergencyStatus = SPUtil.getInt(context, DetailUserBean.EMERGENCY_STATUS, DetailUserBean.EMERGENCY_CONTACT_UNAUTH);
            detailUserBean.mailAuthStatus = SPUtil.getInt(context, DetailUserBean.MAIL_AUTH_STATUS, DetailUserBean.EMAIL_AUTH_UNDO);
            detailUserBean.mobilePhone = SPUtil.getString(context, DetailUserBean.MOBILE_PHONE, "");
            detailUserBean.mobilePhoneQuickLogin = SPUtil.getString(context, DetailUserBean.MOBILE_PHONE_QUICK_LOGIN, "");
            detailUserBean.personAuthStatus = SPUtil.getInt(context, DetailUserBean.PERSON_AUTH_STATUS, DetailUserBean.PERSON_AUTH_UNDO);
            detailUserBean.personCardType = SPUtil.getInt(context, DetailUserBean.PERSON_CARD_TYPE, 1);
            detailUserBean.phoneAuthStatus = SPUtil.getInt(context, DetailUserBean.PHONE_AUTH_STATUS, DetailUserBean.PHONE_NUM_AUTH_UNDO);
            detailUserBean.riskAuthStatus = SPUtil.getInt(context, DetailUserBean.RISK_AUTH_STATUS, DetailUserBean.RISK_UNEVAL);
            detailUserBean.tradePasswordStatus = SPUtil.getInt(context, DetailUserBean.TRADE_PWD_STATUS, DetailUserBean.TRADE_PWD_UN_SET);
            detailUserBean.weixinAuthStatus = SPUtil.getInt(context, DetailUserBean.WX_AUTH_STATUS, DetailUserBean.WX_AUTH_UNDO);
            userInfoBean.detailUserInfo = detailUserBean;

            //#
            RiskEvaluationResponse riskEvalBean = new RiskEvaluationResponse();
            riskEvalBean.data = new RiskEvaluationResponse.DataBean();
            riskEvalBean.data.riskAnswer = SPUtil.getString(context, RiskEvaluationResponse.RISK_EVAL_ANSWERS, "");
            riskEvalBean.data.score = SPUtil.getInt(context, RiskEvaluationResponse.RISK_EVAL_SCORE, 0);
            riskEvalBean.data.type = SPUtil.getInt(context, RiskEvaluationResponse.RISK_EVAL_TYPE, 0);
            riskEvalBean.data.status = SPUtil.getInt(context, RiskEvaluationResponse.RISK_EVAL_STATUS, RiskEvaluationResponse.RISK_EVAL_STATUS_UNAUDIT);
            userInfoBean.riskEvalInfo = riskEvalBean;
            return userInfoBean;
        }
    }

    @Override
    public boolean isLogin(Context context) {
        UserInfoBean bean = getUserInfo(context);
        String accessToken = bean.loginUserInfo.accessToken;
        return !TextUtils.isEmpty(accessToken);
    }

    @Override
    public boolean isCertification(Context context) {
        UserInfoBean userInfo = getUserInfo(context);
        return !TextUtils.isEmpty(userInfo.detailUserInfo.personName)
                && !TextUtils.isEmpty(userInfo.detailUserInfo.personCardNo);
    }

    @Override
    public boolean isTradePwdSetted(Context context) {
        UserInfoBean userInfo = getUserInfo(context);
        return userInfo.detailUserInfo.tradePasswordStatus == DetailUserBean.TRADE_PWD_SETED;
    }

    @Override
    public boolean isBankAuthSuccess(Context context) {
        UserInfoBean userInfo = getUserInfo(context);
        return userInfo.detailUserInfo.bankAuthStatus == DetailUserBean.AUTH_SUCC;
    }

    @Override
    public void makeTradePwdSetted(Context context) {
        SPUtil.put(context, DetailUserBean.TRADE_PWD_STATUS, DetailUserBean.TRADE_PWD_SETED);
    }

    @Override
    public boolean hasPassRiskEval(Context context) {
        UserInfoBean userInfo = getUserInfo(context);
        return userInfo.detailUserInfo.riskAuthStatus == DetailUserBean.RISK_EVALUATED;
    }

    @Override
    public void setRiskEval(Context context, int value) {
        SPUtil.put(context, DetailUserBean.RISK_AUTH_STATUS, value);
    }

    @Override
    public void registerObserver(Observer observer) {
        if (observer != null && !mObservers.contains(observer)) {
            mObservers.add(observer);
        }
    }

    @Override
    public void unregisterObserver(Observer observer) {
        if (observer != null) {
            mObservers.remove(observer);
        }
    }

    @Override
    public void notifyLoginSuccess() {
        Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onLoginSuccess();
        }
    }

    @Override
    public void notifyLoginCancel() {
        Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onLoginCancel();
        }
    }

    @Override
    public void notifyLoginFailure() {
        Observer[] observers = getObservers();
        for (int i = observers.length - 1; i >= 0; --i) {
            observers[i].onLoginFailure();
        }
    }

    @Override
    public void notifyLogout() {
        synchronized (mUserLock) {
            Observer[] observers = getObservers();
            for (int i = observers.length - 1; i >= 0; --i) {
                observers[i].onLogout();
            }
        }
    }

    @NonNull
    private Observer[] getObservers() {
        return mObservers.toArray(new Observer[mObservers.size()]);
    }
}
