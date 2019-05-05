package com.rocky.core.push;

import android.content.Context;

import com.igexin.sdk.PushManager;
import com.igexin.sdk.Tag;

/**
 * @author: wangxueming
 * @version: 1.0.0
 * @date: 2018/12/14
 */
public class PushHelper {
    /**
     * 启动服务
     *
     * @param appContext application
     */
    public static void initPushService(Context appContext) {
        PushManager.getInstance().initialize(appContext, GeTuiPushService.class);
        PushManager.getInstance().registerPushIntentService(appContext, GeTuiIntentService.class);
    }

    /**
     * 接口 PushManager 中的 stopService，停止 SDK 服务，服务不会终止运行， 只是终止推送和联网功能
     * 重新调用 initialize 接口初始化推送服务或者调用 turnOnPush 接口开启 push， 即可正常推送， 具体参考 initialize 和 turnOnPush 接口
     *
     * @param appContext application
     */
    public static void stopService(Context appContext) {
        PushManager.getInstance().stopService(appContext);
    }

    /**
     * 接口 PushManager 中的 turnOnPush， 开启Push推送， 默认是开启状态， 关闭状态则收不到推送。turnOnPush 默认打开
     * 如果已经调用了 stopService 接口停止了 SDK 服务，调用 turnOnPush 或者重新调用 initialize 之后即可正常推送
     * 如果已经调用了 turnOffPush 接口关闭了推送， 只有调用 turnOnPush 之后才能正常推送
     * @param appContext application
     */
    public static void turnOnPush(Context appContext) {
        PushManager.getInstance().turnOnPush(appContext);
    }

    /**
     * 接口 PushManager 中的 turnOffPush， 关闭Push推送， 关闭后则无法收到推送消息
     * 如果已经调用了 stopService 接口停止了 SDK 服务，调用 turnOnPush 或者重新调用 initialize 之后即可正常推送
     * 如果已经调用了 turnOffPush 接口关闭了推送， 只有调用 turnOnPush 之后才能正常推送
     * @param appContext application
     */
    public static void turnOffPush(Context appContext) {
        PushManager.getInstance().turnOffPush(appContext);
    }

    /**
     * 为当前用户设置一组标签，后续推送可以指定标签名进行定向推送
     * 标签的设定，一定要在获取到 Clientid 之后才可以设定。标签的设定，服务端限制一天只能成功设置一次
     *
     * @param context 应用的 Context
     * @param tag 用户标签， 具体参考 setName 接口
     * @param sn 用户自定义的序列号，用来唯一标识该动作, 自定义 IntentService 中会回执该结果
     * @return int
     * 0：成功
     * 20001：tag 数量过大(单次设置的tag数量不超过100)
     * 20002：设置频率过快(频率限制每秒一次)
     * 20003：标签重复
     * 20004：服务初始化失败
     * 20005：setTag 异常
     * 20006：tag 为空
     * 20007：sn为空
     * 20008：离线,还未登陆成功
     * 20009：该 appid 已经在黑名单列表
     * 20010：已存 tag 数目超限
     */
    public static int setTag(Context context, Tag[] tag, String sn) {
        return PushManager.getInstance().setTag(context, tag, sn);
    }

    /**
     * 接口 PushManager 中的 setSilentTime，设置静默时间，静默期间SDK将不再联网
     * @param context 应用的 Context
     * @param beginHour 开始时间，设置范围在0-23小时之间，单位 h
     * @param duration 持续时间，设置范围在0-23小时之间。持续时间为0则不静默，单位 h
     * @return
     */
    public boolean setSilentTime(Context context,int beginHour,int duration){
        return PushManager.getInstance().setSilentTime(context, beginHour, duration);
    }

}
